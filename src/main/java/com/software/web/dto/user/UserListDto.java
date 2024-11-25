package com.software.web.dto.user;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserListDto {
    List<UserDto> staff;
}