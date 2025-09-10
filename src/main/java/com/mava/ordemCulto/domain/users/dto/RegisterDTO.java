package com.mava.ordemCulto.domain.users.dto;

import com.mava.ordemCulto.domain.users.UserRole;

public record RegisterDTO(String login, String password, UserRole role){

}
