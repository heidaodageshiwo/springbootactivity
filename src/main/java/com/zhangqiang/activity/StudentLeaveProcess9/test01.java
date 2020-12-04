package com.zhangqiang.activity.StudentLeaveProcess9;

import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test01 {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;


    @RequestMapping("/groupController_dongtai_deploy111")
    public String deploy() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("testprocesses/StudentLeaveProcess01.bpmn") // 加载资源文件
                .addClasspathResource("testprocesses/StudentLeaveProcess01.png") // 加载资源文件
                .name("学生请假流程0000") // 流程名称
                .deploy();
        System.out.println("流程部署ID:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
        return "groupController_sizhi_deploy";
    }


    @RequestMapping("/groupController_dongtai_start222")
    public String start() {
        Map<String,Object> variables=new HashMap<String,Object>();
        variables.put("groupId", "dev");
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey("studentLeaveProcess",variables);
        Task task=taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult(); // 根据流程实例Id查询任务
        taskService.complete(task.getId());  // 完成 学生填写请假单任务

        System.out.println("流程实例ID:" + processInstance.getId());
        System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());
        return "groupController_sizhi_start";
    }

}
