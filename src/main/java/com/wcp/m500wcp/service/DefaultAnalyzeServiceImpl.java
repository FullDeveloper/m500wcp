package com.wcp.m500wcp.service;

import com.wcp.m500wcp.entity.LotteryOpen;
import com.wcp.m500wcp.repository.LotteryOpenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: zrb
 * Date: 2018/6/13
 * Time: 16:31
 * Description:
 */
@Service
public class DefaultAnalyzeServiceImpl implements AnalyzeService {

    @Autowired
    private LotteryOpenRepository repository;

    @Override
    public String analyzeByCurrentSumValue(Integer currentSumValue, String startIssue, String endIssue) {

        List<LotteryOpen> lotteryOpens = repository.findAll(new Specification<LotteryOpen>() {
            @Override
            public Predicate toPredicate(Root<LotteryOpen> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();

                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("issueNo").as(String.class), startIssue));
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("issueNo").as(String.class), endIssue));
                list.add(criteriaBuilder.equal(root.get("sum").as(String.class), currentSumValue));

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });

        for(LotteryOpen open: lotteryOpens) {
            System.out.println("当前值：" + open.getSum());
            System.out.println("下次出现的值：" + open.getNextSum());
        }
        return null;
    }
}
