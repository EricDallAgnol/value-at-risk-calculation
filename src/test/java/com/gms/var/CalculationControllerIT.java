package com.gms.var;

import com.gms.var.repository.PnLTradesRepository;
import com.gms.var.service.DataManagementService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VarApplication.class)
@AutoConfigureMockMvc
public class CalculationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    DataManagementService dataManagementService;

    @Mock
    private PnLTradesRepository pnLTradesRepository;

    private static JSONArray testJson ;

    static {
        try {
            String jsonString  = new String(Files.readAllBytes(
                    new ClassPathResource("var_computation_test_resources.json").getFile().toPath()
            ));
            testJson = new JSONArray(jsonString );
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void contextLoads() {
        assertThat(dataManagementService).isNotNull();
        assertThat(pnLTradesRepository).isNotNull();
    }

    private void sendVaRPayload(String payload, String expectedValue) throws Exception {
        mockMvc.perform(
                        post("/calculation/var")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(expectedValue));
    }

    /**
     * Used to select the payload for a given test name
     * @param testPayloads
     * @param testName
     * @return
     * @throws JSONException
     */
    private static JSONObject getTestPayload(JSONArray testPayloads, String testName) throws JSONException {
        for (int i = 0; i < testPayloads.length(); i++) {
            JSONObject test = testPayloads.getJSONObject(i);
            if (test.getString("testName").equals(testName)) {
                return test;
            }
        }
        return null;
    }

    @Test
    public void compute_var_when_no_trades() throws Exception {
        mockMvc.perform(
                        post("/calculation/var")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("No trades selected")));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "compute_var_99_simple_higher_2_trades",
            "compute_var_95_simple_higher_2_trades",

            "compute_var_99_simple_lower",
            "compute_var_99_simple_higher",
            "compute_var_99_simple_nearest",
            "compute_var_99_simple_linear",

            "compute_var_95_simple_lower",
            "compute_var_95_simple_higher",
            "compute_var_95_simple_nearest",
            "compute_var_95_simple_linear",

            "compute_var_99_centered_lower",
            "compute_var_99_centered_higher",
            "compute_var_99_centered_nearest",
            "compute_var_99_centered_linear",

            "compute_var_95_centered_lower",
            "compute_var_95_centered_higher",
            "compute_var_95_centered_nearest",
            "compute_var_95_centered_linear",

            "compute_var_99_exc_lower",
            "compute_var_99_exc_higher",
            "compute_var_99_exc_nearest",
            "compute_var_99_exc_linear",

            "compute_var_95_exc_lower",
            "compute_var_95_exc_higher",
            "compute_var_95_exc_nearest",
            "compute_var_95_exc_linear",

            "compute_var_99_inc_lower",
            "compute_var_99_inc_higher",
            "compute_var_99_inc_nearest",
            "compute_var_99_inc_linear",

            "compute_var_95_inc_lower",
            "compute_var_95_inc_higher",
            "compute_var_95_inc_nearest",
            "compute_var_95_inc_linear",

    })
    public void var99Test(String testName) throws Exception {
        JSONObject selectedTest = getTestPayload(testJson, testName);
        assertThat(selectedTest).isNotNull();
        String payload = selectedTest.getString("payload");
        String expectedValue = selectedTest.getString("expectedValue");
        sendVaRPayload(payload, expectedValue);
    }

}
