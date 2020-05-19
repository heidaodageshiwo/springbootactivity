package com.zhangqiang.activity.act_re_procdef;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class act_hi_procinst_query {
    /*开发中 有时候我们也需要通过流程实例ID来查询历史流程实例。
其实本质就是查询历史流程实例表；
这里有一点说下 这个表的id和流程实例id始终是一样的。所以Activiti没有提供获取流程实例id的接口；
因为直接getId()获取的值和流程实例Id是一样的；     */
    @Autowired
    HistoryService historyService;
    @RequestMapping("/getHistoryProcessInstance")
    /**
     * 查询历史流程实例
     */
    public String getHistoryProcessInstance(){
        HistoricProcessInstance hpi= historyService // 历史任务Service
                .createHistoricProcessInstanceQuery() // 创建历史流程实例查询
                .processInstanceId("17505") // 指定流程实例ID
                .singleResult();
        System.out.println("流程实例ID:"+hpi.getId());
        System.out.println("创建时间："+hpi.getStartTime());
        System.out.println("结束时间："+hpi.getEndTime());
        return "getHistoryProcessInstance";
    }
}
