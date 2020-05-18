package com.zhangqiang.activity.helloworld;

import java.util.List;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * java类简单作用描述
 *
 * @ProjectName: activity
 * @Package: com.zhangqiang.activity.helloworld
 * @ClassName: HelloworldController
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2020-05-18 12:41
 * @UpdateUser: zhangq
 * @UpdateDate: 2020-05-18 12:41
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
@RestController
public class HelloworldController {

  //部署Service
  @Autowired
  RepositoryService repositoryService;
  //运行时流程实例Service
  @Autowired
  RuntimeService runtimeService;
  //任务相关Service
  @Autowired
  TaskService taskService;

  // * 部署流程定义
  @RequestMapping("/deployWithClassPath")
  public String deployWithClassPath() {
    Deployment deployment= repositoryService.createDeployment()
        .addClasspathResource("helloworld/Helloworld.bpmn")
        .addClasspathResource("helloworld/Helloworld.png")
        .name("HelloWorld流程")
        .deploy();
    System.out.println("流程部署ID:"+deployment.getId());
    System.out.println("流程部署Name:"+deployment.getName());
    return "deployWithClassPath";
  }
  // *  启动流程实例
  @RequestMapping("/start")
  public String start() {
    ProcessInstance processInstance= runtimeService.startProcessInstanceByKey("myFirstProcess");
    System.out.println("流程实例ID:"+processInstance.getId());
    System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
    return "start";
  }
  // *  查看任务
  @RequestMapping("/findTask")
  public String findTask() {
    List <Task> taskList= taskService.createTaskQuery().taskAssignee("张强").list();
    for(Task task:taskList){
      System.out.println("任务id:"+task.getId());
      System.out.println("任务名称:"+task.getName());
      System.out.println("任务创建时间:"+task.getCreateTime());
      System.out.println("任务委派人:"+task.getAssignee());
      System.out.println("流程定义ID:"+task.getProcessDefinitionId());
      System.out.println("流程实例ID:"+task.getProcessInstanceId());
    }

    return "findTask";
  }
  // *  完成任务
  @RequestMapping("/completeTask")
  public String completeTask() {
    taskService.complete("2505");
    return "completeTask";
  }
}
