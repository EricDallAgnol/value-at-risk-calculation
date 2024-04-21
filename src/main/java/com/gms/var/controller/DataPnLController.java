package com.gms.var.controller;

import com.gms.var.service.DataManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("data")
public class DataPnLController {

    protected final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    DataManagementService dataManagementService;

    @GetMapping("/all-trades")
    public ResponseEntity getAllTrades() {
        try {
            logger.info("User is requesting all trades ...");
            return ResponseEntity.ok(dataManagementService.getAllTrades());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/drop-pnl")
    public ResponseEntity<String> dropPnL() {
        try {
            logger.info("Purge in progress ...");
            dataManagementService.purgePnLTrades();
            logger.info("Purge successfully done ...");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-pnl")
    public ResponseEntity<String> uploadPnL(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("Trade Position Upload in progress ...");
            dataManagementService.isCSVFileValid(file);
            dataManagementService.save(file);
            logger.info("Trade Positions successfully saved !");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
