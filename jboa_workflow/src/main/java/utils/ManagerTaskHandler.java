package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import entity.SysEmployee;


/**
 * 员工经理任务分配
 *
 */
@SuppressWarnings("serial")

public class ManagerTaskHandler implements TaskListener {
	@Autowired  
	private HttpSession session;  
	public void notify(DelegateTask delegateTask) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		delegateTask.setAssignee((String) request.getSession().getAttribute("nextEmp"));
		
		
	}

}
