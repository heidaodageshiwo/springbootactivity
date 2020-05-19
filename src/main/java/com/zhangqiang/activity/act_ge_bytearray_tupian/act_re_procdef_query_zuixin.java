package com.zhangqiang.activity.act_ge_bytearray_tupian;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/procdef_list_new")
public class act_re_procdef_query_zuixin {
/*
    因为每个流程定义都可能会有好几个版本，所以有时候我们有这样的需求，查询出最新版本的流程定义的集合；
    怎么来实现呢？ 我们一般的思路是这样的。
    第一步：我们通过Activiti接口来获取根据流程定义Version升序排序的流程定义的集合；
    第二步：定义一个有序的Map， Map的key就是我们流程定义的Key，Map的值就是流程定义对象；
    第三步：我们遍历第一步的集合，put(key,value)  假如Key相同，后者会覆盖前者；
    第四步：我们获取Map的values。即我们需要的最新版本的流程定义的集合；
*/


    @Autowired
    RepositoryService repositoryService;
//    这里我们通过流程定义的KEY查询返回一个集合。然后我们输入流程定义表的部分关键字段；

  // * 部署流程定义
  @RequestMapping("/deployWithClassPath")
  public String deployWithClassPath() {
    Deployment deployment= repositoryService.createDeployment()
        .addClasspathResource("mysencondprocess/MySencondProcess.bpmn")
        .addClasspathResource("mysencondprocess/MySencondProcess.png")
        .name("MySencondProcess流程")
        .deploy();
    System.out.println("流程部署ID:"+deployment.getId());
    System.out.println("流程部署Name:"+deployment.getName());
    return "deployWithClassPath";
  }


    @RequestMapping("/procdef_list_new")
    /**
     * 查询最新版本的流程定义
     */
     public String listLastVersion()throws Exception{

        // 获取流程定义集合，根据Key升序排序
        List<ProcessDefinition> listAll=repositoryService // 获取service类
                .createProcessDefinitionQuery() // 创建流程定义查询
                .orderByProcessDefinitionVersion().asc() // 根据流程定义版本升序
                .list();
        // 定义有序Map 相同的key 假如添加map的值 后面的值会覆盖前面相同key的值
        Map<String,ProcessDefinition> map=new LinkedHashMap<String,ProcessDefinition>();
        // 遍历集合 根据key来覆盖前面的值 来保证最新的Key覆盖前面的所有老的Key的值
        for(ProcessDefinition pd:listAll){
            map.put(pd.getKey(), pd);
        }
        List<ProcessDefinition> pdList=new LinkedList<ProcessDefinition>(map.values());
        for(ProcessDefinition pd:pdList){
            System.out.println("ID_："+pd.getId());
            System.out.println("NAME_："+pd.getName());
            System.out.println("KEY_："+pd.getKey());
            System.out.println("VERSION_："+pd.getVersion());
            System.out.println("===================");
        }
        return "";
    }
}
