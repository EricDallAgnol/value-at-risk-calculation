package com.gms.var.data.entity;

import com.gms.var.calculation.VaRInterpolation;
import com.gms.var.calculation.VarMode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ComputeVaRForm implements Serializable {
    private double quantile;
    private VarMode mode;
    private VaRInterpolation interpolation;
    private PnLTrade[] pnLTrades;
}
