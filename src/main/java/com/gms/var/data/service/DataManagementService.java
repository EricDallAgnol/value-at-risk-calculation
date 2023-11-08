package com.gms.var.data.service;

import com.gms.var.data.entity.PnLTrade;
import com.gms.var.data.repository.PnLTradesRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataManagementService {

    protected static final String CSV_TYPE = "text/csv";
    protected static final char CSV_SEPARATOR = ',';
    protected static final String VECTOR_SEPARATOR = ";";

    protected static final int ASOFDATE_INDEX = 0;
    protected static final int TRADEID_INDEX = 1;
    protected static final int VECTOR_INDEX = 2;

    @Autowired
    PnLTradesRepository pnLTradesRepository;

    public void isCSVFileValid(MultipartFile csvFile) {
        if (!csvFile.isEmpty()) {
            checkCSVType(csvFile);
        } else {
            throw new RuntimeException("The file provided is empty !");
        }

    }

    /**
     * Save P&L Trades from a CSV file
     * @param csvFile - CSV file containing P&L Trades
     */
    public void save(MultipartFile csvFile) {
        try {
            List<PnLTrade> pnLTradeList = convertCSVIntoPnLTrades(csvFile);
            pnLTradesRepository.saveAll(pnLTradeList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store csv data: " + e.getMessage());
        }
    }

    /**
     *
     * @return all the trades stored in the H2 DataBase
     */
    public List<PnLTrade> getAllTrades() {
        try {
            return pnLTradesRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to store csv data: " + e.getMessage());
        }
    }

    /**
     * Delete all the entries in the H2 DataBase
     */
    public void purgePnLTrades() {
        try {
            pnLTradesRepository.deleteAll();
        } catch (Exception e) {
            throw new RuntimeException("Fail to purge data: " + e.getMessage());
        }
    }

    /**
     * Check if CSV file
     * @param file - file containing the P&L Trades
     * @throws RuntimeException - if not a CSV file
     */
    private static void checkCSVType(MultipartFile file) throws RuntimeException {
        if (!CSV_TYPE.equals(file.getContentType())) {
            throw new RuntimeException("Failed to load trades : the file provided is not a CSV.");
        }
    }

    /**
     * Parse csv and extract a list of PnLTrade
     * @param csvFile - CSV file containing the P&L Trades
     * @return - list of PnLTrade
     */
    private static List<PnLTrade> convertCSVIntoPnLTrades(MultipartFile csvFile) {

        List<PnLTrade> pnLTradeList = new ArrayList<>();

        try {
            Reader reader = new InputStreamReader(csvFile.getInputStream());

            CSVParser csvParser = new CSVParserBuilder()
                    .withSeparator(CSV_SEPARATOR)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                LocalDate asOfDate = LocalDate.parse(line[ASOFDATE_INDEX]);
                String tradeID = line[TRADEID_INDEX];
                double[] pnl = Arrays.stream(line[VECTOR_INDEX].split(VECTOR_SEPARATOR)).mapToDouble(Double::valueOf).toArray();

                pnLTradeList.add(new PnLTrade(asOfDate, tradeID, pnl));
            }

        return pnLTradeList;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
