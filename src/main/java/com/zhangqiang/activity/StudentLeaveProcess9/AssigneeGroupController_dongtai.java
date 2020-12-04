package com.zhangqiang.activity.StudentLeaveProcess9;

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
public class AssigneeGroupController_dongtai {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;


//    Activiti之多用户任务分配
//    方式二：使用流程变量


    @RequestMapping("/groupController_dongtai_deploy")
    public String deploy() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("testprocesses/StudentLevaeProcess91.bpmn") // 加载资源文件
                .addClasspathResource("testprocesses/StudentLevaeProcess91.png") // 加载资源文件
                .name("学生请假流程9") // 流程名称
                .deploy();
        System.out.println("流程部署ID:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
        return "groupController_sizhi_deploy";
    }


    @RequestMapping("/groupController_dongtai_start")
    public String start() {
        Map<String,Object> variables=new HashMap<String,Object>();
        variables.put("groupId", "dev");
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey("StudentLevaeProcess91",variables);
        System.out.println("流程实例ID:" + processInstance.getId());
        System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());
        return "groupController_sizhi_start";
    }


    /**
     * 查看任务
     */
    @RequestMapping("/groupController_dongtai_findTask")
    public String findTask() {
       /* List<Task> taskList=taskService // 任务相关Service
                .createTaskQuery() // 创建任务查询
//                .taskAssignee("李四") // 指定某个人
                .taskCandidateUser("李四") // 候选人查询
                .list();
        for(Task task:taskList){
            System.out.println("任务ID:"+task.getId());
            System.out.println("任务名称:"+task.getName());
            System.out.println("任务创建时间:"+task.getCreateTime());

            //为null，说明当前的zhangsan只是一个候选人，并不是任务的执行人
            System.out.println("任务委派人:"+task.getAssignee());

            System.out.println("流程实例ID:"+task.getProcessInstanceId());
        }*/
        //4.执行查询
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("StudentLevaeProcess91")
                .taskCandidateUser("lisi")//设置候选用户
                .list();
        //5.输出
        for (Task task : list) {
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee());//为null，说明当前的zhangsan只是一个候选人，并不是任务的执行人
        }
        return "groupController_dongtai_findTask";
    }

    //5.测试zhangsan用户，来拾取组任务
    @RequestMapping("/groupController_dongtai_shiqurenwu")
    public String Assignee_shiqurenwu() {
        //4.执行查询
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("StudentLevaeProcess91")
                .taskCandidateUser("张三")//设置候选用户
                .singleResult();
        if (task != null) {
            taskService.claim(task.getId(), "张三");//第一个参数任务ID,第二个参数为具体的候选用户名
            System.out.println("任务拾取完毕!");
        }

        return "groupController_dongtai_shiqurenwu";
    }
    /**
     * 查看任务   6.当前的用户查询自己的任务
     */
    @RequestMapping("/groupController_dongtai_zaichaxun")
    public String Assignee_shiquwan_zaichaxun() {
        List<Task> taskList=taskService // 任务相关Service
                .createTaskQuery() // 创建任务查询
                .taskAssignee("张三") // 指定某个人
                .list();
        for(Task task:taskList){
            System.out.println("任务ID:"+task.getId());
            System.out.println("任务名称:"+task.getName());
            System.out.println("任务创建时间:"+task.getCreateTime());

            //为null，说明当前的zhangsan只是一个候选人，并不是任务的执行人
            System.out.println("任务委派人:"+task.getAssignee());

            System.out.println("流程实例ID:"+task.getProcessInstanceId());
        }

        return "groupController_dongtai_zaichaxun";
    }

    /**
     7.当前用户完成自己的任务
     */
    @RequestMapping("/groupController_dongtai_completeTask")
    public String completeTask() {
        //4.执行查询
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("StudentLevaeProcess91")
                .taskAssignee("张三")  //设置任务的负责人
                .singleResult();
        //5.执行当前的任务
        if(task!=null){
            taskService.complete(task.getId());
            System.out.println("任务执行完毕!");
        }
        return "groupController_sizhi_completeTask";
    }

    /**
     8.任务交接，前提要保证当前用户是这个任务的负责人，这时候他才可以有权限去将任务交接给其他候选人
     */
    @RequestMapping("/groupController_dongtai_renwujiaojie")
    public String Assignee_renwujiaojie() {
        //4.执行查询
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("StudentLevaeProcess91")
                .taskAssignee("张三")  //设置任务的负责人
                .singleResult();
        //5.判断是否有这个任务
        if(task!=null){
            taskService.setAssignee(task.getId(),"lisi");//交接任务为lisi  ,交接任务就是一个候选人拾取用户的过程
            System.out.println("交接任务完成~!");
        }
        return "groupController_dongtai_renwujiaojie";
    }
}
