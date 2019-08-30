package work.jianhang.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTable {
    /**
     * 创建Activiti流的相关的数据库表
     */
    @Test
    public void createTable() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
                .buildProcessEngine();
    }


}
