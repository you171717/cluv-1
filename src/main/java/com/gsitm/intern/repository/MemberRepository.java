package com.gsitm.intern.repository;

import com.gsitm.intern.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Member m set m.password = ?2 where m.id = ?1")
    void updatePassword(Long id, String password);
}
