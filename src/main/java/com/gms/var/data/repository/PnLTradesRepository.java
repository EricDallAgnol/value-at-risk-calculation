package com.gms.var.data.repository;

import com.gms.var.data.entity.PnLTrade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PnLTradesRepository extends CrudRepository<PnLTrade, Long> {

    List<PnLTrade> findAll();

}
