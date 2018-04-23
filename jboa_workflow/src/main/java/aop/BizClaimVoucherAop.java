package aop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import dao.BizClaimVoucherDao;
import dao.SysEmployeeDAO;
import entity.BizClaimVoucher;
import entity.SysEmployee;
import entity.SysRole;
import form.WorkflowBean;

@Aspect
public class BizClaimVoucherAop {
	@Autowired  
	private HttpSession session;  
	@Resource(name="bizClaimVoucherDao")
	BizClaimVoucherDao bizClaimVoucherDao;

	@Resource(name="empDAO")
	SysEmployeeDAO sysEmployeeDao;

	@Resource(name="workflowService")
	biz.IWorkflowService IWorkflowService;

	@Around("execution(* daoimpl.BizClaimVoucherDaoImpl.SaveOrUpdateClaimVouchers(..))")
	public Object send(ProceedingJoinPoint joinPoint)
	{
		Object[] objects=	joinPoint.getArgs();

		BizClaimVoucher bizClaimVoucher= (BizClaimVoucher) objects[0];
		String saveorupdate =null!=bizClaimVoucher.getId()?"update":"save";

		BizClaimVoucher result = null;
		try {
			result=(BizClaimVoucher) joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取下一个任务的办理人
		int createEmpSn= bizClaimVoucher.getCreateSn().getSn();
		String createEmp = ((SysEmployee)sysEmployeeDao.findUserByUserSn(createEmpSn)).getName();
		int nextDealSn= bizClaimVoucher.getNextDealSn().getSn();
		String nextDeal = ((SysEmployee)sysEmployeeDao.findUserByUserSn(nextDealSn)).getName();


		String key=bizClaimVoucher.getClass().getSimpleName();

		//		int id =((Long)bizClaimVoucher.getId()).intValue();
		String id = result.getId().toString();

		bizClaimVoucher= bizClaimVoucherDao.findByID(String.valueOf(id));

		//		String ename =bizClaimVoucher.getCreateSn().getName();

		session.setAttribute("nextEmp", nextDeal);

		System.err.println(session.getAttribute("createSn"));
		try {
			IWorkflowService.saveStartProcess(saveorupdate,createEmp, nextDeal,key, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object resurlt=null;

		return resurlt;
	}


	/*报销单审核*/
	@Around("execution(* bizimpl.BizClaimVoucherBizImpl.ispass(..))")
	public Object checkClaimVoucher(ProceedingJoinPoint joinPoint)
	{
		Object result=null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}

		Object[] objects=	joinPoint.getArgs();
		HttpServletRequest request = (HttpServletRequest) objects[0];

		//获取任务ID
		String taskId= !request.getParameter("taskid").equals("")?request.getParameter("taskid"):null;
		//获取连线的名称
		String outcome = null;
		if(SecurityUtils.getSubject().hasRole("staff"))
		{
			outcome= "staff";
		}
		if(SecurityUtils.getSubject().hasRole("manager"))
		{
			outcome=  "manager";
		}
		if(SecurityUtils.getSubject().hasRole("generalmanager"))
		{
			outcome= "generalmanager";
		}
		if(SecurityUtils.getSubject().hasRole("cashier"))
		{
			outcome=  "cashier";
		}

		//批注信息
		String comment=!request.getParameter("comm").equals("")?request.getParameter("comm"):null;
		//获取报销单ID
		String id =!request.getParameter("id").equals("")?request.getParameter("id"):null;
		//获取代办人
		String nextDealSn=null!=request.getParameter("nextDealSn")&& !request.getParameter("nextDealSn").equals("")?request.getParameter("nextDealSn"):null;
		//填报人
		String createSn=!request.getParameter("createSn").equals("")?request.getParameter("createSn"):null;
		//审核人权限
		String auditorRolename=null;
		if(SecurityUtils.getSubject().hasRole("staff"))
		{
			auditorRolename="staff";
		}
		else if(SecurityUtils.getSubject().hasRole("manager"))
		{
			auditorRolename="manager";

		}
		else if(SecurityUtils.getSubject().hasRole("generalmanager"))
		{
			auditorRolename="generalmanager";

		}
		else if(SecurityUtils.getSubject().hasRole("cashier"))
		{
			auditorRolename="cashier";

		}

		//是否通过审核

		String ispass  = (String) objects[1];


		SysEmployee nextEmp=	sysEmployeeDao.findUserByUserSn(Integer.parseInt(null==nextDealSn?createSn:nextDealSn));

		session.setAttribute("nextEmp", nextEmp.getName());


		SysEmployee createEmp=	sysEmployeeDao.findUserByUserSn(Integer.parseInt(createSn));



		List<String> roles = new ArrayList<String>();
		for (SysRole role : createEmp.getRoles()) {
			roles.add(role.getRolename());
		}


		WorkflowBean workflowBean =new WorkflowBean();
		workflowBean.setId(Long.parseLong(id));
		workflowBean.setComment(comment);
		workflowBean.setTaskId(taskId);
		workflowBean.setAssignee(nextEmp.getName());
		workflowBean.setOutcome(outcome);
		workflowBean.setCreateEmpRolNames(roles);
		workflowBean.setAuditorRolename(auditorRolename);
		workflowBean.setIspass(ispass);
		IWorkflowService.saveSubmitTask(workflowBean);


		return result;
	}

}
