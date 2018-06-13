package com.wcp.m500wcp.repository;

import com.wcp.m500wcp.entity.LotteryOpen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 14:06
 * Description:
 */
public interface LotteryOpenRepository extends JpaRepository<LotteryOpen,Long>,JpaSpecificationExecutor<LotteryOpen> {


    LotteryOpen findByIssueNo(String issueNo);

}
