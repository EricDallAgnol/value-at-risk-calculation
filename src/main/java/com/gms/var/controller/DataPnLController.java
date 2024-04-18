package com.gms.var.controller;

import com.gms.var.service.DataManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("data")
public class DataPnLController {

    @Autowired
    DataManagementService dataManagementService;

    @GetMapping("/all-trades")
    public ResponseEntity getAllTrades() {
        try {
            return ResponseEntity.ok(dataManagementService.getAllTrades());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/drop-pnl")
    public ResponseEntity<String> dropPnL() {
        try {
            dataManagementService.purgePnLTrades();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-pnl")
    public ResponseEntity<String> uploadPnL(@RequestParam("file") MultipartFile file) {
        try {
            dataManagementService.isCSVFileValid(file);
            dataManagementService.save(file);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
