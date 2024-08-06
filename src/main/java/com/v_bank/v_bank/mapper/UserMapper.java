package com.v_bank.v_bank.mapper;

import com.v_bank.v_bank.dto.UserDto;
import com.v_bank.v_bank.entity.UserEntity;
import com.v_bank.v_bank.utils.UserDetails;
import org.mapstruct.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default UserDetails userEntityToUsrDetails(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDetails userDetails = new UserDetails();
        userDetails.setName(userEntity.getEmail());
        userDetails.setPassword(userEntity.getPassword());
        userDetails.setAuthorities(userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList()));
        return userDetails;
    }

    UserEntity dtoToEntity(UserDto userDto);
    UserDto entityToDto(UserEntity userEntity);
    UserEntity loginDtoToEntity(UserDto.UserRegisterInfo userRegisterInfo);
}
