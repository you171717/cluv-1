package com.gsitm.intern.repository;

import com.gsitm.intern.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String Email);
}
