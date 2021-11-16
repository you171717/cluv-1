package com.shop.repository;

import com.shop.entity.OAuth2MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2MemberInfoRepository extends JpaRepository<OAuth2MemberInfo, String> {

    OAuth2MemberInfo findByEmail(String email);

}
