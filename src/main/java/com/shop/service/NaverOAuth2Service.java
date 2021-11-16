package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NaverOAuth2Service implements OAuth2ServiceType {

    private final ObjectMapper objectMapper;

    @Value("${oauth.naver.client.id}")
    private String clientId;

    @Value("${oauth.naver.client.secret}")
    private String clientSecret;

    @Value("${oauth.naver.url.authorize}")
    private String authorizeUrl;

    @Value("${oauth.naver.url.access_token}")
    private String accessTokenUrl;

    @Value("${oauth.naver.url.user_info}")
    private String userInfoUrl;

    @Value("${oauth.naver.url.redirect}")
    private String redirectUrl;

    @Override
    public String getRedirectURL() {
        SecureRandom random = new SecureRandom();

        String stateCode = new BigInteger(130, random).toString();

        String encodeRedirectUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);

        StringBuilder stringBuilder = new StringBuilder(authorizeUrl);
        stringBuilder.append("?response_type=code");
        stringBuilder.append("&client_id=");
        stringBuilder.append(clientId);
        stringBuilder.append("&redirect_uri=");
        stringBuilder.append(encodeRedirectUrl);
        stringBuilder.append("&state=");
        stringBuilder.append(stateCode);

        return stringBuilder.toString();
    }

    @Override
    public String getToken(String authorizationCode) throws Exception {
        return this.getToken(null, authorizationCode);
    }

    public String getToken(String stateCode, String authorizationCode) throws Exception {
        String encodeRedirectUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);

        StringBuilder stringBuilder = new StringBuilder(accessTokenUrl);
        stringBuilder.append("?grant_type=authorization_code");
        stringBuilder.append("&client_id=");
        stringBuilder.append(clientId);
        stringBuilder.append("&client_secret=");
        stringBuilder.append(clientSecret);
        stringBuilder.append("&redirect_uri=");
        stringBuilder.append(encodeRedirectUrl);
        stringBuilder.append("&code=");
        stringBuilder.append(authorizationCode);
        stringBuilder.append("&state=");
        stringBuilder.append(stateCode);

        Map<String, Object> response = this.sendRequest(stringBuilder.toString(), HttpURLConnection.HTTP_OK);

        return (String) response.get("access_token");
    }

    @Override
    public Map<String, String> getUserInfo(String accessToken) throws Exception {
        Map<String, String> requestHeaders = new HashMap<>();

        requestHeaders.put("Authorization", "Bearer " + accessToken);

        Map<String, Object> response = this.sendRequest(userInfoUrl, HttpURLConnection.HTTP_OK, requestHeaders);
        Map<String, String> userInfo = (Map<String, String>) response.get("response");

        return userInfo;
    }

    private Map<String, Object> sendRequest(String requestUrl, int expectResponseCode) throws Exception {
        return this.sendRequest(requestUrl, expectResponseCode, null);
    }

    private Map<String, Object> sendRequest(String requestUrl, int expectResponseCode, Map<String, String> requestHeaders) throws Exception {
        URL url = new URL(requestUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        if(requestHeaders != null) {
            for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        int responseCode = conn.getResponseCode();

        BufferedReader br;

        if(responseCode == expectResponseCode) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String line;
        StringBuffer content = new StringBuffer();

        while((line = br.readLine()) != null) {
            content.append(line);
        }

        br.close();

        Map<String, Object> json = objectMapper.readValue(content.toString(), new TypeReference<Map<String, Object>>() {});

        if(responseCode == expectResponseCode) {
            return json;
        } else {
            throw new IllegalStateException((String) json.getOrDefault("error_description", ""));
        }
    }

}
