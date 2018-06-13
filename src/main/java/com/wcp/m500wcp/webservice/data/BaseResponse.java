package com.wcp.m500wcp.webservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 11:45
 * Description:
 */
@Data
public class BaseResponse {

    @JsonProperty(value = "Code")
    private String Code;
    @JsonProperty(value = "StrCode")
    private String StrCode;
    @JsonProperty(value = "DataCount")
    private String DataCount;
    @JsonProperty(value = "BackUrl")
    private String BackUrl;
    @JsonProperty(value = "Data")
    private String Data;
    @JsonProperty(value = "CacheData")
    private String CacheData;
    @JsonProperty(value = "OpenType")
    private String OpenType;
    @JsonProperty(value = "Style")
    private String Style;
}
