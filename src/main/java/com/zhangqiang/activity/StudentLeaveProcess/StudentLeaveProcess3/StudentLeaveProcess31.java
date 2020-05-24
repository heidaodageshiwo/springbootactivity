package com.zhangqiang.activity.StudentLeaveProcess.StudentLeaveProcess3;

import java.util.HashMap;
import java.util.List;
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
public class StudentLeaveProcess31 {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    @RequestMapping("/StudentLeaveProcess31_deploy")
    public String deploy() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess3/StudentLeaveProcess3.bpmn")  // 加载资源文件
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess3/StudentLeaveProcess3.png")   // 加载资源文件
                .name("学生请假流程2")  // 流程名称
                .deploy();
        System.out.println("流程部署ID:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
        return "StudentLeaveProcess31_deploy";
    }
//    studentLevaeProcess2

    @RequestMapping("/StudentLeaveProcess3_start")
    public String start() {
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey("studentLevaeProcess3");
        System.out.println("流程实例ID" + processInstance.getId());
        System.out.println("流程定义ID" + processInstance.getProcessDefinitionId());
        return "StudentLeaveProcess3_start";
    }


    @RequestMapping("/StudentLeaveProcess3_findTask1")
    public String StudentLeaveProcess21_findTask1() {
        List<Task> taskList = taskService // 任务相关Service
            .createTaskQuery() // 创建任务查询
            .taskAssignee("张三") // 指定某个人
            .list();
        for (Task task : taskList) {
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务创建时间:" + task.getCreateTime());
            System.out.println("任务委派人:" + task.getAssignee());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
        }
        return "StudentLeaveProcess3_findTask1";
    }
    @RequestMapping("/StudentLeaveProcess3_completeTask222")
    public String StudentLeaveProcess2_completeTask222() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("days", 8);
        taskService.complete("62509",variables);
        return "StudentLeaveProcess3_completeTask222";

    }
    @RequestMapping("/StudentLeaveProcess3_completeTask211122")
    public String StudentLeaveProcess2_completeTask2111122() {
        Map<String, Object> variables = new HashMap<String, Object>();
        taskService.complete("60004");
        return "StudentLeaveProcess3_completeTask221112";

    }
}
