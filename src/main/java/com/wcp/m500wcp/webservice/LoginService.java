package com.wcp.m500wcp.webservice;

import com.wcp.m500wcp.webservice.data.GetLotteryOpenResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 10:41
 * Description:
 */
public class LoginService extends IWebService {

    private static final String login_url = "/tools/ssc_ajax.ashx?A=Login&S=500cp&U=qw77555";

    public String doWcpLogin() throws IOException {

        Map params = new HashMap();
        params.put("UserName","qw77555");
        params.put("Password","cad7583211eaff5a87b6ce86d08b50af");
        params.put("Action","Login");
        params.put("Type","Hash");
        params.put("SourceName","MB");
        String result = sendPost(URL + login_url ,params);


        return result;
    }

    public static void main(String[] args) throws IOException {
        LoginService loginService = new LoginService();
        String result = loginService.doWcpLogin();
        System.out.println(result);
    }

}
