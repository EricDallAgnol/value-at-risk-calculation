package com.gms.var;

import com.gms.var.repository.PnLTradesRepository;
import com.gms.var.service.DataManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VarApplication.class)
@AutoConfigureMockMvc
public class DataPnLControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    DataManagementService dataManagementService;

    @Mock
    private PnLTradesRepository pnLTradesRepository;

    private static String inputFileString;
    private static String expectedResultString;

    static {
        try {
            inputFileString = new String(Files.readAllBytes(
                    new ClassPathResource("Trade-Position-With-PV.csv").getFile().toPath()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            expectedResultString = new String(Files.readAllBytes(
                    new ClassPathResource("expected_result.json").getFile().toPath()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void contextLoads() {
        assertThat(dataManagementService).isNotNull();
        assertThat(pnLTradesRepository).isNotNull();
    }

    @Test
    public void upload_wrong_file_type_500_expected() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "wrong.xml",
                MediaType.TEXT_XML_VALUE,
                "Hello, World!".getBytes()
        );
        mockMvc.perform(multipart("/data/upload-pnl").file(file))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("the file provided is not a CSV")));
    }

    @Test
    public void upload_csv_200_expected() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "right.csv",
                "text/csv",
                "Hello, World!".getBytes()
        );
        mockMvc.perform(multipart("/data/upload-pnl").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void load_and_get_trades_from_H2() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "right.csv",
                "text/csv",
                inputFileString.getBytes()
        );
        mockMvc.perform(multipart("/data/upload-pnl").file(file))
                .andExpect(status().isOk());

        mockMvc.perform(get("/data/all-trades"))
                .andExpect(status().isOk())
                .andExpect(content().string("[" + expectedResultString + "]"));
    }

    @Test
    public void load_and_drop_H2() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "right.csv",
                "text/csv",
                inputFileString.getBytes()
        );
        mockMvc.perform(multipart("/data/upload-pnl").file(file))
                .andExpect(status().isOk());

        mockMvc.perform(get("/data/all-trades"))
                .andExpect(status().isOk())
                .andExpect(content().string("[" + expectedResultString + "]"));

        mockMvc.perform(delete("/data/drop-pnl"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/data/all-trades"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }



}
