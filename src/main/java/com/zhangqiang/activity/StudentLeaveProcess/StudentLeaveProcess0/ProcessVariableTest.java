package com.zhangqiang.activity.StudentLeaveProcess.StudentLeaveProcess0;

import java.util.Date;
import java.util.List;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessVariableTest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    //删除所有的流程
    @RequestMapping("/studentleaveprocess0_deleteAll")
    public void deleteAll(){
      List<ProcessDefinition> p= repositoryService.createProcessDefinitionQuery()
          .processDefinitionKey("MySencondProcess")
          .list();
      for(ProcessDefinition processDefinition:p){
          repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);
      }
    }

    @RequestMapping("/studentleaveprocess0_deploy")
    public String  deploy(){
      Deployment deployment= repositoryService .createDeployment()  // 创建部署
                .addClasspathResource("StudentLeaveProcess0/StudentLeaveProcess0.bpmn")  // 加载资源文件
                .addClasspathResource("StudentLeaveProcess0/StudentLeaveProcess0.png")   // 加载资源文件
                .name("学生请假流程")  // 流程名称
                .deploy(); // 部署
        System.out.println("流程部署ID:"+deployment.getId());
        System.out.println("流程部署Name:"+deployment.getName());
        return "studentleaveprocess_deploy";
     /*   流程部署ID:30001
        流程部署Name:学生请假流程*/
    }

    @RequestMapping("/studentleaveprocess_start")
    public String start(){
      ProcessInstance processInstance= runtimeService.startProcessInstanceByKey("studentLeaveProcess0");
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
//    下面我们来设置下流程变量：
    @RequestMapping("/studentleaveprocess_setVariablesValues")
    public String setVariablesValues(){
        String taskId="20005"; // 任务id
        taskService.setVariableLocal(taskId, "days", 2); // 存Integer类型
        taskService.setVariable(taskId, "date", new Date()); // 存日期类型
        taskService.setVariable(taskId, "reason", "病假"); // 存字符串
        Student student=new Student();
        student.setId(1);
        student.setName("张三");
        taskService.setVariable(taskId, "student", student);  // 存序列化对象
        return "studentleaveprocess_setVariablesValues";
    }

    @RequestMapping("/studentleaveprocess_completeTask")
    public String completeTask(){
      taskService.complete("20005");
        return "studentleaveprocess_completeTask";
    }


    @RequestMapping("/studentleaveprocess_getVariablesValue")
    public String getVariablesValue(){
        String taskId="25002"; // 任务id
        Integer days=(Integer) taskService.getVariable(taskId, "days");
        Date date=(Date) taskService.getVariable(taskId, "date");
        String reason=(String) taskService.getVariable(taskId, "reason");
        Student student=(Student) taskService.getVariable(taskId, "student");
        System.out.println("请假天数："+days);
        System.out.println("请假日期："+date);
        System.out.println("请假原因："+reason);
        System.out.println("请假对象："+student.getId()+","+student.getName());
        return "studentleaveprocess_getVariablesValue";
    }
    @RequestMapping("/studentleaveprocess_completeTask25002")
    public String studentleaveprocess_completeTask25002(){
        taskService.complete("25002");
        return "studentleaveprocess_completeTask25002";
    }
}
