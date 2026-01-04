package com.rabbi.repo;

import com.rabbi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {

    User findByEmail(String email);


}
