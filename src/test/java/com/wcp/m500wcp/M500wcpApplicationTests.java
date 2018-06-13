package com.wcp.m500wcp;

import com.wcp.m500wcp.service.AnalyzeService;
import com.wcp.m500wcp.webservice.GetLotteryOpenService;
import com.wcp.m500wcp.webservice.LotteryOpenPageProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class M500wcpApplicationTests {

	@Autowired
	private GetLotteryOpenService lotteryOpenService;
	@Autowired
	private AnalyzeService analyzeService;

	@Autowired
	private LotteryOpenPageProcess process;

	@Test
	public void contextLoads() throws IOException {
		// lotteryOpenService.doGetLotteryOpenData();
		//analyzeService.analyzeByCurrentSumValue(14,"20180613047","20180613049");

		String startIssue = "20120101001";
		String endIssue = "20120101082";




		process.processByDocument(startIssue,endIssue);
	}

}
