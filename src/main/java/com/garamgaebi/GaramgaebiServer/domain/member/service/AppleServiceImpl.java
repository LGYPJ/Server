package com.garamgaebi.GaramgaebiServer.domain.member.service;

import com.garamgaebi.GaramgaebiServer.global.response.exception.ErrorCode;
import com.garamgaebi.GaramgaebiServer.global.response.exception.RestApiException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;

@Service
public class AppleServiceImpl implements AppleService{
    /**
     * 1. apple로부터 공개키 3개 가져오기
     * 2. 클라이언트에서 받은 idToken과 비교하여 사용할 공개키 확인 (equals kid & alg)
     * 3. 해당 공개키 재료들로 새로운 공개키를 만들기
     * 4. 만든 공개키의 JWT token body를 decode하여 유저 정보 얻기
     */
    public String getUserIdFromApple(String idToken) {
        StringBuffer result = new StringBuffer();
        try {
            /* 1. apple로부터 공개키 3개 가져오기 */
            URL url = new URL("https://appleid.apple.com/auth/keys");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JSONParser parser = new JSONParser();
            JSONObject keys = (JSONObject) parser.parse(result.toString());
            JSONArray keyArray = (JSONArray) keys.get("keys");

            System.out.println("1. DEBUG: 애플에서 가져온 공개키들 \n" + keyArray);

            /* 2. 클라이언트에서 받은 idToken과 비교하여 사용할 공개키 확인 (equals kid & alg) */
            // 클라이언트로부터 받은 idToken decode
            String[] decodeArray = idToken.split("\\.");
            String header = new String(Base64.getDecoder().decode(decodeArray[0]));

            // apple에서 제공해주는 kid 값과 일치하는지 비교
            JsonElement kid = ((JsonObject) parser.parse(header)).get("kid");
            JsonElement alg = ((JsonObject) parser.parse(header)).get("alg");

            // 사용해야하는 Element 추출 (kid & alg 값이 일치하는 element)
            JSONObject availableObject = null;
            for (int i = 0; i < keyArray.toList().size(); i++) {
                JSONObject appleObject = (JSONObject) keyArray.get(i);
                JsonElement appleKid = (JsonElement) appleObject.get("kid");
                JsonElement appleAlg = (JsonElement) appleObject.get("alg");

                if (Objects.equals(appleKid, kid) && Objects.equals(appleAlg, alg)) {
                    availableObject = appleObject;
                    break;
                }
            }

            System.out.println("2. DEBUG: 사용할 element \n" + availableObject);

            // 일치하는 공개키 없음
            if (ObjectUtils.isEmpty(availableObject)) {
                System.out.println("일치하는 공개키가 없습니다.");
            }

            /* 3. 해당 공개키 재료들로 새로운 공개키를 만들기 */
            PublicKey publicKey = this.getPublicKey(availableObject);

            System.out.println("3. DEBUG: 새로 만든 공개키 \n" + publicKey);

            /* 4. 만든 공개키의 JWT token body를 decode하여 유저 정보 얻기 */
            Claims userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(idToken).getBody();
            JSONObject userInfoObject = (JSONObject) parser.parse(new Gson().toJson(userInfo));
            JsonElement appleAlg = (JsonElement) userInfoObject.get("sub");
            String userId = appleAlg.getAsString();

            System.out.println("4. DEBUG: userId \n" + userId);

            return userId;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public PublicKey getPublicKey(JSONObject object) {
        String nStr = object.get("n").toString();
        String eStr = object.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
