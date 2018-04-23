package controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.activiti.image.ProcessDiagramGenerator;
import biz.BizClaimVoucherBiz;
import biz.IWorkflowService;
import dao.BizClaimVoucherDao;
import entity.SysEmployee;
import form.WorkflowBean;
import util.JsonData;


@Controller
@RequestMapping("workflowAction.do")
public class WorkflowController  {
	@Autowired
	BizClaimVoucherBiz bizClaimVoucherBiz;

	@Resource(name="workflowBean")
	private WorkflowBean workflowBean ;

	@Resource(name="workflowService")
	private IWorkflowService workflowService;
	
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
	
	Logger logger = Logger.getLogger(WorkflowController.class);
	

	@SuppressWarnings("unused")
	@RequestMapping(params="method=deployHome")
	public String deployHome(ModelMap modelMap){
		List<Deployment> depList = workflowService.findDeploymentList();
		List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
		//		ValueContext.putValueContext("depList", depList);
		//		ValueContext.putValueContext("pdList", pdList);
		modelMap.put("depList", depList);
		modelMap.put("pdList", pdList);
		return "workflow/workflow";
	}
	@RequestMapping(params="method=newdeploy")
	public String newdeploy(@RequestParam("files") MultipartFile[] files,HttpServletRequest request){
		if(null!=files)
		{
			for (MultipartFile multipartFile : files) {
				String filename = multipartFile.getOriginalFilename();
				CommonsMultipartFile cf= (CommonsMultipartFile)multipartFile; 
				DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
				File f = fi.getStoreLocation(); 
				workflowService.saveNewDeploye(f,filename);
			}
		}
		return "redirect:workflowAction.do?method=deployHome";
	}

	public String delDeployment(){
		String deploymentId = workflowBean.getDeploymentId();
		workflowService.deleteProcessDefinitionByDeploymentId(deploymentId);
		return "list";
	}


	public String startProcess(){
		return "listTask";
	}


	/*	@RequestMapping(params="method=getlisttask")
	public @ResponseBody List<Task> listTask(ModelMap modelMap,HttpServletRequest httpServletRequest){
		SysEmployee sysEmployee =	(SysEmployee) httpServletRequest.getSession().getAttribute("sysEmploye");
		String name = sysEmployee.getName();
		List<Task> list = workflowService.findTaskListByName(name); 
		for (Task task : list) {
			System.err.println(task.getId());
			System.err.println(task.getName());
			System.err.println(task.getCreateTime());
			System.err.println(task.getOwner());
			System.err.println(task.getAssignee());
		} 


		return list;
	}*/

	@RequestMapping(params="method=getlisttask")
	public  String execute02(HttpServletRequest request ,HttpServletResponse response)  {


		SysEmployee employee =	(SysEmployee) request.getSession().getAttribute("sysEmploye");
		String name = employee.getName();
		List<String> taksid = new ArrayList<String>();
		List<Task> list = workflowService.findTaskListByName(name); 
		for (Task task : list) {
			taksid.add(task.getId());
		} 
		

		List<Integer> strings = workflowService.findLeaveBillByTaskId(list);

		
		request.setAttribute("claimVoucherids", strings);
		request.setAttribute("taskids", taksid);

		
		
		return "forward:ClaimVoucher.do?method=checkclaimvoucherShowByTask";
	}
	
	
	@RequestMapping(params="method=getlisttaskByShowClaimVoucher")
	public  String execute03(HttpServletRequest request ,HttpServletResponse response)  {


		SysEmployee employee =	(SysEmployee) request.getSession().getAttribute("sysEmploye");
		String name = employee.getName();
		List<String> taksid = new ArrayList<String>();
		List<Task> list = workflowService.findTaskListByName(name); 
		for (Task task : list) {
			taksid.add(task.getId());
		} 
		

		List<Integer> strings = workflowService.findLeaveBillByTaskId(list);

		
		request.setAttribute("claimVoucherids", strings);
		request.setAttribute("taskids", taksid);

		
		
		return "forward:ClaimVoucher.do?method=ClaimVouchershow";
	}


	
	public String audit(){
		String taskId = workflowBean.getTaskId();
		//		LeaveBill leaveBill = workflowService.findLeaveBillByTaskId(taskId);
		//		ValueContext.putValueStack(leaveBill);
		//		List<Comment> commentList = workflowService.findCommentByTaskId(taskId);
		//		ValueContext.putValueContext("commentList", commentList);
		return "taskForm";
	}

	public String submitTask(){
		
		workflowService.saveSubmitTask(workflowBean);
		
		
		return "listTask";

	}
	
	@RequestMapping(params="method=getImage")
	public  void viewImage(HttpServletResponse servletResponse) throws Exception{
		String deploymentId = workflowBean.getDeploymentId();
		String imageName = workflowBean.getImageName();
		InputStream in = workflowService.findImageInputStream(deploymentId,imageName);
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		
	}
	
/*	public String viewTaskForm(){
		//浠诲姟ID
		String taskId = workflowBean.getTaskId();
		String url = workflowService.findTaskFormKeyByTaskId(taskId);
		url += "?taskId="+taskId;
		//		ValueContext.putValueContext("url", url);
		return "viewTaskForm";
	}*/
	
	@RequestMapping(params="method=getStatus")
	public String viewCurrentImage(String taskid,ModelMap modelMap){
		ProcessDefinition processDefinition=  workflowService.findProcessDefinitionByTaskId(taskid);
		modelMap.addAttribute("deploymentId", processDefinition.getDeploymentId());
		modelMap.addAttribute("imageName",processDefinition.getDiagramResourceName());
		Map<String, Object> map=  workflowService.findCoordingByTask(taskid);
		modelMap.addAttribute("x",map.get("x"));
		modelMap.addAttribute("y",map.get("y"));
		modelMap.addAttribute("width",map.get("width"));
		modelMap.addAttribute("height",map.get("height"));
		return "image";
	}





}
