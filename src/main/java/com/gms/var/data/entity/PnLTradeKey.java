package com.gms.var.data.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class PnLTradeKey implements Serializable {
    private LocalDate asOfDate;
    private String tradeID;
}
