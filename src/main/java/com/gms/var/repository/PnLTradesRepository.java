package com.gms.var.repository;

import com.gms.var.entities.TradePositionWithPnL;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PnLTradesRepository extends CrudRepository<TradePositionWithPnL, Long> {
    List<TradePositionWithPnL> findAll();

}
