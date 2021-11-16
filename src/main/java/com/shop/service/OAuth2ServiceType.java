package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public abstract class OAuth2ServiceType {

    public abstract String getRedirectURL();

    public abstract String getToken(String authorizationCode) throws Exception;

    public abstract Map<String, String> getUserInfo(String accessToken) throws Exception;

    protected Map<String, Object> sendRequest(String requestUrl, int expectResponseCode) throws Exception {
        return this.sendRequest(requestUrl, expectResponseCode, null);
    }

    protected Map<String, Object> sendRequest(String requestUrl, int expectResponseCode, Map<String, String> requestHeaders) throws Exception {
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

        StringBuilder content = new StringBuilder();

        String line;

        while((line = br.readLine()) != null) {
            content.append(line);
        }

        br.close();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> json = objectMapper.readValue(content.toString(), new TypeReference<Map<String, Object>>() {});

        if(responseCode == expectResponseCode) {
            return json;
        } else {
            throw new IllegalStateException((String) json.getOrDefault("error_description", ""));
        }
    }

}
