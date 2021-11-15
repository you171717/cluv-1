package com.shop.repository;

import com.shop.entity.Comment;
import com.shop.entity.FAQ;
import com.shop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c where c.inquiry.id = :inquiry_id")
    List<Comment> findByInquriyId(@Param("inquiry_id") Long inquiry_id);
}
