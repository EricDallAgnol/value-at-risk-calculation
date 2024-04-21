package com.gms.var.controller;

import com.gms.var.calculation.ComputeHistoricalVaR;
import com.gms.var.records.ComputeVaRFormRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("calculation")
public class CalculationController {

    @PostMapping("/var")
    public ResponseEntity<String> computeValueAtRisk(@RequestBody ComputeVaRFormRecord computeVaRFormRecord) {
        try {
            if (computeVaRFormRecord.tradePositionWithPnL() == null) {
                throw new RuntimeException("No trades selected !");
            }
            ComputeHistoricalVaR computeHistoricalVaR = new ComputeHistoricalVaR(List.of(computeVaRFormRecord.tradePositionWithPnL()), computeVaRFormRecord.quantile(), computeVaRFormRecord.mode(), computeVaRFormRecord.interpolation());
            double var = computeHistoricalVaR.computeTradeVaR();
            return ResponseEntity.ok(String.valueOf(var));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
