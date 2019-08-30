package work.jianhang.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class ActivitiTest {
    /**
     * 1、部署流程
     * 2、启动流程实例
     * 3、请假人发出请假申请
     * 4、班主任查看任务
     * 5、班主任审批
     * 6、最终的教务处Boss审批
     */


    /**
     * 1：部署一个Activiti流程
     * 运行成功后，查看之前的数据库表，就会发现多了很多内容
     */
    @Test
    public void createActivityTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("shenqing.bpmn")
                .addClasspathResource("shenqing.png")
                .deploy();
    }

    /**
     * 2：启动流程实例
     */
    @Test
    public void testStartProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //这个是查看数据库中act_re_procdef表
        processEngine.getRuntimeService().startProcessInstanceById("shenqing:1:4");
    }

    /**
     * 3.完成请假申请
     */
    @Test
    public void testQingjia() {} {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查看act_ru_task表
        // processEngine.getTaskService().complete("2504");
    }

    /**
     * 4.小明学习的班主任小毛查询当前正在执行任务
     */
    @Test
    public void testQueryTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("小毛")
                .list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }
    }

    /**
     * 班主任小毛完成任务
     */
    @Test
    public void testFinishTaskManager() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查看act_ru_task数据表
        processEngine.getTaskService().complete("5002");
    }


}
