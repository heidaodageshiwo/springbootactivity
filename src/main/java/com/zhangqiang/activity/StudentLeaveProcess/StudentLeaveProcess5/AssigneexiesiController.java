package com.zhangqiang.activity.StudentLeaveProcess.StudentLeaveProcess5;

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
public class AssigneexiesiController {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;


//    Activiti之个人任务分配

    @RequestMapping("/Assignee_deploy")
    public String deploy(){
        Deployment deployment=repositoryService.createDeployment()
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess5/gerenrenwu1.bpmn") // 加载资源文件
                .name("学生请假流程6") // 流程名称
                .deploy();
        System.out.println("流程部署ID:"+deployment.getId());
        System.out.println("流程部署Name:"+deployment.getName());
        return "Assignee_deploy";
    }


    @RequestMapping("/Assignee_start")
    public String start() {
        Map<String,Object> variables=new HashMap<String,Object>();
        variables.put("userId", "张三");
        ProcessInstance processInstance=runtimeService.
                startProcessInstanceByKey("gerenrenwu1",variables);
        System.out.println("流程实例ID:"+processInstance.getId());
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
        return "Assignee_start";
    }


    /**
     * 查看任务
     */
    @RequestMapping("/Assignee_findTask")
    public String findTask(){
        List<Task> taskList=taskService // 任务相关Service
                .createTaskQuery() // 创建任务查询
                .taskAssignee("张三") // 指定某个人
                .list();
        for(Task task:taskList){
            System.out.println("任务ID:"+task.getId());
            System.out.println("任务名称:"+task.getName());
            System.out.println("任务创建时间:"+task.getCreateTime());
            System.out.println("任务委派人:"+task.getAssignee());
            System.out.println("流程实例ID:"+task.getProcessInstanceId());
        }
        return "Assignee_findTask";

    }


    /**
     * 完成任务
     */
    @RequestMapping("/Assignee_completeTask")
    public String completeTask(){
        taskService // 任务相关Service
                .complete("92511");
        return "Assignee_completeTask";

    }

}
