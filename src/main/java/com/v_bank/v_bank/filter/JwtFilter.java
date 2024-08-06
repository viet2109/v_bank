package com.v_bank.v_bank.filter;

import com.v_bank.v_bank.dao.UserDao;
import com.v_bank.v_bank.entity.UserEntity;
import com.v_bank.v_bank.enums.Error;
import com.v_bank.v_bank.exception.AppException;
import com.v_bank.v_bank.mapper.UserMapper;
import com.v_bank.v_bank.utils.JwtProvider;
import com.v_bank.v_bank.utils.UserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        AtomicReference<String> email = new AtomicReference<>(null);
        AtomicReference<String> token = new AtomicReference<>(null);

        if (request.getCookies() != null) {
            Arrays.stream(request.getCookies())
                    .filter(cookie -> "AccessToken".equals(cookie.getName()))
                    .findFirst().ifPresent(cookie -> {
                        String tokenValue = cookie.getValue();
                        email.set(jwtProvider.extractUserEmail(tokenValue));
                        token.set(tokenValue);
                    });
        }

        if (email.get() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userDao.findByEmail(email.get()).ifPresent(user -> {
                UserDetails userDetails = userMapper.userEntityToUsrDetails(user);
                if (jwtProvider.validateToken(token.get(), userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            });
        }

        filterChain.doFilter(request, response);
    }

}
