package com.software.dto.user;

import com.software.dto.role.RoleListDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Long userId;
    RoleListDto roles;
}
