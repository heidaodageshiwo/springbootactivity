package com.zhangqiang.activity.StudentLeaveProcess.StudentLeaveProcess5;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener  implements TaskListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void notify(DelegateTask delegateTask) {
        // TODO Auto-generated method stub
        delegateTask.setAssignee("李四"); // 指定办理人
    }

}
