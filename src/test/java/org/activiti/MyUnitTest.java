package org.activiti;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class MyUnitTest {
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Test
	@Deployment(resources = {"org/activiti/test/Custom_outcome.bpmn20.xml"})
	public void test() {
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("custom-outcome-process");
		assertNotNull(processInstance);
		
		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("UserTask1", task.getName());
		
		Map<String, String> properties = new HashMap<String, String>();
		// in Activiti 6 UI 'outcome' property is set in user reference form... 
		// so this test is not the case and passed without any errors 
		properties.put("outcome", "Agree");
		activitiRule.getFormService().submitTaskFormData(task.getId(), properties );
		
		task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("Agree UserTask", task.getName());		
	}

}
