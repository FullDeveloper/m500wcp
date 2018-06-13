package com.wcp.m500wcp.webservice.data.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 11:47
 * Description:
 */
@Data
public class GetLotteryOpenDetail {

    @JsonProperty(value = "IssueNo")
    private String issueNo;

    @JsonProperty(value = "LotteryOpen")
    private String lotteryOpen;

    @JsonProperty(value = "OpenTime")
    private String openTime;



}
