//상품의 이미지 정보를 저장하기 위한 인터페이스
package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    //테스트 코드를 위해
    //매개변수 : 넘겨준 상품 아이디
    //상품 이미지 아이디 오름차순으로 가져옴
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
