package com.v_bank.v_bank.dao;

import com.v_bank.v_bank.entity.RoleEntity;
import com.v_bank.v_bank.enums.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleDao extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(RoleType role);
}
