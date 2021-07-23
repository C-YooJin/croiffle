package com.cfww.croiffle.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name = "item")
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private Date uploadDate;

    private String Sale;    // 구분(매도, 임대, ..)

    private String province;    // 도

    private String city;    // 시군구

    private String typeOfSale;  // 계약 형태(매매, 임대차, ..)

    private String typeOfProperty;  // 매물 유형(토지, 상가, 단독주택, ..)

    private int landArea;   // 대지면적

    private int rentalArea;    // 임대면적

    private int netLeasableArea;     // 전용면적

    private int cost;   // 거래 대금

    private int deposit;    // 보증금

    private int monthlyLease;    // 월세

    private String link;    // 매물 링크
}
