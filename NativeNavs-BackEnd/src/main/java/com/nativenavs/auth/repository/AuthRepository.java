package com.nativenavs.auth.repository;

import com.nativenavs.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserDTO, Integer> {
}
