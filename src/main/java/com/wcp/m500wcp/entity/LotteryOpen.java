package com.wcp.m500wcp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 14:01
 * Description:
 */
@Entity
@Data
public class LotteryOpen {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition=("varchar(20)  default null comment '期号'"))
    private String issueNo;

    @Column(columnDefinition=("varchar(20)  default null comment '开奖数字'"))
    private String lotteryOpen;

    @Column(columnDefinition=("varchar(20)  default null comment '数字一'"))
    private Integer firstNo;

    @Column(columnDefinition=("varchar(20)  default null comment '数字二'"))
    private Integer secondNo;

    @Column(columnDefinition=("varchar(20)  default null comment '数字三'"))
    private Integer thirdNo;

    @Column(columnDefinition=("varchar(20)  default null comment '和'"))
    private Integer sum;

    @Column(columnDefinition=("varchar(20)  default null comment '开奖时间'"))
    private String openTime;

    @Column(columnDefinition=("varchar(20)  default null comment '和大小 0：大 1：小'"))
    private String magnitude;

    @Column(columnDefinition=("varchar(20)  default null comment '和单双 0：单 1：双'"))
    private String singleOrDouble;

    @Column(columnDefinition=("varchar(20)  default null comment '彩票代码'"))
    private String LotteryCode; //江苏快三编号1403

    @Column(columnDefinition=("varchar(20)  default null comment '与上一次开奖间隔时间'"))
    private Integer openSeconds;

    @Column(columnDefinition=("varchar(20)  default null comment '下一次开奖的和值'"))
    private Integer nextSum;

}
