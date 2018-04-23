package bizimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import dao.BizClaimVoucherDao;
import entity.BizClaimVoucher;
import form.WorkflowBean;

@Service("workflowService")
public class WorkflowServiceImpl implements biz.IWorkflowService {
	/**请假申请Dao*/

	@Resource(name="repositoryService")
	private RepositoryService repositoryService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	@Resource(name="taskService")
	private TaskService taskService;
	@Resource(name="formService")
	private FormService formService;
	@Resource(name="historyService")
	private HistoryService historyService;
	@Resource(name="bizClaimVoucherDao")
	BizClaimVoucherDao bizClaimVoucherDao;

	/**部署流程定义*/
	public void saveNewDeploye(File file, String filename) {
		try {
			//2：将File类型的文件转化成ZipInputStream流
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment()//创建部署对象
			.name(filename)//添加部署名称
			.addZipInputStream(zipInputStream)//
			.deploy();//完成部署
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**查询部署对象信息，对应表（act_re_deployment）*/
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
				.orderByDeploymenTime().asc()//
				.list();
		return list;
	}

	/**查询流程定义的信息，对应表（act_re_procdef）*/

	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
				.orderByProcessDefinitionVersion().asc()//
				.list();
		return list;
	}

	/**使用部署对象ID和资源图片名称，获取图片的输入流*/

	public InputStream findImageInputStream(String deploymentId,
			String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}

	/**使用部署对象ID，删除流程定义*/

	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/**更新请假状态，启动流程实例，让启动的流程实例关联业务*/

	public void saveStartProcess(String saveorupdate,String ename,String nextDeal,String key,String id) {
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("inputUser", ename);//表示惟一用户
		String objId = key+"."+id;
		variables.put("objId", objId);
		runtimeService.startProcessInstanceByKey(key,objId,variables);
		Map<String, Object> variables2 = new HashMap<String,Object>();
		List<Task> task =findTaskListByName(ename);

		BizClaimVoucher bizClaimVoucher= bizClaimVoucherDao.findByID(id);


		if(saveorupdate.equals("save"))
		{
			//添加
			if(SecurityUtils.getSubject().hasRole("manager"))
			{
				variables2.put("role", "manager");
			}
			else if(SecurityUtils.getSubject().hasRole("generalmanager"))
			{
				variables2.put("role", "generalmanager");
			}
			else if(SecurityUtils.getSubject().hasRole("cashier"))
			{
				variables2.put("role", "cashier");
			}
			else if(SecurityUtils.getSubject().hasRole("staff"))
			{
				variables2.put("role", "staff");
			}

			for (Task task2 : task) {
				taskService.complete(task2.getId(), variables2);

				List<Task> task3 =findTaskListByName(nextDeal);
				for (Task task4 : task3) {
					bizClaimVoucher.setTaskid(task4.getId());
				}
			}
		}
		else
		{


			//修改
			if(SecurityUtils.getSubject().hasRole("staff"))
			{
				variables2.put("role", "staff");
				task =findTaskListByName(ename);

				for (Task task2 : task) {
					taskService.complete(task2.getId(), variables2);
					List<Task> task3 =findTaskListByName(nextDeal);
					for (Task task4 : task3) {
						bizClaimVoucher.setTaskid(task4.getId());

					}

				}
			}
			else if(SecurityUtils.getSubject().hasRole("manager"))
			{
				variables2.put("rollback", "no");
				task =findTaskListByName(ename);

				for (Task task2 : task) {
					taskService.complete(task2.getId(), variables2);
					List<Task> task3 =findTaskListByName(nextDeal);
					for (Task task4 : task3) {
						bizClaimVoucher.setTaskid(task4.getId());

					}
				}
			}
			else if(SecurityUtils.getSubject().hasRole("cashier"))
			{

				variables2.put("createmp", "default");
				task =findTaskListByName(ename);

				for (Task task2 : task) {
					taskService.complete(task2.getId(), variables2);
					List<Task> task3 =findTaskListByName(nextDeal);
					for (Task task4 : task3) {
						bizClaimVoucher.setTaskid(task4.getId());

					}
				}

			}
			else if(SecurityUtils.getSubject().hasRole("generalmanager"))
			{

				variables2.put("rollback", "no");
				task =findTaskListByName(ename);

				for (Task task2 : task) {
					taskService.complete(task2.getId(), variables2);
					List<Task> task3 =findTaskListByName(nextDeal);
					for (Task task4 : task3) {
						bizClaimVoucher.setTaskid(task4.getId());

					}
				}

			}
		}


	}

