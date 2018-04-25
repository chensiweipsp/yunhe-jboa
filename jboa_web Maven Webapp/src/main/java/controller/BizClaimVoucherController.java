package controller;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import util.JsonData;
import entity.BizClaimVoucher;
import entity.SysDepartment;
import entity.SysDepartmentForJson;
import entity.SysEmployee;
import entity.SysPermission;
import entity.SysRole;
import biz.BizClaimVoucherBiz;
import biz.DeptBIZ;
import biz.EmpBIZ;
import biz.IWorkflowService;
@Controller
@RequestMapping(value={"ClaimVoucher.csv","ClaimVoucher.xls","ClaimVoucher.do"})
public class BizClaimVoucherController {
	@Autowired
	BizClaimVoucherBiz bizClaimVoucherBiz;
	@Autowired
	EmpBIZ empBIZ;

	@Resource(name="workflowService")
	private IWorkflowService workflowService;


	@RequestMapping(params="method=ClaimVouchershow")
	@RequiresPermissions("query")
	public @ResponseBody JsonData execute(HttpServletRequest request ,HttpServletResponse response)  {
		
		
		SysEmployee sysEmployee=(SysEmployee) request.getSession().getAttribute("sysEmploye");
		
		//获取角色权限  
		//		String role = request.getParameter("rols");
		//获取操作权限
		//		String permission = request.getParameter("permissions");
		String previous = request.getParameter("previous");
		String createSn=request.getParameter("createSn");
		String nextDeal=request.getParameter("nextDeal");
		int page=Integer.parseInt( request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		if ("true".equals(previous)) {
			page = page - 1; 
		} 
		JsonData jsonData = new JsonData();
		jsonData.setTotal(bizClaimVoucherBiz.getClaimVoucherCount(createSn, nextDeal, "", sysEmployee,  false));
		jsonData.setRows(bizClaimVoucherBiz.getClaimVouchers(createSn, nextDeal, "", page, rows, sysEmployee,  false));
		
		return jsonData;
	}


	@RequestMapping(params="method=CheckClaimVouchershow")
	public @ResponseBody JsonData execute02(HttpServletRequest request ,HttpServletResponse response)  {
		String previous = request.getParameter("previous");
		String createSn=request.getParameter("createSn");
		String nextDeal=request.getParameter("nextDeal");
		int page=Integer.parseInt( request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		SysEmployee sysEmployee=(SysEmployee) request.getSession().getAttribute("sysEmploye");
		if ("true".equals(previous)) {
			page = page - 1; 
		} 
		JsonData jsonData = new JsonData();
		jsonData.setTotal(bizClaimVoucherBiz.getClaimVoucherCount(createSn, nextDeal, "", sysEmployee,  true));
		jsonData.setRows(bizClaimVoucherBiz.getClaimVouchers(createSn, nextDeal, "", page, rows, sysEmployee,  true));
		return jsonData;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params="method=checkclaimvoucherShowByTask")
	public @ResponseBody JsonData checkclaimvoucherShowByTask(HttpServletRequest request ,HttpServletResponse response)  {

		List<Integer> claimVoucherids = (List<Integer>) request.getAttribute("claimVoucherids");
		//获取TASKID （为了绑定到数据网格中 在审核报销单的时候使用）
		List<String> taskids =(List<String>)  request.getAttribute("taskids");

		String previous = request.getParameter("previous");
		int page=Integer.parseInt( request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		if ("true".equals(previous)) {
			page = page - 1; 
		} 


		List<BizClaimVoucher> bizClaimVouchers=	bizClaimVoucherBiz.getClaimVouchersByTask(claimVoucherids, page, rows);

      //开始对报销单关联业务流程（给予TASKID）
		for (BizClaimVoucher bizClaimVoucher : bizClaimVouchers) {

			for (String taskid : taskids) {
				ProcessInstance processInstance=workflowService.findProcessInstanceByTaskId(taskid);
				String id =processInstance.getBusinessKey().split("\\.")[1];
				if(id.equals(bizClaimVoucher.getId().toString()))
				{
					bizClaimVoucher.setTaskid(taskid);
				}
			}

		}
		
		JsonData jsonData = new JsonData();
		jsonData.setTotal(bizClaimVoucherBiz.getClaimVouchersCountByTask(claimVoucherids, page, rows));
		jsonData.setRows(bizClaimVouchers);

		return jsonData;
	}

	@RequestMapping(params="method=gitStatus",method=RequestMethod.POST)
	public String gitStatus(HttpServletRequest request,HttpServletResponse response,ModelMap map)
	{
		String id=request.getParameter("id");
		BizClaimVoucher bizClaimVoucher=bizClaimVoucherBiz.findByID(id);
		map.put("schedule", bizClaimVoucher.getSchedule());
		return "BizClaimVoucher/status";
	}


	@RequestMapping(params="method=saveorUpdate")
	public @ResponseBody String saveorUpdate(HttpServletRequest request ,HttpServletResponse response) throws ParseException
	{
		int id =0;
		BizClaimVoucher bizClaimVoucher =new BizClaimVoucher();
		if(null!=request.getParameter("id"))
		{
			id=Integer.parseInt( request.getParameter("id"));
			bizClaimVoucher.setId(Long.parseLong(String.valueOf(id)));
		}

		String createSn=request.getParameter("createSn");
		String nextDealSn=request.getParameter("nextDealSn");
		String createTime=request.getParameter("createTime");
		String event=request.getParameter("event");
		String totalAccount=request.getParameter("totalAccount");
		String status=request.getParameter("status");
		String taskid=request.getParameter("taskid");

		bizClaimVoucher.setCreateSn(new SysEmployee(Integer.parseInt(createSn)));
		bizClaimVoucher.setNextDealSn(new SysEmployee(Integer.parseInt(nextDealSn)));
		bizClaimVoucher.setCreateTime(new SimpleDateFormat("MM/dd/yyyy").parse(createTime));
		bizClaimVoucher.setEvent(event);
		bizClaimVoucher.setTotalAccount(new Double(totalAccount));
		bizClaimVoucher.setStatus(status);
		bizClaimVoucher.setTaskid(taskid);
		bizClaimVoucherBiz.SaveOrUpdateClaimVouchers(bizClaimVoucher);



		return "ok";
	}

	@SuppressWarnings("unused")
	@RequestMapping(params="method=ClaimVouchercomm",method=RequestMethod.POST)
	public @ResponseBody String claimvouchercomm(HttpServletRequest request,HttpServletResponse response)
	{
		SysEmployee sysEmployee =(SysEmployee) request.getSession().getAttribute("sysEmploye");
		//		String sn=sysEmployee.getSn();
		String cn=request.getParameter("createSn");
		String sn=request.getParameter("nextDealSn");
		//		String power =sysEmployee.getPosition().getNameEn();
		String id=request.getParameter("id");
		String comm=request.getParameter("comm");
		String ispass=request.getParameter("ispass");
		bizClaimVoucherBiz.ispass( request,ispass, Long.parseLong(id), comm, null==sn?Integer.parseInt(cn):Integer.parseInt(sn),Integer.parseInt(cn));





		//启动流程






		return "ok";
	}

	@RequestMapping(params="method=delete")
	public @ResponseBody int delete(HttpServletRequest request ,HttpServletResponse response)
	{
		String deleteIDs=request.getParameter("deleteIDs");
		int count=0;
		String[] ids = deleteIDs.split(",");
		for (int i = 0; i < ids.length; i++) {
			bizClaimVoucherBiz.deleteClaimVouchers(Integer.parseInt(ids[i]));
			count++;
		}
		return count;
	}

	/**PDF
	 * @throws ParseException */
	@RequestMapping(params="method=getpdfReport",method=RequestMethod.POST)
	@RequiresPermissions("exeport")
	public  ModelAndView doSalesReportPDF(HttpServletRequest request,ModelAndView modelAndView) throws ParseException 
	{
		String size = request.getParameter("size");
		List<BizClaimVoucher> bizClaimVouchers=new ArrayList<BizClaimVoucher>();
		for (int i = 0; i < Integer.parseInt(size); i++) {
			String bObject = request.getParameter(String.valueOf(i));
			String[] args= bObject.split(","); 
			BizClaimVoucher bizClaimVoucher=new BizClaimVoucher();
			bizClaimVoucher.setId(Long.parseLong(args[0]));
			SysEmployee sysEmployee=	new SysEmployee() ;
			sysEmployee.setName(args[1]);
			bizClaimVoucher.setCreateSn(sysEmployee);
			SysEmployee sysEmployee2=	new SysEmployee() ;
			sysEmployee2.setName(args[2]);
			bizClaimVoucher.setNextDealSn(sysEmployee2);
			bizClaimVoucher.setCreateTime(new Timestamp(Long.parseLong(args[3])));
			bizClaimVoucher.setEvent(args[4]);
			bizClaimVoucher.setTotalAccount(Double.parseDouble( args[5]));
			bizClaimVoucher.setStatus(args[6]);
			bizClaimVouchers.add(bizClaimVoucher);
		}
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(bizClaimVouchers);
		//		    response.setContentType("application/vnd.ms-execl");
		//		    response.setHeader("Content-Disposition","attachment;filename="+new String(fileNames.getBytes("gbk"),"iso8859-1")+".xls");
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("datasource", data);
		/*        注意 bizClaimVoucher 对应 BizClaimVoucher.jrxml
			中的	<parameter name="bizClaimVoucher" 
			class="com.zx.entity.BizClaimVoucher"/>*/
		parameterMap.put("bizClaimVoucher", new BizClaimVoucher());
		modelAndView = new ModelAndView("pdfReport", parameterMap);
		return modelAndView;
	}
	/**HTML*/
	@RequestMapping(params="method=gethtmlReport",method=RequestMethod.POST)
	@RequiresPermissions("exeport")
	public ModelAndView doSalesReporthtml(HttpServletRequest request,ModelAndView modelAndView) 
	{
		String size = request.getParameter("size");
		List<BizClaimVoucher> bizClaimVouchers=new ArrayList<BizClaimVoucher>();
		for (int i = 0; i < Integer.parseInt(size); i++) {
			String bObject = request.getParameter(String.valueOf(i));
			String[] args= bObject.split(",");
			BizClaimVoucher bizClaimVoucher=new BizClaimVoucher();
			bizClaimVoucher.setId(Long.parseLong(args[0]));
			SysEmployee sysEmployee=	new SysEmployee() ;
			sysEmployee.setName(args[1]);
			bizClaimVoucher.setCreateSn(sysEmployee);
			SysEmployee sysEmployee2=	new SysEmployee() ;
			sysEmployee2.setName(args[2]);
			bizClaimVoucher.setNextDealSn(sysEmployee2);
			bizClaimVoucher.setCreateTime(new Timestamp(Long.parseLong(args[3])));
			bizClaimVoucher.setEvent(args[4]);
			bizClaimVoucher.setTotalAccount(Double.parseDouble( args[5]));
			bizClaimVoucher.setStatus(args[6]);
			bizClaimVouchers.add(bizClaimVoucher);
		}
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(bizClaimVouchers);
		//		    response.setContentType("application/vnd.ms-execl");
		//		    response.setHeader("Content-Disposition","attachment;filename="+new String(fileNames.getBytes("gbk"),"iso8859-1")+".xls");
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("datasource", data);
		/*        注意 bizClaimVoucher 对应 BizClaimVoucher.jrxml
			中的	<parameter name="bizClaimVoucher" 
			class="com.zx.entity.BizClaimVoucher"/>*/
		parameterMap.put("bizClaimVoucher", new BizClaimVoucher());
		modelAndView = new ModelAndView("htmlReport", parameterMap);
		return modelAndView;
	}

	/**VCS*/
	@RequestMapping(params="method=getcvsReport",method=RequestMethod.POST)
	@RequiresPermissions("exeport")
	public ModelAndView doSalesReportcvs(HttpServletRequest request,ModelAndView modelAndView) 
	{
		String size = request.getParameter("size");
		List<BizClaimVoucher> bizClaimVouchers=new ArrayList<BizClaimVoucher>();
		for (int i = 0; i < Integer.parseInt(size); i++) {
			String bObject = request.getParameter(String.valueOf(i));
			String[] args= bObject.split(",");
			BizClaimVoucher bizClaimVoucher=new BizClaimVoucher();
			bizClaimVoucher.setId(Long.parseLong(args[0]));
			SysEmployee sysEmployee=	new SysEmployee() ;
			sysEmployee.setName(args[1]);
			bizClaimVoucher.setCreateSn(sysEmployee);
			SysEmployee sysEmployee2=	new SysEmployee() ;
			sysEmployee2.setName(args[2]);
			bizClaimVoucher.setNextDealSn(sysEmployee2);
			bizClaimVoucher.setCreateTime(new Timestamp(Long.parseLong(args[3])));
			bizClaimVoucher.setEvent(args[4]);
			bizClaimVoucher.setTotalAccount(Double.parseDouble( args[5]));
			bizClaimVoucher.setStatus(args[6]);
			bizClaimVouchers.add(bizClaimVoucher);
		}
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(bizClaimVouchers);
		//		    response.setContentType("application/vnd.ms-execl");
		//		    response.setHeader("Content-Disposition","attachment;filename="+new String(fileNames.getBytes("gbk"),"iso8859-1")+".xls");

		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("datasource", data);
		/*        注意 bizClaimVoucher 对应 BizClaimVoucher.jrxml
			中的	<parameter name="bizClaimVoucher" 
			class="com.zx.entity.BizClaimVoucher"/>*/
		parameterMap.put("bizClaimVoucher", new BizClaimVoucher());
		modelAndView = new ModelAndView("csvReport", parameterMap);
		return modelAndView;
	}

	/**
	 * xls
	 * @param pagesize
	 * @param pagenum
	 * @param modelAndView
	 * @return
	 */
	/*	@RequestMapping(params="method=getxlsReport",method=RequestMethod.POST,produces="application/vnd.ms-execl")
	 */	
	@RequestMapping(params="method=getxlsReport",method=RequestMethod.POST)
	@RequiresPermissions("exeport")
	public ModelAndView doSalesReportxls(HttpServletRequest request,ModelAndView modelAndView) 
	{
		String size = request.getParameter("size");
		List<BizClaimVoucher> bizClaimVouchers=new ArrayList<BizClaimVoucher>();
		for (int i = 0; i < Integer.parseInt(size); i++) {
			String bObject = request.getParameter(String.valueOf(i));
			String[] args= bObject.split(",");
			BizClaimVoucher bizClaimVoucher=new BizClaimVoucher();
			bizClaimVoucher.setId(Long.parseLong(args[0]));
			SysEmployee sysEmployee=	new SysEmployee() ;
			sysEmployee.setName(args[1]);
			bizClaimVoucher.setCreateSn(sysEmployee);
			SysEmployee sysEmployee2=	new SysEmployee() ;
			sysEmployee2.setName(args[2]);
			bizClaimVoucher.setNextDealSn(sysEmployee2);
			bizClaimVoucher.setCreateTime(new Timestamp(Long.parseLong(args[3])));
			bizClaimVoucher.setEvent(args[4]);
			bizClaimVoucher.setTotalAccount(Double.parseDouble( args[5]));
			bizClaimVoucher.setStatus(args[6]);
			bizClaimVouchers.add(bizClaimVoucher);
		}
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(bizClaimVouchers);
		//		    response.setContentType("application/vnd.ms-execl");
		//		    response.setHeader("Content-Disposition","attachment;filename="+new String(fileNames.getBytes("gbk"),"iso8859-1")+".xls");
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("datasource", data);
		parameterMap.put("bizClaimVoucher", new BizClaimVoucher());
		modelAndView = new ModelAndView("xlsReport", parameterMap);
		return modelAndView;
	}
	@RequestMapping(params="method=getnextDealSn" ,method=RequestMethod.POST)
	public @ResponseBody SysDepartmentForJson getnextDealSn(HttpServletRequest request,HttpServletResponse response)
	{
		/*	String cn=request.getParameter("createSn");
		Set<SysDepartment> departments=bizClaimVoucherBiz.getnextDealSn(Integer.parseInt(cn));
		SysDepartmentForJson sysDepartment=new SysDepartmentForJson();
		for (SysDepartment sysDepartment2 : departments){
			sysDepartment.setName(sysDepartment2.getName());
			sysDepartment.setEmps(sysDepartment2.getEmps());
			sysDepartment.setEmpsLength(sysDepartment2.getEmps().size());
		}
		for (SysEmployee emp : sysDepartment.getEmps()) {
			System.out.println(emp.getName());
		}*/
		String cn=request.getParameter("cnname");
		/*		String role=request.getParameter("role");
		 */		SysEmployee employee=empBIZ.findUserByUserSn(Integer.parseInt(cn));
		 String role=null;
		 //目前只实现了  一个用户一个角色的功能  如果是多角色用LIST<STRING>集合然后在对应业务层实现
		 for (SysRole  role1 : employee.getRoles()) {
			 role = role1.getRolename();
		 }
		 SysDepartment department=bizClaimVoucherBiz.getnextDealSn(employee.getDept(), role);
		 SysDepartmentForJson departmentForJson =new SysDepartmentForJson();
		 departmentForJson.setEmps(department.getEmps());
		 departmentForJson.setEmpsLength(department.getEmps().size());
		 departmentForJson.setId(department.getId());
		 departmentForJson.setName(department.getName());
		 return departmentForJson;
	}




}
