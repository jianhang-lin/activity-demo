package work.jianhang.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Activiti流程部署的方法
 */
public class ActivityDeploy {

    /**
     * 1.通过bpmn和png资源进行部署
     */
    @Test
    public void testDeployFromClasspath() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("shenqing.bpmn")
                .addClasspathResource("shenqing.png")
                .deploy();
    }

    /**
     * 2.通过 inputstream完成部署
     */
    @Test
    public void testDeployFromInputStream() {
        InputStream bpmnStream = this.getClass().getClassLoader().getResourceAsStream("shenqing.bpmn");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addInputStream("shenqing.bpmn", bpmnStream)
                .deploy();
    }

    /**
     * 通过zipinputstream完成部署
     * 注意：这个的话，需要将bpmn和png文件进行压缩成zip文件，然后放在项目src目录下即可(当然其他目录也可以)
     */
    @Test
    public void testDeployFromZipInpuStream() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("shenqing.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
    }

    /**
     * 删除已经部署的Activiti流程
     */
    @Test
    public void testDelete() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService().deleteDeployment("17501", Boolean.TRUE);
    }

    /**
     * 根据名称查询流程部署
     */
    @Test
    public void testQueryDeploymentByName() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime()
                .desc()
                .deploymentName("请假流程").list();
        for (Deployment deployment : deployments) {
            System.out.println(deployment.getId());
        }
    }

    /**
     * 查询所有的部署流程
     */
    @Test
    public void queryAllDeployment() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime() // 按照部署时间排序
                .desc() // 按照降序排序
                .list();
        for (Deployment deployment : deployments) {
            System.out.println(deployment.getId() + " 部署名称：" + deployment.getName());
        }
    }

    /**
     * 查询所有的流程定义
     */
    @Test
    public void testQueryAllPd() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        for (ProcessDefinition processDefinition : processDefinitions) {
            System.out.println(processDefinition.getName() + processDefinition.getVersion());
        }
    }

    /**
     * 查看流程图
     * 根据deploymentId和name(在act_ge_bytearray数据表中)
     */
    @Test
    public void testShowImage() throws IOException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                            /**
                             * deploymentID
                             * 文件的名称和路径
                             */
                        .getResourceAsStream("1", "shenqing.png");
        OutputStream outputStream = new FileOutputStream("/home/grant/IdeaProjects/activity/src/test/java/work/jianhang/activity/1.png");
        int b = -1;
        while ((b = inputStream.read()) != -1) {
            outputStream.write(b);
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * 根据pdid查看图片(在act_re_procdef数据表中)
     */
    @Test
    public void testShowImageByPdid() throws IOException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getProcessDiagram("shenqing:1:4");
        OutputStream outputStream = new FileOutputStream("/home/grant/IdeaProjects/activity/src/test/java/work/jianhang/activity/testShowImageByPdid.png");
        int b = -1;
        while ((b = inputStream.read()) != -1) {
            outputStream.write(b);
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * 查看bpmn文件(在act_re_procdef数据表中)
     */
    @Test
    public void testShowBpmn() throws IOException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getProcessModel("shenqing:1:4");
        OutputStream outputStream = new FileOutputStream("/home/grant/IdeaProjects/activity/src/test/java/work/jianhang/activity/testShowBpmn.bpmn");
        int b = -1;
        while ((b = inputStream.read()) != -1) {
            outputStream.write(b);
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * 启动流程实例，通过PID
     */
    @Test
    public void testStartProcessInstanceByPid() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById("shenqing:1:4");
        System.out.println(processInstance.getId());
    }

    /**
     * 根据pdkey启动流程实例，默认启动最高版本的
     */
    @Test
    public void testStartProcessInstanceByPdKey() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("shenqing");
        System.out.println(processInstance.getId());
    }
}