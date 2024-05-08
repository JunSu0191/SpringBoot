package com.aloha.security5.service;

import com.aloha.security5.dto.Users;

public interface UserService {
    
    // 회원가입
    public int join(Users user) throws Exception;
}
