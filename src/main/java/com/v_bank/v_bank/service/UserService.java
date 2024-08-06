package com.v_bank.v_bank.service;

import com.v_bank.v_bank.dao.CardDao;
import com.v_bank.v_bank.dao.RoleDao;
import com.v_bank.v_bank.dao.UserDao;
import com.v_bank.v_bank.dto.UserDto;
import com.v_bank.v_bank.entity.CardEntity;
import com.v_bank.v_bank.entity.UserEntity;
import com.v_bank.v_bank.enums.CardType;
import com.v_bank.v_bank.enums.Error;
import com.v_bank.v_bank.enums.RoleType;
import com.v_bank.v_bank.exception.AppException;
import com.v_bank.v_bank.mapper.UserMapper;
import com.v_bank.v_bank.utils.CardProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CardProvider cardProvider;
    private final CardDao cardDao;

    public void register(UserDto.UserRegisterInfo userDto) {
        userDao.findByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new AppException(Error.ALREADY_USER);
        });
        UserEntity user = userMapper.loginDtoToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleDao.findByRole(RoleType.USER).ifPresent(roleEntity -> user.setRoles(List.of(roleEntity)));
        userDao.save(user);

        String cardNumber = cardProvider.generatedCardNumberByEmail(userDto.getEmail(), 12);
        CardEntity card = CardEntity
                .builder()
                .cardNumber(cardNumber)
                .user(user)
                .cardType(CardType.DEBIT)
                .build();

        cardDao.save(card);
    }

    public UserDto login(UserDto.UserLoginInfo userLoginInfo) {
        Optional<UserEntity> userOP = userDao.findByPhoneOrEmail(userLoginInfo.getPhoneOrMail(), userLoginInfo.getPhoneOrMail());
        userOP.orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        if (!passwordEncoder.matches(userLoginInfo.getPassword(), userOP.get().getPassword()))
            throw new AppException(Error.LOGIN_INFO_WRONG);
        return userMapper.entityToDto(userOP.get());
    }

}
