package com.zhangqiang.activity.act_re_procdef;

import java.util.List;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * java类简单作用描述
 *
 * @ProjectName: activity
 * @Package: com.zhangqiang.activity.act_re_procdef
 * @ClassName: act_re_procdef_query
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2020-05-18 20:23
 * @UpdateUser: zhangq
 * @UpdateDate: 2020-05-18 20:23
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
@RestController
@RequestMapping("/act_re_procdef_query")
public class act_re_procdef_query {
  /**
   * 查询流程定义 返回流程定义集合 ---对应act_re_procdef
   */
  @Autowired
  RepositoryService repositoryService;

  @RequestMapping("/procdef_list")
  public String getlist(){
    List<ProcessDefinition> pdList = repositoryService // 获取service类
        .createProcessDefinitionQuery() // 创建流程定义查询
        .processDefinitionKey("myFirstProcess") // 通过key查询
        .list(); // 返回一个集合
    for (ProcessDefinition pd : pdList) {
      System.out.println("ID_：" + pd.getId());
      System.out.println("NAME_：" + pd.getName());
      System.out.println("KEY_：" + pd.getKey());
      System.out.println("VERSION_：" + pd.getVersion());
      System.out.println("===================");
    }
    return "procdef_list";
  }

  @RequestMapping("/procdef_id")
  public String procdef_id(){
    List<ProcessDefinition> pdList = repositoryService // 获取service类
        .createProcessDefinitionQuery() // 创建流程定义查询
//        .processDefinitionKey("myFirstProcess") // 通过key查询
        .processDefinitionId("myFirstProcess:2:7504")
        .list(); // 返回一个集合
    for (ProcessDefinition pd : pdList) {
      System.out.println("ID_：" + pd.getId());
      System.out.println("NAME_：" + pd.getName());
      System.out.println("KEY_：" + pd.getKey());
      System.out.println("VERSION_：" + pd.getVersion());
      System.out.println("===================");
    }

    return "procdef_id";
  }


//    比如我们某个流程定义不需要，我们要删除它；这时候我们可以通过接口，通过流程定义部署ID来删除流程定义；
  /**
   * 删除流程定义
   */
  @RequestMapping("/procdef_delete")
  public void delete(){
    repositoryService
        .deleteDeployment("12501"); // 流程部署ID
    System.out.println("delete OK！");
  }
/*  本质的话，就是数据库里的数据 有主外键关联，不能删除；
  我们实际情况的，假如一个流程定义都不需要了。那那些活动的流程实例也直接了当的级联删除；
  所以我们这里要搞级联删除；上代码；*/
@RequestMapping("/procdef_deleteCascade")
public void procdef_deleteCascade(){
  repositoryService
      .deleteDeployment("12501",true); // 流程部署ID
  System.out.println("delete OK！");
}
}
