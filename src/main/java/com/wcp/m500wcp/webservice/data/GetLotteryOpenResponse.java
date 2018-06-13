package com.wcp.m500wcp.webservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wcp.m500wcp.webservice.data.detail.GetLotteryOpenDetail;
import lombok.Data;

import java.util.List;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 11:46
 * Description:
 */
@Data
public class GetLotteryOpenResponse extends BaseResponse {

    @JsonProperty(value = "BackData")
    private List<GetLotteryOpenDetail> BackData;

}
