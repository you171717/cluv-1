package com.gsitm.intern.service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class SMSServiceTest {

    @Test
    @DisplayName("SMS 전송 테스트")
    public void sendSms() {

        String api_key = "NCS1TB65HVL5RIAO";
        String api_secret = "EWBEAGXE92QMSABDLAP6BYLRNHCUZL8O";
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("to", "010-3583-7031");
        params.put("from", "010-3583-7031");
        params.put("type", "SMS");
        params.put("text", "문자 내용");
        params.put("app_version", "test app 1.2");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

}
