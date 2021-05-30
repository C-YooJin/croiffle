package com.cfww.croiffle.domain.entity;

import com.cfww.croiffle.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "oAuth2Id", "email", "nickname", "introduction", "role", "location"})
public class Broker extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_id", length = 20)
    private Long id;

    @Column(unique = true, nullable = false)
    private String oAuth2Id;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String profileImageUrl;

    private String introduction;

    // 관할 지역은 수정, 변경 가능하도록 자유도를 높였음. 따라서 nullable 설정 일단 안 함.
    @Column(unique = true)
    private String location;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;



}


