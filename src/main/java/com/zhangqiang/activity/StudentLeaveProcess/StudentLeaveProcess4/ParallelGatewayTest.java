package com.zhangqiang.activity.StudentLeaveProcess.StudentLeaveProcess4;

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
public class ParallelGatewayTest {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    @RequestMapping("/StudentLeaveProcess5_deploy")
    public String deploy(){
        Deployment deployment=repositoryService.createDeployment()
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess4/StudentLeaveProcess5.bpmn") // 加载资源文件
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess4/StudentLeaveProcess5.png") // 加载资源文件
                .name("学生请假流程5") // 流程名称
                .deploy(); // 部署
        System.out.println("流程部署ID"+deployment.getId());
        System.out.println("流程部署Name"+deployment.getName());
        System.out.println("流程部署Key"+deployment.getKey());
        System.out.println("流程部署Date"+deployment.getDeploymentTime());
        return "StudentLeaveProcess5_deploy";
    }
    @RequestMapping("/StudentLeaveProcess5_start")
    public String start(){
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("studentLeaveProcess5");
        System.out.println("流程实例ID"+processInstance);
        System.out.println("流程定义ID");
        return "StudentLeaveProcess5_start";
    }

    /**
     * 查看任务
     */
    @RequestMapping("/StudentLeaveProcess5_findTask")
    public String  findTask(){
        List<Task> taskList=taskService // 任务相关Service
                .createTaskQuery() // 创建任务查询
                .taskAssignee("王五") // 指定某个人
                .list();
        for(Task task:taskList){
            System.out.println("任务ID:"+task.getId());
            System.out.println("任务名称:"+task.getName());
            System.out.println("任务创建时间:"+task.getCreateTime());
            System.out.println("任务委派人:"+task.getAssignee());
            System.out.println("流程实例ID:"+task.getProcessInstanceId());
        }
        return "StudentLeaveProcess5_findTask";
    }
    /**
     * 完成任务
     */
    @RequestMapping("/StudentLeaveProcess5_completeTask")
    public String  completeTask(){
       taskService.complete("85005");
        return "StudentLeaveProcess5_completeTask";
    }

    /**
     * 完成任务
     */
    @RequestMapping("/StudentLeaveProcess5_completeTask1")
    public String  completeTask1(){
        taskService.complete("87504");
        return "StudentLeaveProcess5_completeTask";
    }

    /**
     * 完成任务
     */
    @RequestMapping("/StudentLeaveProcess5_completeTask2")
    public String  completeTask23(){
        taskService.complete("87507");
        return "StudentLeaveProcess5_completeTask";
    }





}
