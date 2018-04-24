package biz;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import form.WorkflowBean;



public interface IWorkflowService {

	void saveNewDeploye(File file, String filename);

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionList();

	InputStream findImageInputStream(String deploymentId, String imageName);

	void deleteProcessDefinitionByDeploymentId(String deploymentId);

	void saveStartProcess(String saveorupdate,String ename,String next,String key,String id);

	List<Task> findTaskListByName(String name);

	String findTaskFormKeyByTaskId(String taskId);

	List<String> findOutComeListByTaskId(String taskId);

	void saveSubmitTask(WorkflowBean workflowBean);

	List<Comment> findCommentByTaskId(String taskId);

	List<Comment> findCommentByClaimVoucherId(String id);

	ProcessDefinition findProcessDefinitionByTaskId(String taskId);

	Map<String, Object> findCoordingByTask(String taskId);

	List<Integer> findLeaveBillByTaskId(List<Task> taskId);

	List<ProcessInstance> findProcessInstancesByTaskId(List<String> taskId);
	
	ProcessInstance findProcessInstanceByTaskId(String taskId) ;
	
	
}