	/**2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>*/
	public List<Task> findTaskListByName(String name) {
		List<Task> list = taskService.createTaskQuery()//s
				.taskAssignee(name)//指定个人任务查询
				.orderByTaskCreateTime().asc()//
				.list();
		return list;
	}

	/**使用任务ID，获取当前任务节点中对应的Form key中的连接的值*/
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		//获取Form key的值
		String url = formData.getFormKey();
		return url;
	}
	/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
	public List<Integer> findLeaveBillByTaskId(List<Task> taskId) {
		List<Integer> bizClaimVouchers =new ArrayList<Integer>();
		for (Task task : taskId) {
			//1：使用任务ID，查询任务对象Task
			task = taskService.createTaskQuery()//
					.taskId(task.getId())//使用任务ID查询
					.singleResult();
			//2：使用任务对象Task获取流程实例ID
			String processInstanceId = task.getProcessInstanceId();
			//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
			//4：使用流程实例对象获取BUSINESS_KEY
			String buniness_key = pi.getBusinessKey();
			//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
			String id = "";
			if(StringUtils.isNotBlank(buniness_key)){
				//截取字符串，取buniness_key小数点的第2个值
				id = buniness_key.split("\\.")[1];
			}

			bizClaimVouchers.add(Integer.parseInt( id));
		}

		return bizClaimVouchers;
	}

	public List<ProcessInstance> findProcessInstancesByTaskId(List<String> taskId) {
		List<ProcessInstance> processInstances =new ArrayList<ProcessInstance>();

		for (String taskid : taskId) {
			//1：使用任务ID，查询任务对象Task
			Task task = taskService.createTaskQuery()//
					.taskId(taskid)//使用任务ID查询
					.singleResult();
			//2：使用任务对象Task获取流程实例ID
			String processInstanceId = task.getProcessInstanceId();
			//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();

			processInstances.add(pi);
		}

		return processInstances;
	}
	public ProcessInstance findProcessInstanceByTaskId(String taskId) {

		//1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//使用流程实例ID查询
				.singleResult();


		return pi;
	}

	/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		/*	//1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				}
				else{
					list.add("默认提交");
				}
			}
		}*/
		return list;
	}

	/**指定连线的名称完成任务*/
	public void saveSubmitTask(WorkflowBean workflowBean) {
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		//获取连线的名称
		String outcome = workflowBean.getOutcome();
		//批注信息
		String message = workflowBean.getComment();
		//获取报销单ID
		Long id = workflowBean.getId();
		//获取待处理人
		String assignee = workflowBean.getAssignee();
		//获取审核人权限
		String auditorRolename= workflowBean.getAuditorRolename();
		//获取 是否通过
		String ispass = workflowBean.getIspass();

		BizClaimVoucher bizClaimVoucher = bizClaimVoucherDao.findByID(id.toString());

		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		//使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用：
		 * 		String userId = Authentication.getAuthenticatedUserId();
			    CommentEntity comment = new CommentEntity();
			    comment.setUserId(userId);
			  所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
			 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 * */
		//		Authentication.setAuthenticatedUserId(SessionContext.get().getName());
		taskService.addComment(taskId, processInstanceId, message);
		/**
		 * 2：如果连线的名称是“默认提交”，那么就不需要设置，如果不是，就需要设置流程变量
		 * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务
				 流程变量的名称：outcome
				 流程变量的值：连线的名称
		 */
		Map<String, Object> variables = new HashMap<String,Object>();


		/*	if(outcome!=null && !outcome.equals("staff")){

			variables.put("role", outcome);


		}*/

		if(ispass.equals("no"))
		{
			if(auditorRolename.equals("manager"))
			{
				variables.put("rollback", "manager");
				bizClaimVoucher.setStatus("部门经理回拒");

			}
			else if(auditorRolename.equals("generalmanager"))
			{
				variables.put("rollback", "generalmanager");
				bizClaimVoucher.setStatus("总经理回拒");

			}
			else if(auditorRolename.equals("cashier"))
			{
				variables.put("rollback", "cashier");
				bizClaimVoucher.setStatus("财务回拒");
			}
		}
		else
		{

			if(SecurityUtils.getSubject().hasRole("manager"))
			{
				variables.put("role", "manager");
				bizClaimVoucher.setSchedule(2);

			}
			else if(SecurityUtils.getSubject().hasRole("generalmanager"))
			{

				variables.put("role", "generalmanager");
				bizClaimVoucher.setSchedule(4);

			}
			else if(SecurityUtils.getSubject().hasRole("cashier"))
			{

				variables.put("role", "cashier");
				bizClaimVoucher.setSchedule(3);


			}
			else if(SecurityUtils.getSubject().hasRole("staff"))
			{

				variables.put("role", "staff");
				bizClaimVoucher.setSchedule(1);

			}
			variables.put("rollback", "no");
			bizClaimVoucher.setStatus("待通过");
		}


		List<String> roles=	workflowBean.getCreateEmpRolNames();
		for (String string : roles) {
			if(string.equals("generalmanager")) {
				variables.put("createmp", "generalmanager");
			}
			else
			{
				variables.put("createmp", "default");
			}
		}

		bizClaimVoucher.setTaskid(task.getId());
		bizClaimVoucherDao.SaveOrUpdateClaimVouchers2(bizClaimVoucher);
		
		
		//3：使用任务ID，完成当前人的个人任务，同时流程变量
		//		taskService.setAssignee(taskId, assignee);
		taskService.complete(taskId, variables);
		//4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		/**
		 * 5：在完成任务之后，判断流程是否结束
   			如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//使用流程实例ID查询
				.singleResult();
		//流程结束了
		if(pi==null){
			//更新请假单表的状态从1变成2（审核中-->审核完成）
			bizClaimVoucher.setStatus("已通过审核");
			bizClaimVoucher.setSchedule(4);
		}

	}
	/**获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注*/

	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务ID，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//		//使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
		//		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
		//						.processInstanceId(processInstanceId)//使用流程实例ID查询
		//						.list();
		//		//遍历集合，获取每个任务ID
		//		if(htiList!=null && htiList.size()>0){
		//			for(HistoricTaskInstance hti:htiList){
		//				//任务ID
		//				String htaskId = hti.getId();
		//				//获取批注信息
		//				List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
		//				list.addAll(taskList);
		//			}
		//		}
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}
	/**使用请假单ID，查询历史批注信息*/
	/*public List<Comment> findCommentByLeaveBillId(Long id) {
		//使用请假单ID，查询请假单对象
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
		//获取对象的名称
		String objectName = leaveBill.getClass().getSimpleName();
		//组织流程表中的字段中的值
		String objId = objectName+"."+id;

	 *//**1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID*//*
//		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
//						.processInstanceBusinessKey(objId)//使用BusinessKey字段查询
//						.singleResult();
//		//流程实例ID
//		String processInstanceId = hpi.getId();
	  *//**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*//*
		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
						.variableValueEquals("objId", objId)//使用流程变量的名称和流程变量的值查询
						.singleResult();
		//流程实例ID
		String processInstanceId = hvi.getProcessInstanceId();
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}*/

	/**1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象*/

	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef 
				.processDefinitionId(processDefinitionId)//使用流程定义ID查询
				.singleResult();
		return pd;
	}

	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
		 map集合的key：表示坐标x,y,width,height
		 map集合的value：表示坐标对应的值
	 */
	public Map<String, Object> findCoordingByTask(String taskId) {
		//存放坐标
		Map<String, Object> map = new HashMap<String,Object>();
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
				.processInstanceId(processInstanceId)//使用流程实例ID查询
				.singleResult();
		//获取当前活动的ID
		String activityId = pi.getActivityId();
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
		//获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}

	public List<Comment> findCommentByLeaveBillId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
