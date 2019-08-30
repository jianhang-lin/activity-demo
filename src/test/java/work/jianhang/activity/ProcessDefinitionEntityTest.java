package work.jianhang.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.junit.Test;

import java.util.List;

/**
 * ProcessDefinitionEntity（流程定义实体）
 */
public class ProcessDefinitionEntityTest {

    /**
     * 根据pdid得到processDefinitionEntity
     */
    @Test
    public void testProcessDefinitionEntity() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据pdid得到ProcessDefinitionEntry
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition("shenqing:3:15004");
        System.out.println(processDefinitionEntity.getId() + ":"+ processDefinitionEntity.getName());
    }
}
