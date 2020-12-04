package com.zhangqiang.activity.test1;

import java.io.File;
import java.io.FileInputStream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * java类简单作用描述
 *
 * @ProjectName: activity
 * @Package: com.zhangqiang.activity.test1
 * @ClassName: zq
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2020-05-29 17:20
 * @UpdateUser: zhangq
 * @UpdateDate: 2020-05-29 17:20
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
@RestController
//@RequestMapping(value="/api/v1")
public class zq {


  @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
  @ResponseBody
  public byte[] test() throws Exception {

    File file = new File("C:\\Users\\Think\\Desktop\\图片\\202510wrq4iaiqaryii5qg.jpg");
    FileInputStream inputStream = new FileInputStream(file);
    byte[] bytes = new byte[inputStream.available()];
    inputStream.read(bytes, 0, inputStream.available());
    return bytes;

  }

}
