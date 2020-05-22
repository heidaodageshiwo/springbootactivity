package com.zhangqiang.activity.StudentLeaveProcess.StudentLeaveProcess1;

import java.util.Date;
import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessVariableTest1 {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;
  @Autowired
  HistoryService historyService;

    @Autowired
    TaskService taskService;

    //删除所有的流程
    @RequestMapping("/studentleaveprocess1_deleteAll")
    public void deleteAll(){
      List<ProcessDefinition> p= repositoryService.createProcessDefinitionQuery()
          .processDefinitionKey("studentLeaveProcess1")
          .list();
      for(ProcessDefinition processDefinition:p){
          repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);
      }
    }

    @RequestMapping("/studentleaveprocess1_deploy")
    public String  deploy(){
      Deployment deployment= repositoryService .createDeployment()  // 创建部署
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess1/StudentLeaveProcess1.bpmn")  // 加载资源文件
                .addClasspathResource("StudentLeaveProcess/StudentLeaveProcess1/StudentLeaveProcess1.png")   // 加载资源文件
                .name("学生请假流程")  // 流程名称
                .deploy(); // 部署
        System.out.println("流程部署ID:"+deployment.getId());
        System.out.println("流程部署Name:"+deployment.getName());
        return "studentleaveprocess_deploy";
     /*   流程部署ID:30001
        流程部署Name:学生请假流程*/
    }

    @RequestMapping("/studentleaveprocess1_start")
    public String start(){
      ProcessInstance processInstance= runtimeService.startProcessInstanceByKey("studentLeaveProcess1");
        System.out.println("流程任务ID:"+processInstance.getDeploymentId());
        System.out.println("流程任务ID:"+processInstance.getTenantId());
        System.out.println("流程任务ID:"+processInstance.getActivityId());
        System.out.println("流程任务ID:"+processInstance.getParentId());
        System.out.println("流程任务ID:"+processInstance.getProcessInstanceId());
        System.out.println("流程任务ID:"+processInstance.getStartUserId());
        System.out.println("流程任务ID:"+processInstance.getRootProcessInstanceId());
        System.out.println("流程任务ID:"+processInstance.getSuperExecutionId());
        System.out.println("--------------------------------------------------------");


        System.out.println("流程实例ID:"+processInstance.getId());
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
        return "studentleaveprocess_start";
        /*
        流程实例ID:30005
        流程定义ID:studentLeaveProcess:2:30004*/
    }


    @RequestMapping("/studentleaveprocess_completeTask1")
    public String completeTask(){
      taskService.complete("40009");
        return "studentleaveprocess_completeTask";
    }

    @RequestMapping("/studentleaveprocess_completeTask250021")
    public String studentleaveprocess_completeTask25002(){
        taskService.complete("42502");
        return "studentleaveprocess_completeTask25002";
    }
    @RequestMapping("/studentleaveprocess_completeTask2500211")
    public String studentleaveprocess_completeTask250021(){
        taskService.complete("45002");
        return "studentleaveprocess_completeTask25002";
    }


  /**
   * 历史活动查询
   */
  @RequestMapping("/studentleaveprocess1_his1")
  public void historyActInstanceList(){
    List<HistoricActivityInstance> list=historyService // 历史任务Service
        .createHistoricActivityInstanceQuery() // 创建历史活动实例查询
        .processInstanceId("40005") // 指定流程实例id
        .finished() // 查询已经完成的任务
        .list();
    for(HistoricActivityInstance hai:list){
      System.out.println("任务ID:"+hai.getId());
      System.out.println("流程实例ID:"+hai.getProcessInstanceId());
      System.out.println("活动名称："+hai.getActivityName());
      System.out.println("办理人："+hai.getAssignee());
      System.out.println("开始时间："+hai.getStartTime());
      System.out.println("结束时间："+hai.getEndTime());
      System.out.println("===========================");
    }
  }
}
