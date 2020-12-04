package com.zhangqiang.activity.StudentLeaveProcess9;

import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * java类简单作用描述
 *
 * @ProjectName: zhangqiang_act
 * @Package: com.zhangqiang.zhangqiang_act.StudentLeaveProcess9
 * @ClassName: Test_view_picture
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2019-09-26 23:51
 * @UpdateUser: zhangq
 * @UpdateDate: 2019-09-26 23:51
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * *
 */
@Controller
public class Test_view_picture {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    HistoryService historyService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;
    @Autowired
    ManagementService managementService;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;


    @RequestMapping("/testindex")
    public String index(){
        return "index";
    }

    @RequestMapping("/showCurrentView")
    public ModelAndView showCurrentView(  HttpServletResponse response)throws Exception{
       String  taskId="160014";
        ModelAndView mav=new ModelAndView();
        Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        String processDefinitionId=task.getProcessDefinitionId(); // 获取流程定义id
        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery() // 创建流程定义查询
                .processDefinitionId(processDefinitionId) // 根据流程定义id查询
                .singleResult();
        mav.addObject("deploymentId",processDefinition.getDeploymentId()); // 部署id
        mav.addObject("diagramResourceName", processDefinition.getDiagramResourceName()); // 图片资源文件名称

        ProcessDefinitionEntity	processDefinitionEntity=(ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        String processInstanceId=task.getProcessInstanceId(); // 获取流程实例id
        ProcessInstance pi=runtimeService.createProcessInstanceQuery() // 根据流程实例id获取流程实例
                .processInstanceId(processInstanceId)
                .singleResult();
//        ActivityImpl activityImpl=processDefinitionEntity.findActivity(pi.getActivityId()); // 根据活动id获取活动实例
//        ProcessDefinitionUtil

        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());

        //获取当前活动对象
        FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());

        GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowElement.getId());


        mav.addObject("x", graphicInfo.getX()); // x坐标
        mav.addObject("y", graphicInfo.getY()); // y坐标
        mav.addObject("width", graphicInfo.getWidth()); // 宽度
        mav.addObject("height", graphicInfo.getHeight()); // 高度
        mav.setViewName("currentView");
        return mav;
    }

    @RequestMapping("/showViewByTaskId")
    public String showViewByTaskId(String taskId,HttpServletResponse response)throws Exception{
        HistoricTaskInstance hti=historyService.createHistoricTaskInstanceQuery().taskId("160014").singleResult();
        String processDefinitionId=hti.getProcessDefinitionId(); // 获取流程定义id
        ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        InputStream inputStream=repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
        OutputStream out=response.getOutputStream();
        for(int b=-1;(b=inputStream.read())!=-1;){
            out.write(b);
        }
        out.close();
        inputStream.close();
        return null;
    }
/*
    *//**
     * 流程是否已经结束
     *
     * @param processInstanceId 流程实例ID
     * @return
     *//*
    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished()
                .processInstanceId(processInstanceId).count() > 0;
    }
    *//**
     * 流程跟踪图片
     *
     * @param processDefinitionId
     *            流程定义ID
     * @param executionId
     *            流程运行ID
     * @param out
     *            输出流
     * @throws Exception
     *//*
    public void processTracking(String processDefinitionId, String executionId,
                                OutputStream out) throws Exception {
        // 当前活动节点、活动线
        List<String> activeActivityIds = new ArrayList<String>(), highLightedFlows = new ArrayList<String>();

        *//**
         * 获得当前活动的节点
         *//*
        if (this.isFinished(executionId)) {// 如果流程已经结束，则得到结束节点
            activeActivityIds.add(historyService
                    .createHistoricActivityInstanceQuery()
                    .executionId(executionId).activityType("endEvent")
                    .singleResult().getActivityId());
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            activeActivityIds = runtimeService
                    .getActiveActivityIds(executionId);
        }
        *//**
         * 获得当前活动的节点-结束
         *//*

        *//**
         * 获得活动的线
         *//*
        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery().executionId(executionId)
                .orderByHistoricActivityInstanceStartTime().asc().list();
        // 计算活动线
  *//*      highLightedFlows = this
                .getHighLightedFlows(
                        (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                                .getDeployedProcessDefinition(processDefinitionId),
                        historicActivityInstances);*//*
        *//**
         * 获得活动的线-结束
         *//*

        *//**
         * 绘制图形
         *//*
        if (null != activeActivityIds) {
            InputStream imageStream = null;
            try {
                // 获得流程引擎配置
//                ProcessEngineConfiguration processEngineConfiguration = processEngine
//                        .getProcessEngineConfiguration();
                // 根据流程定义ID获得BpmnModel
                BpmnModel bpmnModel = repositoryService
                        .getBpmnModel(processDefinitionId);
                // 输出资源内容到相应对象
                imageStream = new DefaultProcessDiagramGenerator()
                        .generateDiagram(bpmnModel, "png", activeActivityIds,
                                highLightedFlows, processEngineConfiguration
                                        .getActivityFontName(),
                                processEngineConfiguration.getLabelFontName(),
                                processEngineConfiguration.getClassLoader(),
                                1.0);
                IOUtils.copy(imageStream, out);
            } finally {
                IOUtils.closeQuietly(imageStream);
            }
        }
    }

    *//**
     * 获得高亮线
     *
     * @param processDefinitionEntity
     *            流程定义实体
     * @param historicActivityInstances
     *            历史活动实体
     * @return 线ID集合
     *//*
    *//*public List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得 到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            if ((i + 1) >= historicActivityInstances.size()) {
                break;
            }
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());// 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }*/
}
