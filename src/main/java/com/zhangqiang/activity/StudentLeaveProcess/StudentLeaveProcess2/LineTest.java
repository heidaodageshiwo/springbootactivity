package com.zhangqiang.activity.StudentLeaveProcess.StudentLeaveProcess2;

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
public class LineTest {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    @RequestMapping("/StudentLeaveProcess2_deploy")
    public String deploy() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess2/StudentLeaveProcess2.bpmn")  // 加载资源文件
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess2/StudentLeaveProcess2.png")   // 加载资源文件
                .name("学生请假流程2")  // 流程名称
                .deploy();
        System.out.println("流程部署ID:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
        return "StudentLeaveProcess2_deploy";
    }
//    studentLevaeProcess2

    @RequestMapping("/StudentLeaveProcess2_start")
    public String start() {
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey("studentLevaeProcess2");
        System.out.println("流程实例ID" + processInstance.getId());
        System.out.println("流程定义ID" + processInstance.getProcessDefinitionId());
        return "StudentLeaveProcess2_start";
    }


    @RequestMapping("/StudentLeaveProcess21_findTask")
    public String StudentLeaveProcess21_findTask() {
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
        return "StudentLeaveProcess2_findTask";
    }
    @RequestMapping("/StudentLeaveProcess2_findTask")
    public String findTask() {
        List<Task> taskList = taskService // 任务相关Service
                .createTaskQuery() // 创建任务查询
                .taskAssignee("李四") // 指定某个人
                .list();
        for (Task task : taskList) {
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务创建时间:" + task.getCreateTime());
            System.out.println("任务委派人:" + task.getAssignee());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
        }
        return "StudentLeaveProcess2_findTask";
    }

    @RequestMapping("/StudentLeaveProcess2_completeTask")
    public String completeTask() {
        taskService.complete("50009");
        return "StudentLeaveProcess2_completeTask";

    }
 @RequestMapping("/StudentLeaveProcess21_completeTask")
    public String StudentLeaveProcess21_completeTask() {
        taskService.complete("52507");
        return "StudentLeaveProcess2_completeTask";

    }

    @RequestMapping("/StudentLeaveProcess2_completeTask222")
    public String StudentLeaveProcess2_completeTask222() {
        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("msg", "一般情况");
        variables.put("msg", "重要情况");
        taskService.complete("52502",variables);
        return "StudentLeaveProcess2_completeTask222";

    }
}
