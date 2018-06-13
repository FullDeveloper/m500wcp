package com.wcp.m500wcp;

import com.wcp.m500wcp.async.ProcessPageDataAsync;
import com.wcp.m500wcp.service.AnalyzeService;
import com.wcp.m500wcp.util.DateUtils;
import com.wcp.m500wcp.webservice.GetLotteryOpenService;
import com.wcp.m500wcp.webservice.LotteryOpenPageProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = M500wcpApplication.class)
@EnableAsync
public class M500wcpApplicationTests {

	@Autowired
	private GetLotteryOpenService lotteryOpenService;
	@Autowired
	private AnalyzeService analyzeService;

	@Autowired
	private LotteryOpenPageProcess process;

	@Autowired
	private ProcessPageDataAsync async;

	@Test
	public void contextLoads() throws IOException {
		// lotteryOpenService.doGetLotteryOpenData();
		//analyzeService.analyzeByCurrentSumValue(14,"20180613047","20180613049");

		String date = "20120122";

		String startIssue = "001";
		String endIssue = "082";

		while(true) {
			try{
				process.processByDocument(date + startIssue, date + endIssue);
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("忽略错误继续执行！");
			}
			date = DateUtils.toDateAddDay(DateUtils.parseDateDateTime(date, "yyyyMMdd"), 1);
		}
	}

	@Test
	public void testAsync2013() throws InterruptedException {
		String date = "20130101";
		try{
			Future<Boolean> future = async.asyncInvokeProcessPageDate(date,365);
			date = "20140101";
			Future<Boolean> future1 = async.asyncInvokeProcessPageDate(date,365);
			date = "20150101";
			Future<Boolean> future2 = async.asyncInvokeProcessPageDate(date,365);
			date = "20160101";
			Future<Boolean> future3 = async.asyncInvokeProcessPageDate(date,365);
			date = "20170101";
			Future<Boolean> future4 = async.asyncInvokeProcessPageDate(date,365);
			date = "20180101";
			Future<Boolean> future5 = async.asyncInvokeProcessPageDate(date,365);

			System.out.println(future.get());
			System.out.println(future1.get());
			System.out.println(future2.get());
			System.out.println(future3.get());
			System.out.println(future4.get());
			System.out.println(future5.get());
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("忽略错误继续执行！");
		}

	}

}
