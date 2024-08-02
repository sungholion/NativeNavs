package com.nativenavs.auth.dto;

import com.nativenavs.auth.entity.LoginEntity;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private static final Logger log = LoggerFactory.getLogger(LoginDTO.class);
    private int id;
    private String email;
    private Boolean isNav;
    private String accessToken;
    private String refreshToken;

    // Entity -> DTO
    public static LoginDTO toLoginDTO(LoginEntity loginEntity){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(loginEntity.getId());


        return loginDTO;
    }


}
