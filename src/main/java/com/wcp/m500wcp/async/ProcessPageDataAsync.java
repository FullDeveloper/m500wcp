package com.wcp.m500wcp.async;

import com.wcp.m500wcp.util.DateUtils;
import com.wcp.m500wcp.webservice.LotteryOpenPageProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Create By Project m500wcp
 * <p>
 * author: zrb
 * date: 2018/6/13
 * TIME: 下午9:23
 * description:
 */
@Component
@Slf4j
public class ProcessPageDataAsync {

    @Autowired
    private LotteryOpenPageProcess process;

    @Async
    public Future<Boolean> asyncInvokeProcessPageDate(String date, int day) throws InterruptedException, IOException {
        String startIssue = "001";
        String endIssue = "082";
        for(int i=1;i<=day;i++){
            log.info("执行第" + i + "次");
            process.processByDocument(date + startIssue, date + endIssue);
            date = DateUtils.toDateAddDay(DateUtils.parseDateDateTime(date, "yyyyMMdd"), 1);
        }
        Future<Boolean> future = new AsyncResult<Boolean>(true);

        return future;
    }

}
