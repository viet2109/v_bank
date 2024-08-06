package com.v_bank.v_bank.mapper;

import com.v_bank.v_bank.dto.CardDto;
import com.v_bank.v_bank.entity.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDto entityToDto(CardEntity cardEntity);
}
