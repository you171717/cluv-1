package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private String Id;

    private String name;

    private String phone;

    private String address;

    @Override
    public String toString() {
        return "AddressDto{" +
                "memberId='" + Id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
