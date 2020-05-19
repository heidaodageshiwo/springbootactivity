package com.zhangqiang.activity.act_re_procdef;

import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class act_hi_actinst_query {


    /* Activiti历史活动查询
在流程系统开发中，我们有这样一种需求，
当流程实例完成后，我们要查下流程活动具体的执行情况，比如这个流程实例什么时候开始的，什么时候结束的，
以及中间具体的执行步骤，这时候，我们需要查询历史流程活动执行表，act_hi_actinst*/
 /*

 流程执行过程--根据任务id去查询****************（根据任务id去查询历史的完成结果列表）
            流程执行过程列表
    任务节点ID   任务节点名称  开始时间                 结束时间
    startevent1 Start     2019-09-25 05:12:04    2019-09-25 05:12:04
    usertask5   学生处审批   2019-09-25 06:17:31

    HistoricTaskInstance hti=historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		String processInstanceId=hti.getProcessInstanceId(); // 获取流程实例id
		List<HistoricActivityInstance> haiList=historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();


finishedList********************完成的任务的列表（历史任务）
List<HistoricTaskInstance> htiList=historyService.createHistoricTaskInstanceQuery() // 创建历史任务实例查询
				.taskCandidateGroup(groupId) // 根据角色查询
				.taskNameLike("%"+s_name+"%") // 根据任务名称查询
				.list();
for(HistoricTaskInstance hti:htiList){
if((taskService.createTaskQuery().processInstanceId(hti.getProcessInstanceId()).singleResult()==null)){
				MyTask myTask=new MyTask();
				myTask.setId(hti.getId());
				myTask.setName(hti.getName());
				myTask.setCreateTime(hti.getCreateTime());
				myTask.setEndTime(hti.getEndTime());
				taskList.add(myTask);
			}
		}
	***********************************************************
    通过历史任务表act_hi_taskinst中的taskid可以查询出act_hi_comment表中的批注人员与批注结果
    act_hi_taskinst
    25008	studentLeaveProcess:2:22504	usertask2	25001	25001	班长审批					2019-09-25 17:14:40		2019-09-25 18:17:31	3771261	completed	50		audit_bz.jsp

    act_hi_comment
    25010	comment	2019-09-25 18:17:22	王二小[学生]	25008	25001	AddComment	王二小同意了	王二小同意了
    ********************************************************
    往act_hi_comment表中插入数据
    Task task=taskService.createTaskQuery() // 创建任务查询
				.taskId(taskId) // 根据任务id查询
				.singleResult();
  String processInstanceId=task.getProcessInstanceId(); // 获取流程实例id
		MemberShip currentMemberShip=(MemberShip) session.getAttribute("currentMemberShip");
		User currentUser=currentMemberShip.getUser();
		Group currentGroup=currentMemberShip.getGroup();
		Authentication.setAuthenticatedUserId(currentUser.getFirstName()+currentUser.getLastName()+"["+currentGroup.getName()+"]"); // 设置用户id
		taskService.addComment(taskId, processInstanceId, comment); // 添加批注信息
		taskService.complete(taskId); // 完成任务

    */

    @Autowired
    HistoryService historyService;
    @RequestMapping("/historyActInstanceList")
    /**
     * 历史活动查询
     */
    public String historyActInstanceList(){
        List<HistoricActivityInstance> list=historyService // 历史任务Service
                .createHistoricActivityInstanceQuery() // 创建历史活动实例查询
                .processInstanceId("35006") // 指定流程实例id
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
        return "historyActInstanceList";
    }
}
