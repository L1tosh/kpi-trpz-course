package com.software.dto.user;

import com.software.dto.role.RoleListDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Long id;

    @NotBlank(message = "Name cannot be blank")
    String name;

    @NotBlank(message = "Surname cannot be blank")
    String surname;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    String email;

    RoleListDto roles;
}
