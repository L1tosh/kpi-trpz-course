package com.software.web.dto.user;

import com.software.web.dto.role.RoleListDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Long userId;
    RoleListDto roles;
}
