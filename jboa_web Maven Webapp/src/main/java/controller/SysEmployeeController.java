package controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import realm.EmpRealm;
import biz.BizClaimVoucherBiz;
import biz.DeptBIZ;
import biz.EmpBIZ;
import entity.SysDepartment;
import entity.SysEmployee;
import entity.SysRole;
@Controller
@RequestMapping("emp.do")
public class SysEmployeeController {
	@Resource
	EmpBIZ empBIZ;
	@Autowired
	DeptBIZ deptBIZ;
	@Autowired
	BizClaimVoucherBiz bizClaimVoucherBiz;
	@RequestMapping(params="method=login",method=RequestMethod.POST)
	public String login(HttpServletRequest request,HttpServletResponse response,String username,String password,String random,ModelMap map){  
		try {  
			//使用安全管理器工具进行用户登录，登录成功后跳到shiro配置的successUrl中，与下面的return没什么关系！  
			try {
				SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));  
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "login";
			}
			SysEmployee sysEmployee=(SysEmployee) request.getSession().getAttribute("sysEmploye");
			//初始化搜索框   ,初始化添加报销单待处理人栏
			initializeSearch(sysEmployee,request);
			return "index";  
		} catch (AuthenticationException e) {  
			map.put("message","用户名或密码错误");  
			return "login";  
		}
	}  
	@RequestMapping(params="method=getroles")
	@RequiresPermissions("query")
	public void getroles(HttpServletRequest request)
	{
		Subject currenUser = SecurityUtils.getSubject();
		HttpSession session = request.getSession();
		for ( Object object : currenUser.getSession().getAttributeKeys() )
		{
			System.out.println(object);
		}	
		System.err.println( request.getSession().getAttribute("numrand"));
		System.err.println( request.getSession().getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY"));
		System.err.println( request.getSession().getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"));
		System.err.println( request.getSession().getAttribute("org.apache.shiro.web.session.HttpServletSession.HOST_SESSION_KEY"));
	}
	
	public void initializeSearch(SysEmployee employee,HttpServletRequest request)
	{
			Set<SysRole> roles= employee.getRoles();
			Set<String> Stringroles = new HashSet<String>();
			for (SysRole sysRole : roles){
				//赋予角色权限
				Stringroles.add(sysRole.getRolename());
			}
			List<SysDepartment> searchDepts=deptBIZ.searchFindall(employee); 
			request.getSession().setAttribute("SearchDepts", searchDepts);
			//单角色权限
			String role =null;
			for (String role2 : Stringroles) {
				role=role2;
			}
			SysDepartment department=bizClaimVoucherBiz.getnextDealSn(employee.getDept(), role);
			request.getSession().setAttribute("AddDepts", department);
	}
}