package filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

public class SystemLogoutFilter  extends LogoutFilter{

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		
		Subject subject=  getSubject(request, response);
		
		
		subject.logout();
		
		
		issueRedirect(request, response, "/");
		
		return false;
	}
			
	
}
