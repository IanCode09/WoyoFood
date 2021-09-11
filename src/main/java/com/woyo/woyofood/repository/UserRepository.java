package com.woyo.woyofood.repository;

import com.woyo.woyofood.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query(value = "SELECT * FROM tab_user WHERE email = ?1", nativeQuery = true)
    List<UserModel> findByEmail(String email);
}
