package com.v_bank.v_bank.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LocalDateTime dob;

    @Data
    @Builder
    public static class UserLoginInfo {

        @NotBlank(message = "This field is mandatory")
        @Pattern(regexp = "^(?:\\d{10}|\\d{10}|[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,})$", message = "The email or phone has invalid")
        String phoneOrMail;

        @NotBlank(message = "This field is mandatory")
        @Length(min = 6, message = "The password must be at least 6")
        private String password;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRegisterInfo {

        @NotBlank(message = "The firstname is mandatory")
        private String firstName;

        @NotBlank(message = "The lastname is mandatory")
        private String lastName;

        @NotBlank(message = "The phone is mandatory")
        @Pattern(regexp = "^(?:\\+84)?\\d{9}$", message = "The phone number has invalid")
        private String phone;

        @NotBlank(message = "The email is mandatory")
        @Email(message = "The email has invalid")
        private String email;
        private LocalDateTime dob;

        @Length(min = 6, message = "The password must be at least 6")
        private String password;
    }
}
