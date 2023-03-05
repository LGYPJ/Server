package com.garamgaebi.GaramgaebiServer.domain.member.service;

import java.io.IOException;
import java.util.Map;

public interface KakaoService {
    public String getToken(String code) throws IOException;

    public Map<String, Object> getUserInfo(String access_token) throws IOException;
}
