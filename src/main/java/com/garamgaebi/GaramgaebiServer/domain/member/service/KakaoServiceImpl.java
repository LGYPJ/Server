package com.garamgaebi.GaramgaebiServer.domain.member.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoServiceImpl implements KakaoService {
    @Value("${custom.kakaoClientId}")
    private String kakaoClientId;

    /**
     * 카카오 인가 코드로 토큰 받아오기
     * 사용 x
     */
    public String getToken(String code) throws IOException {
        String host = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";

        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // 데이터 기록 찍기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + kakaoClientId);
            System.out.println("debug: " + "client_id=" + kakaoClientId);
            sb.append("&redirect_uri=http://localhost:8080/member/kakao");
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result);

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();
            System.out.println("refresh_token = " + refresh_token);
            System.out.println("access_token = " + access_token);

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * access_token으로 유저 정보 가져오기
     */
    public Map<String, Object> getUserInfo(String access_token) throws IOException {
        String host = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> result = new HashMap<>();

        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while ((line=br.readLine()) != null) {
                res += line;
            }

            System.out.println("res = " + res);

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);

            String id = obj.get("id").toString();

            result.put("id", id);

            br.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}