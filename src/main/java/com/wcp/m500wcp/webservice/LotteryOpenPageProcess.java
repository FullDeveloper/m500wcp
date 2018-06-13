package com.wcp.m500wcp.webservice;

import com.wcp.m500wcp.entity.LotteryOpen;
import com.wcp.m500wcp.repository.LotteryOpenRepository;
import com.wcp.m500wcp.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 16:57
 * Description:
 */
@Slf4j
@Service
public class LotteryOpenPageProcess extends IWebService implements WcpPageProcess {

    private static final String URL = "https://zst.cjcp.com.cn/cjwk3/view/kuai3_hezhi2-.html";

    @Autowired
    private LotteryOpenRepository repository;

    @Override
    public void processByUrl() throws IOException {
        Map<String, String> query = new HashMap<>();


        //获取请求连接
        Connection con = Jsoup.connect(URL);

        //遍历生成参数

        con.data("startqi", "20180101010");
        con.data("endqi", "20180613051");
        con.data("searchType", "9");

        Document doc = con.post();
        //获取网页里面class为zumc的那段代码
        Element element = doc.getElementById("pagedata");
        List<Node> nodeList = element.childNodes();
        for (int i = 0; i < nodeList.size(); i++) {
            if (i == 0) {
                continue;
            }
            Node node = nodeList.get(i);
            Element node1 = (Element) node;
            List<Node> nodes = node.childNodes();

            Element issue = (Element) nodes.get(1); //期号
            System.out.println("期号：" + issue.text());
            Element open = (Element) nodes.get(2);   //和值
            System.out.println("奖号:" + open.text());
            Element sum = (Element) nodes.get(3);   //和值
            System.out.println("和值:" + sum.text());

            //.select()


        }

        //System.out.println(doc);
    }

    @Override
    public void processByDocument(String startIssue, String endIssue) throws IOException {
        Map map = new HashMap();
        map.put("startqi", startIssue);
        map.put("endqi", endIssue);
        map.put("searchType", "9");

        String result = IWebService.sendPost(URL, map);

        Document doc = Jsoup.parse(result);
        //获取网页里面class为zumc的那段代码
        Element element = doc.getElementById("pagedata");
        List<Node> nodeList = element.childNodes();
        List<LotteryOpen> lotteryOpens = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++) {
            if (i == 0) {
                continue;
            }
            Node node = nodeList.get(i);
            List<Node> nodes = node.childNodes();
            Element issue = (Element) nodes.get(1); //期号
            Element open = (Element) nodes.get(2);   //和值
            Element sum = (Element) nodes.get(3);   //和值
            log.info("期号=>" + issue.text() + "; 奖号=>" + open.text() + "; 和值=>" + sum.text());

            LotteryOpen lotteryOpen = repository.findByIssueNo(issue.text());
            if (lotteryOpen != null) {
                continue;
            }

            lotteryOpen = new LotteryOpen();
            lotteryOpen.setIssueNo(issue.text() + "");
            lotteryOpen.setFirstNo(Integer.parseInt(open.text().charAt(0) + ""));
            lotteryOpen.setSecondNo(Integer.parseInt(open.text().charAt(1) + ""));
            lotteryOpen.setThirdNo(Integer.parseInt(open.text().charAt(2) + ""));
            lotteryOpen.setSum(Integer.parseInt(sum.text()));
            lotteryOpen.setLotteryCode("1401");
            lotteryOpen.setLotteryOpen(open.text());

            if (lotteryOpen.getSum() >= 3 && lotteryOpen.getSum() <= 10) {
                lotteryOpen.setMagnitude("1");  // 小
            } else if (lotteryOpen.getSum() >= 11 && lotteryOpen.getSum() <= 18) {
                lotteryOpen.setMagnitude("0");  // 小
            } else {
                lotteryOpen.setMagnitude("2"); //数据错误
            }

            if (lotteryOpen.getSum() % 2 == 0) {
                lotteryOpen.setSingleOrDouble("1");
            } else {
                lotteryOpen.setSingleOrDouble("0");
            }

            Long preIssueNo = Long.parseLong(lotteryOpen.getIssueNo()) - 1;

            LotteryOpen preOpen = repository.findByIssueNo(preIssueNo + "");
// 计算本次开奖时间和上次开奖时间秒数
            if (preOpen != null) {
                // 记录本次的和值
                preOpen.setNextSum(lotteryOpen.getSum());
                repository.save(preOpen);
            }
            log.info("obj => " + mapper.writeValueAsString(lotteryOpen));
            //repository.save(lotteryOpen);
            lotteryOpens.add(lotteryOpen);

        }
        repository.saveAll(lotteryOpens);

    }

    public static void main(String[] args) throws IOException {
        LotteryOpenPageProcess process = new LotteryOpenPageProcess();

        process.processByDocument("", "");
        Map map = new HashMap();
        map.put("startqi","20170421079");
        map.put("endqi","20170421081");
        map.put("searchType","9");

        String result = IWebService.sendPost(URL,map);
        System.out.println(result);
    }

}
