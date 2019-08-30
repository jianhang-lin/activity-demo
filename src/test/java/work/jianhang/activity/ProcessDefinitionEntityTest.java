package work.jianhang.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.ArrayList;
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

    /**
     * 根据pdid得到processDefinitionEntity中的activityimpl
     */
    @Test
    public void testGetActivityImpl() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据pdid得到ProcessDefinitionEntry
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition("shenqing:3:15004");
        System.out.println(processDefinitionEntity.getId() + ":"+ processDefinitionEntity.getName());
        /**
         * ActivityImpl是一个对象
         * 一个activityImpl代表processDefinitionEntity中的一个节点
         */
        List<ActivityImpl> activities = processDefinitionEntity.getActivities();
        for (ActivityImpl activity : activities) {
            System.out.println(activity.getId());
            System.out.println("hegiht:" + activity.getHeight());
            System.out.println("width:" + activity.getWidth());
            System.out.println("x:" + activity.getX());
            System.out.println("y:"+ activity.getY());
        }
    }

    /**
     * 得到ProcessDefinitionEntity中的所有的ActivityImpl的所有的PvmTransition
     */
    @Test
    public void testSequenceFlow() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据pdid得到ProcessDefinitionEntry
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition("shenqing:3:15004");
        System.out.println(processDefinitionEntity.getId() + ":"+ processDefinitionEntity.getName());
        /**
         * ActivityImpl是一个对象
         * 一个activityImpl代表processDefinitionEntity中的一个节点
         */
        List<ActivityImpl> activities = processDefinitionEntity.getActivities();
        for (ActivityImpl activity : activities) {
            /**
             * 得到一个activityimpl的所有的outgoing
             */
            List<PvmTransition> pvmTransitions = activity.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                System.out.println("sequenceFlowId:"+pvmTransition.getId());
            }
        }
    }

    /**
     * 得到当前正在执行的流程实例的activityimpl-->PvmTransition
     */
    @Test
    public void testQueryActivityImpl() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition("shenqing:3:15004");
        // 根据piid获取得到activityId
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId("22501")
                .singleResult();
        // 根据流程实例得到当前正在执行的流程实例的正在执行的节点
        ActivityImpl activity = processDefinitionEntity.findActivity(processInstance.getActivityId());
        System.out.println("流程实例id:" + processInstance.getId());
        System.out.println("当前正在执行的节点："+ activity.getId());
        System.out.println("hegiht:"+activity.getHeight());
        System.out.println("width:"+ activity.getWidth());
        System.out.println("x:"+activity.getX());
        System.out.println("y:"+activity.getY());
    }

    /**
     * 当前用户-->当前用户正在执行的任务--->当前正在执行的任务的piid-->该任务所在的流程实例
     */
    @Test
    public void testGetProcessInstanceByUser() {
        List<ProcessInstance> result = getProcessInstanceByUser("小毛");
        for (ProcessInstance processInstance : result) {
            System.out.println(processInstance.getId());
        }
    }

    List<ProcessInstance> getProcessInstanceByUser(String assignee) {
        List<ProcessInstance> processInstances = new ArrayList<>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService().createTaskQuery()
                .taskAssignee(assignee).list();
        for (Task task : tasks) {
            String piid = task.getProcessInstanceId();
            ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                    .processInstanceId(piid)
                    .singleResult();
            processInstances.add(processInstance);
        }
        return processInstances;
    }

    /**
     * 根据当前的登录人能够推导出所在的流程定义
     */
    @Test
    public void testGetProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService().createTaskQuery()
                .taskAssignee("小毛").list();
        for (Task task : tasks) {
            String pdid = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = processEngine.getRepositoryService()
                    .createProcessDefinitionQuery()
                    .processDefinitionId(pdid)
                    .singleResult();
            System.out.println(processDefinition.getName());
        }
    }
}
