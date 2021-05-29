package com.cfww.croiffle.entity;

import com.cfww.croiffle.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "oAuth2Id", "email", "nickname", "introduction", "wantLocation", "role"})
public class Buyer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String oAuth2Id;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String profileImageUrl;

    private String introduction;

    private String wantLocation;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
