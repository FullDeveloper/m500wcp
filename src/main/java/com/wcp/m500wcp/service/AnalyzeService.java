package com.wcp.m500wcp.service;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 16:29
 * Description:
 */
public interface AnalyzeService {

    /**
     * 根据当前和值、指定一个期数范围，查找该和值下一次和值的数有哪些。 分别几率多少
     * @param currentSumValue 当前开奖和值
     * @param startIssue 期数开始
     * @param endIssue 期数结束
     * @return
     */
    String analyzeByCurrentSumValue(Integer currentSumValue,String startIssue,String endIssue);

}
