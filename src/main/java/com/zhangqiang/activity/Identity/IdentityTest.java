package com.zhangqiang.activity.Identity;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.persistence.entity.ByteArrayRef;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IdentityTest {
  @Autowired
  IdentityService identityService;

  /**
   * 添加用户测试
   */
  @RequestMapping("/testSaveUser")
  public String testSaveUser() {
    User user2 = identityService.newUser("user2");
    user2.setId("王五");
    user2.setEmail("123@qq.com");
    user2.setPassword("123456");
    user2.setFirstName("王");
    user2.setLastName("五");
    identityService.saveUser(user2); // 添加用户
    return "testSaveUser";

  }

  /**
   * 测试删除用户
   */
  @RequestMapping("/testDeleteUser")
  public String testDeleteUser() {
    identityService.deleteUser("lisi");
    return "testDeleteUser";
  }

  /**
   * 测试查询用户
   */
  @RequestMapping("/testSelectUser")
  public String testSelectUser() {
    User usecc = identityService.createUserQuery().userId("王五").singleResult();
    System.out.println("id========" + usecc.getId());
    System.out.println("firstname========" + usecc.getFirstName());
    System.out.println("lastname========" + usecc.getLastName());
    System.out.println("password========" + usecc.getPassword());
    System.out.println("email========" + usecc.getEmail());

    System.out.println("==================单个用户，多个用户分割线=====================================");
    List<User> users=identityService.createUserQuery().list();
    for(int i=0;i<users.size();i++){
      System.out.println("id========" + users.get(i).getId());
      System.out.println("firstname========" + users.get(i).getFirstName());
      System.out.println("lastname========" + users.get(i).getLastName());
      System.out.println("password========" + users.get(i).getPassword());
      System.out.println("email========" + users.get(i).getEmail());
      System.out.println("=====================================================================");
    }
    return "testSelectUser";
  }

  /**
   * 测试添加组
   */
  @RequestMapping("/testSaveGroup")
  public String testSaveGroup() {
    Group group = identityService.newGroup("group1");
    group.setId("dev");
    identityService.saveGroup(group);
    return "testSaveGroup";

  }

  /**
   * 测试删除组
   */
  @RequestMapping("/testDeleteGroup")
  public String testDeleteGroup() {
    identityService.deleteGroup("test");
    return "testDeleteGroup";

  }


  /**
   * 测试添加用户和组关联关系
   */
  @RequestMapping("/testSaveMembership")
  public String testSaveMembership() {
    identityService.createMembership("张三", "dev");
    identityService.createMembership("王五", "dev");
    return "testSaveMembership";

  }

  /**
   * 测试删除用户和组关联关系
   */
  @RequestMapping("/testDeleteMembership")
  public String testDeleteMembership() {
    identityService.deleteMembership("lisi", "test");
    return "testDeleteMembership";

  }
}
