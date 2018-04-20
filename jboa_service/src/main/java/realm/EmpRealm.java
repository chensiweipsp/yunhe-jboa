package realm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import dao.DeptDAO;
import dao.SysEmployeeDAO;
import entity.ActiveEmp;
import entity.SysDepartment;
import entity.SysEmployee;
import entity.SysPermission;
import entity.SysRole;
import biz.EmpBIZ;

@Component("realm")
public class EmpRealm extends AuthorizingRealm {  
	@Resource(name="empDAO")
	SysEmployeeDAO employeeDAO;
	@Resource(name="deptDAO")
	DeptDAO deptDAO;
	
	HttpServletRequest request;
	HttpServletResponse response;	
	/** 
	 * 授权
	 */  
	@Override  
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {  
		//获取登录时输入的用户名  
		String username= (String) principalCollection.getPrimaryPrincipal();  
		//到数据库查是否有此对象  
		SysEmployee emp=employeeDAO.findUserByUsername(username);  

		Set<SysRole> roles= emp.getRoles();

		List<String> permissions = new ArrayList<>();
		List<String> Stringroles = new ArrayList<>();

		for (SysRole sysRole : roles) {
			//赋予角色权限
			Stringroles.add(sysRole.getRolename());
			//赋予部门权限
			Stringroles.add(emp.getDept().getName());
			for (SysPermission permission : sysRole.getPermissions()) {
				//赋予操作权限
				permissions.add(permission.getPermissionname());
			}
		}
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(permissions);
		authorizationInfo.addRoles(Stringroles);
		return authorizationInfo;  
	}  
	/** 
	 * 认证; 
	 */  
	@Override  
	protected AuthenticationInfo doGetAuthenticationInfo(  
			AuthenticationToken authenticationToken) throws AuthenticationException {  
		//UsernamePasswordToken对象用来存放提交的登录信息  
		UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
		
		//查出是否有此用户  
		SysEmployee sysEmploye=null;
		try {
			sysEmploye = employeeDAO.findUserByUsername(token.getUsername());
			
			//SPRING耦合获取REQUEST
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session= request.getSession();
			session.setMaxInactiveInterval(-1);
			session.setAttribute("sysEmploye", sysEmploye);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(sysEmploye!=null){  
			//若存在，将此用户存放到登录认证info中   principal 主体    credentials 凭证   realmName
			return new SimpleAuthenticationInfo(sysEmploye.getName(), sysEmploye.getPassword(), ByteSource.Util.bytes(sysEmploye.getSalt()),this.getName());  
		}  
		
		return null;
	}

	public static void main(String[] args) {
		Md5Hash hash = new Md5Hash("123","sw",1);

		System.err.println(hash.toString());
	}
}  