package com.shop.repository;


import com.shop.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

//    List<Tag> findByTagNm(String s);

}
