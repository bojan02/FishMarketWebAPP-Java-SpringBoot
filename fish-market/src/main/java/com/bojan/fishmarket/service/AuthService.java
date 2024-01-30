package com.bojan.fishmarket.service;

import com.bojan.fishmarket.dto.LoginDTO;
import com.bojan.fishmarket.dto.RegisterDTO;

public interface AuthService {
    String register (RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);
}
