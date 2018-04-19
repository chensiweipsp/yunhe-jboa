package aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import dao.BizClaimVoucherDao;
import dao.SysEmployeeDAO;
import entity.BizClaimVoucher;
import entity.SysEmployee;

@Aspect
public class BizClaimVoucherAop {

	@Resource(name="bizClaimVoucherDao")
	BizClaimVoucherDao bizClaimVoucherDao;

	@Resource(name="empDAO")
	SysEmployeeDAO sysEmployeeDao;
	
	@Resource(name="workflowService")
	biz.IWorkflowService IWorkflowService;

	@Around("execution(* daoimpl.BizClaimVoucherDaoImpl.SaveOrUpdateClaimVouchers(..))")
	public Object send(ProceedingJoinPoint joinPoint)
	{
		BizClaimVoucher result = null;
		try {
			result=(BizClaimVoucher) joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[] objects=	joinPoint.getArgs();


		BizClaimVoucher bizClaimVoucher= (BizClaimVoucher) objects[0];
		
		//获取下一个任务的办理人
		int assignno= bizClaimVoucher.getNextDealSn().getSn();
		String assign = ((SysEmployee)sysEmployeeDao.findUserByUserSn(assignno)).getName();
		

		String key=bizClaimVoucher.getClass().getSimpleName();
		
//		int id =((Long)bizClaimVoucher.getId()).intValue();
		
		String id = result.getId().toString();
		
		bizClaimVoucher= bizClaimVoucherDao.findByID(String.valueOf(id));

//		String ename =bizClaimVoucher.getCreateSn().getName();
		
		IWorkflowService.saveStartProcess(assign, key, id);
		Object resurlt=null;

		return resurlt;
	}

	
	
/*	@Around("execution(* controller.BizClaimVoucherController.CheckClaimVouchershow(..))")
	public Object checkClaimVoucher(ProceedingJoinPoint joinPoint)
	{
		Subject subject = SecurityUtils.getSubject();
		
		String sysempname = (String) subject.getPrincipal();
		
		
		return null;
	}*/

}
