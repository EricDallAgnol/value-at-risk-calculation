package com.gms.var.records;

import com.gms.var.entities.TradePositionWithPnL;
import com.gms.var.enums.VaRInterpolationEnum;
import com.gms.var.enums.VarModeEnum;

import java.io.Serializable;


public record ComputeVaRFormRecord(double quantile, VarModeEnum mode, VaRInterpolationEnum interpolation, TradePositionWithPnL[] tradePositionWithPnL) implements Serializable {

}
