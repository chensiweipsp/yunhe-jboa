package aop;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import dao.BizClaimVoucherDao;
import entity.BizClaimVoucher;



@Aspect
public class BizClaimVoucherAop {

	@Resource(name="bizClaimVoucherDao")
	BizClaimVoucherDao bizClaimVoucherDao;

	@Resource(name="workflowService")
	biz.IWorkflowService IWorkflowService;

	@Around("execution(* daoimpl.BizClaimVoucherDaoImpl.SaveOrUpdateClaimVouchers(..))")
	public void send(ProceedingJoinPoint joinPoint)
	{
		Object[] objects=	joinPoint.getArgs();


		BizClaimVoucher bizClaimVoucher= (BizClaimVoucher) objects[0];

		String key=bizClaimVoucher.getClass().getSimpleName();
		
		int id =((Long)bizClaimVoucher.getId()).intValue();
		
		bizClaimVoucher= bizClaimVoucherDao.findByID(String.valueOf(id));

		String ename =bizClaimVoucher.getCreateSn().getName();
		
		IWorkflowService.saveStartProcess(ename, key, id);
		
		
		

	}

}
