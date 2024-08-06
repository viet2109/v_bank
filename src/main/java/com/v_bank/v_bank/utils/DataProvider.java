package com.v_bank.v_bank.utils;

import com.v_bank.v_bank.dao.RoleDao;
import com.v_bank.v_bank.entity.RoleEntity;
import com.v_bank.v_bank.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataProvider implements CommandLineRunner {
    private final RoleDao roleDao;
    private final Environment environment;

    @Override
    public void run(String... args) {
        String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");
        if ("create".equals(ddlAuto) || "create-drop".equals(ddlAuto)) {
            List<RoleType> initRolesList = List.of(RoleType.ADMIN, RoleType.EMPLOYEE, RoleType.USER);
            initRolesList.forEach(roleType -> {
                RoleEntity roleEntity = RoleEntity
                        .builder()
                        .role(roleType)
                        .build();
                roleDao.save(roleEntity);
            });
        }
    }
}
