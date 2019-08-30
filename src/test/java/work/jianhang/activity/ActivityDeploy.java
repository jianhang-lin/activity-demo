package work.jianhang.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.io.InputStream;
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

}
