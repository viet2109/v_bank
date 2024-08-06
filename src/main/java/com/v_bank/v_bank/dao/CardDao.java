package com.v_bank.v_bank.dao;

import com.v_bank.v_bank.entity.CardEntity;
import com.v_bank.v_bank.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardDao extends CrudRepository<CardEntity, Long> {
    List<CardEntity> findByUser(UserEntity user);
}
