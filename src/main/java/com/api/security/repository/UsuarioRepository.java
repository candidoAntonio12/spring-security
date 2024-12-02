package com.api.security.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.security.models.UserModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UserModel, UUID>  {
	UserModel findByEmail(String email);

}
