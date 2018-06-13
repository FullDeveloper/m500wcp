package com.wcp.m500wcp.webservice;

import java.io.IOException;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 16:53
 * Description:
 */
public interface WcpPageProcess {

    void processByUrl() throws IOException;

    void processByDocument(String startIssue,String endIssue) throws IOException;

}
