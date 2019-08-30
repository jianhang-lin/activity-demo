package work.jianhang.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

//Task任务
public class TaskTest {

//    情况一：当没有进入该节点之前，就可以确定任务的执行人
//  实例：比如进行“请假申请”的流程时候，最开始执行的就是提交”请假申请“，那么就需要知道，谁提交的“请假”，很明显，在一个系统中，谁登陆到系统里面，谁就有提交“请假任务”的提交人，那么执行人就可以确定就是登录人。那么执行人就可以确定就是登录人
    @Test
    public void startDeployTestTask1() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程：情况一")
                .addClasspathResource("task1.bpmn")
                .addClasspathResource("task1.png")
                .deploy();
    }


//    情况二：有可能一个任务节点的执行人是固定的。
//    实例：比如，在“公司财务报账”的流程中，最后一个审批的人，一定是财务部的最大的BOSS，所以，这样该流程的最后一个节点执行人，是不是就已经确定是为“财务部最大BOSS”了。

//    情况三：一个节点任务，之前是不存在执行人（未知），只有当符合身份的人，登陆系统，进入该系统，才能确定执行人。
//    实例：比如，如果当前的流程实例正在执行“自荐信审批”，这个时候，自荐信审批没有任务执行人，因为审批人是可以很多个，无法确定到底是谁，只有当咨询员登录系统以后才能给该任务赋值执行人，即存在只要是咨询员登陆，那么就可以看到所有的“自荐信”。

//    情况四：一个任务节点有n多人能够执行该任务，但是只要有一个人执行完毕就完成该任务了：组任务
//    实例：比如，“进入地铁站通道”的流程，我们一般地铁都是有N个安全检查的入口，有很多个人在进行检查，那么我们要想通过检查，那么任意一个检察员只要通过即可。
}
