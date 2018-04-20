package utils;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import entity.SysEmployee;


/**
 * 员工经理任务分配
 *
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {

	public void notify(DelegateTask delegateTask) {
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		delegateTask.getAssignee();
		delegateTask.setAssignee(delegateTask.getAssignee());
		
//		SysEmployee employee =(SysEmployee) request.getSession().getAttribute("sysEmploye");
		/**懒加载异常*/
//		//设置个人任务的办理人
//		delegateTask.setAssignee(employee.getManager().getName());
		/**从新查询当前用户，再获取当前用户对应的领导*/
//		Employee employee = SessionContext.get();
		//当前用户
//		String name = employee.getName();
		//使用当前用户名查询用户的详细信息
		//从web中获取spring容器
//		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
//		IEmployeeService employeeService = (IEmployeeService) ac.getBean("employeeService");
//		Employee emp = employeeService.findEmployeeByName(name);
		//设置个人任务的办理人
//		delegateTask.setAssignee(emp.getManager().getName());
		
	}

}
