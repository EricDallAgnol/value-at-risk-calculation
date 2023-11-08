package com.gms.var.data.controller;

import com.gms.var.calculation.HistoricalVaRComputation;
import com.gms.var.data.entity.ComputeVaRForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("calculation")
public class CalculationController {

    @PostMapping("/var")
    public ResponseEntity<String> computeValueAtRisk(@RequestBody ComputeVaRForm computeVaRForm) {
        try {
            if (computeVaRForm.getPnLTrades() == null) {
                throw new RuntimeException("No trades selected !");
            }
            double var = HistoricalVaRComputation.computeTradeVaR(List.of(computeVaRForm.getPnLTrades()), computeVaRForm.getQuantile(), computeVaRForm.getMode(), computeVaRForm.getInterpolation());
            return ResponseEntity.ok(String.valueOf(var));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
