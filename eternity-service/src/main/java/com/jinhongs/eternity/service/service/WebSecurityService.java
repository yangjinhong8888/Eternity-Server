package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.service.model.dto.SecurityUserInfoDTO;

public interface WebSecurityService {
    SecurityUserInfoDTO getSecurityUserInfoByUsername(String username);
}
