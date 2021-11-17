package com.shop.config.auth.dto;

import com.shop.entity.Member;
import com.shop.entity.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUser implements Serializable{
    private String name;
    private String email;
    private String picture;

    public SessionUser(User member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
