package com.zhangqiang.activity.act_re_procdef;

import java.util.List;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class act_re_procdef_delete_suoyou {
    @Autowired
    RepositoryService repositoryService;
    /*有时候我们一个流程定义不需要的，包括所有版本，这时候我们在用户界面上一个一个删除太麻烦；
    所有有时候我们又这样的需求，一下子把所有Key相同的流程定义全部删除；
    我们的思路是这样的；
    第一步：根据Key获取所有的流程定义；
    第二步：遍历集合，获取每个流程定义的流程部署Id
    第三步：我们根据流程部署Id即可删除所有的流程定义；*/

    /**
     * 删除流程定义
     */
    @RequestMapping("/procdef_delete_suoyoubanben")
    /**
     * 删除所有Key相同的流程定义
     * @throws Exception
     */
     public void deleteByKey()throws Exception{
        List<ProcessDefinition> pdList=repositoryService  // 获取service类
                .createProcessDefinitionQuery() // 创建流程定义查询
                .processDefinitionKey("mySecondProcess") // 根据Key查询
                .list();
        for(ProcessDefinition pd:pdList){  // 遍历集合 获取流程定义的每个部署Id，根据这个id来删除所有流程定义
            repositoryService
                    .deleteDeployment(pd.getDeploymentId(), true);
        }
    }

}
