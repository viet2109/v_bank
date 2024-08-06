package com.v_bank.v_bank.service;

import com.v_bank.v_bank.dao.CardDao;
import com.v_bank.v_bank.dao.UserDao;
import com.v_bank.v_bank.dto.CardDto;
import com.v_bank.v_bank.entity.UserEntity;
import com.v_bank.v_bank.enums.Error;
import com.v_bank.v_bank.exception.AppException;
import com.v_bank.v_bank.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardDao cardDao;
    private final UserDao userDao;
    private final CardMapper cardMapper;

    public List<CardDto> getListCardByUserId(Long id) {
        UserEntity userEntity = userDao.findById(id)
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        return cardDao.findByUser(userEntity).stream().map(cardMapper::entityToDto).collect(Collectors.toList());
    }
}
