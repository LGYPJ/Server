package com.garamgaebi.GaramgaebiServer.domain.member.service;

import org.json.simple.JSONObject;

import java.security.PublicKey;

public interface AppleService {
    public String getUserIdFromApple(String idToken);
    public PublicKey getPublicKey(JSONObject object);
}
