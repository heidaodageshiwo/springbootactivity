package com.zhangqiang.activity.act_re_procdef;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution_list")
public class act_ru_execution_query {
/*    在开发中，我们有时候需要查看下某个流程实例的状态，运行中 Or 执行结束 ？
    这时候我们可以用流程实例Id去运行时执行表去查，假如能查到数据，说明流程实例还是运行，假如没查到
    ，就说明这个流程实例已经运行结束了；*/
    @Autowired
    RuntimeService runtimeService;
    @RequestMapping("/execution_list")
    /**
     * 查询流程状态（正在执行 or 已经执行结束）
     */
    public String processState(){
        ProcessInstance pi=runtimeService // 获取运行时Service
                .createProcessInstanceQuery() // 创建流程实例查询
                .processInstanceId("17505") // 用流程实例ID查询
                .singleResult();
        if(pi!=null){
            System.out.println("流程正在执行！");
        }else{
            System.out.println("流程已经执行结束！");
        }
        return "processState";
    }
}
