package com.dihari.majduri.DihariMajduri.web.dao;

import com.dihari.majduri.DihariMajduri.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
