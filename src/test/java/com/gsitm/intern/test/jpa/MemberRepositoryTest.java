package com.gsitm.intern.test.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    EntityManager em;
    @PersistenceUnit
    EntityManagerFactory emf;
    public void createUserList(){
        for (long i = 1; i <= 30; i++) {
            JpaEntity jett = new JpaEntity(i,"0109442094"+i,"hiatus122"+i);
            JpaEntity savedMember = memberRepository.save(jett);
            em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                tx.commit();
            }catch (Exception e){
                System.out.println("Do not Transaction Commit");
                tx.rollback();
            }finally{
                em.close();
            }

            System.out.println(savedMember.toString());
        }
    }

    @Test
    @DisplayName("All Users Test")
    public void findAllTest(){
        System.out.println(":::All User Test:::");
        this.createUserList();
        List<JpaEntity> userList = memberRepository.findAll();
        for(JpaEntity jett : userList){
            System.out.println(jett.getId());
            System.out.println(jett.getName());
            System.out.println(jett.getPhone());
        }
    }



//    @Test
//    @DisplayName("composite Test")
//    public void compositeTest() {
//        System.out.println(":::All User Test:::");
//        this.createUserList();
//        List<JpaEntity> userList = memberRepository.findAll();
//        for(JpaEntity jett : userList){
//            System.out.println(jett.getJpaCompositeKey().getId());
//            System.out.println(jett.getName());
//            System.out.println(jett.getJpaCompositeKey().getPhone());
//        }
//    }



}