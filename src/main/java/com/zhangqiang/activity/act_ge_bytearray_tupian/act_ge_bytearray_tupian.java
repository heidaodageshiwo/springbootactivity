package com.zhangqiang.activity.act_ge_bytearray_tupian;

import java.io.File;
import java.io.InputStream;
import org.activiti.engine.RepositoryService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/procdef_getImageById")
public class act_ge_bytearray_tupian {
    @Autowired
    RepositoryService repositoryService;
/*    Activiti给我们提供了接口，可以返回一个资源文件输入流，然后我们可以得到一张图片，存到本地服务器，然后我们可以通过图片路径在网页上显示，
    来实现管理员查询流程定义图片的功能；
    我们代码里用到了apache的commons包里的FileUtils类，所以我们在下pom.xml里加下commons_io的依赖：
    <dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.4</version>
</dependency>
    */

    /**
     * 通过流程部署ID获取流程图图片
     */
    @RequestMapping("/procdef_getImageById")
    public void getImageById() throws Exception {
        InputStream inputStream = repositoryService
                .getResourceAsStream("7501", "Helloworld.png"); // 根据流程部署ID和资源名称获取输入流
        FileUtils.copyInputStreamToFile(inputStream, new File("D:/helloWorld.png"));
    }
/*
    可能你们的和我的流程部署ID 资源名称不一样，写上对应的即可，然后运行方法。我们会在D盘发现一个图片，即流程定义图图片。
    实际开发的时候，我们把图片存到项目路径下，然后名字的话，可以根据当前日期年月日时分秒来命名，然后得到路径后，在新的页面，
    或者是模态窗口里显示图片；*/


}
