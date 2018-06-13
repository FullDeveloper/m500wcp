package com.wcp.m500wcp.webservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wcp.m500wcp.entity.LotteryOpen;
import com.wcp.m500wcp.repository.LotteryOpenRepository;
import com.wcp.m500wcp.util.DateUtils;
import com.wcp.m500wcp.webservice.data.GetLotteryOpenResponse;
import com.wcp.m500wcp.webservice.data.detail.GetLotteryOpenDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 11:40
 * Description:
 */
@Service
@Slf4j
public class GetLotteryOpenService extends IWebService {

    @Autowired
    private LotteryOpenRepository lotteryOpenRepository;

    private static final String GetLotteryOpen_URL = "/tools/ssc_ajax.ashx?A=GetLotteryOpen&S=500cp&U=qw77555";

    private static final String LotteryCode = "1401"; // 江苏快三编号

    public String doGetLotteryOpenData() throws IOException {
        Map params = new HashMap();
        params.put("Action", "GetLotteryOpen");
        params.put("LotteryCode", "1401");
        params.put("IssueNo", "0");
        params.put("DataNum", "100");
        params.put("SourceName", "MB");
        String result = sendPost(URL + GetLotteryOpen_URL, params);
        GetLotteryOpenResponse response = mapper.readValue(result, GetLotteryOpenResponse.class);

        dealWithData(response);

        return result;
    }

    private void dealWithData(GetLotteryOpenResponse response) throws JsonProcessingException {

        if (response.getBackData() != null && response.getBackData().size() > 0) {

            Collections.sort(response.getBackData(), (o1, o2) -> {

                if (Long.parseLong(o1.getIssueNo()) > Long.parseLong(o2.getIssueNo())) {
                    return 1;
                }
                if (Long.parseLong(o1.getIssueNo()) == Long.parseLong(o2.getIssueNo())) {
                    return 0;
                }
                return -1;
            });

            for (GetLotteryOpenDetail openDetail : response.getBackData()) {

                LotteryOpen lotteryOpen = lotteryOpenRepository.findByIssueNo(openDetail.getIssueNo());
                if (lotteryOpen != null) {
                    continue;
                }

                log.info("执行插入操作。参数为：" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(openDetail));

                lotteryOpen = new LotteryOpen();
                lotteryOpen.setIssueNo(openDetail.getIssueNo());

                String[] numbers = openDetail.getLotteryOpen().split(",");

                Integer firstNo = Integer.parseInt(numbers[0]);
                Integer secondNo = Integer.parseInt(numbers[1]);
                Integer thirdNo = Integer.parseInt(numbers[2]);

                lotteryOpen.setOpenTime(openDetail.getOpenTime());
                lotteryOpen.setLotteryOpen(openDetail.getLotteryOpen());

                lotteryOpen.setLotteryCode(LotteryCode);
                lotteryOpen.setFirstNo(firstNo);
                lotteryOpen.setSecondNo(secondNo);
                lotteryOpen.setThirdNo(thirdNo);

                lotteryOpen.setSum((firstNo + secondNo + thirdNo));

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


                //获取上次开奖时间
                Long preIssueNo = Long.parseLong(openDetail.getIssueNo()) - 1;

                LotteryOpen preOpen = lotteryOpenRepository.findByIssueNo(preIssueNo + "");

                // 计算本次开奖时间和上次开奖时间秒数
                if (preOpen != null) {
                    String preTime = preOpen.getOpenTime();
                    Date preDate = DateUtils.parseDateDateTime(preTime, DateUtils.DATETIME_FORMAT_X);
                    Date nowDate = DateUtils.parseDateDateTime(lotteryOpen.getOpenTime(), DateUtils.DATETIME_FORMAT_X);
                    int seconds = DateUtils.calLastedTime(preDate, nowDate);
                    lotteryOpen.setOpenSeconds(seconds);

                    // 记录本次的和值
                    preOpen.setNextSum(lotteryOpen.getSum());

                    lotteryOpenRepository.save(preOpen);
                }
                lotteryOpenRepository.save(lotteryOpen);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GetLotteryOpenService loginService = new GetLotteryOpenService();
        String result = loginService.doGetLotteryOpenData();
        System.out.println(result);
    }


}
