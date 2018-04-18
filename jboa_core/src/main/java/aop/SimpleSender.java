package aop;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import dao.SysEmployeeDAO;
import entity.BizClaimVoucher;
import entity.SysEmployee;

@Service
@Aspect
public class SimpleSender {



	@Resource(name="javaMailSender")
	private JavaMailSender javaMailSender;
	@Resource(name="empDAO")
    SysEmployeeDAO sysEmployeeDAO;


	@Around("execution(* daoimpl.BizClaimVoucherDaoImpl.SaveOrUpdateClaimVouchers(..))")
	public Object send(ProceedingJoinPoint joinPoint)
	{
		
		javaMailSender=	(JavaMailSender) new ClassPathXmlApplicationContext("email.xml").getBean("javaMailSender");

		
		Object object =null;
		
		System.err.println("正在发送邮箱!!。。。。");
		
		try {
			
			object=	joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		Object[]	 objects=	joinPoint.getArgs();
		BizClaimVoucher bizclaimvoucher  = (BizClaimVoucher) objects[0];

		SysEmployee cnSysEmployee=	sysEmployeeDAO.findUserByUserSn(bizclaimvoucher.getCreateSn().getSn());
		SysEmployee nextSysEmployee=	sysEmployeeDAO.findUserByUserSn(bizclaimvoucher.getNextDealSn().getSn());

		String cnempString=cnSysEmployee.getName();
        String nextEmpEmail =nextSysEmployee.getEmail();
		mailMessage.setSubject(cnempString+"有一条报销单待您审核");
		
		mailMessage.setText(cnempString+"有一条报销单待您审核   申请于  "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"");

		mailMessage.setFrom("295255658@qq.com");
		
		mailMessage.setTo(nextEmpEmail);
		
		try {
			javaMailSender.send(mailMessage);

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return object;
	}










}
