package com.shop.service;

import java.util.Map;

public interface OAuth2ServiceType {

    String getRedirectURL();

    String getToken(String authorizationCode) throws Exception;

    Map<String, String> getUserInfo(String accessToken) throws Exception;

}
