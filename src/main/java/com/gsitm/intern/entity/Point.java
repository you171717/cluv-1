//package com.gsitm.intern.entity;
//
//import com.gsitm.intern.constant.Role;
//import com.gsitm.intern.dto.MemberFormDto;
//import com.gsitm.intern.dto.PointDto;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "point")
//@Getter
//@Setter
//@ToString
//public class Point extends BaseEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "point_id")
//    private int point;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//
//    private Date death_line_time;
//
//    public static Point createPoint(PointDto pointDtoDto){
//        Point point = new Point();
//
//        point.setPoint(PointDto.getPoint());
//
//        return point;
//    }
//}
