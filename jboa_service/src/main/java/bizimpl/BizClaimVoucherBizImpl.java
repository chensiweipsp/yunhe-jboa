package bizimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.BizClaimVoucherBiz;
import biz.DeptBIZ;

import dao.BizClaimVoucherDao;
import dao.DeptDAO;
import entity.BizClaimVoucher;
import entity.SysDepartment;
import entity.SysEmployee;
import entity.SysPermission;
import entity.SysRole;


@Service("bizClaimVoucherBiz")
public class BizClaimVoucherBizImpl implements BizClaimVoucherBiz {
	@Autowired
	BizClaimVoucherDao bizClaimVoucherDao;
	@Autowired
	DeptDAO deptDAO;
	@Override
	public BizClaimVoucher SaveOrUpdateClaimVouchers(BizClaimVoucher BizClaimVoucher
			) {
		// TODO Auto-generated method stub
		return bizClaimVoucherDao.SaveOrUpdateClaimVouchers(BizClaimVoucher);
	}
	@Override
	public int deleteClaimVouchers(int id) {
		// TODO Auto-generated method stub
		return bizClaimVoucherDao.deleteClaimVouchers(id);
	}

	@Override
	public int ispass(HttpServletRequest request,String pass, long id, String comm,int sn,int cn) {

		int count=0;

		if("yes".equals(pass))
		{
			count=	bizClaimVoucherDao.pass( request,pass, id, comm, sn,cn);
		}
		if("no".equals(pass))
		{
			count=	bizClaimVoucherDao.notgo( request,pass, id, comm, sn,cn);
		}

		return count;
	}
	@Override
	public BizClaimVoucher findByID(String id) {
		return bizClaimVoucherDao.findByID(id);
	}
	@Override
	public SysDepartment getnextDealSn(SysDepartment dept,String role) {
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<SysEmployee> iterator= dept.getEmps().iterator();
		Set<SysEmployee> employees = new HashSet<>();
		if(role.equals("staff"))
		{
			dept=	deptDAO.findDeptByName(dept.getName());

		while (iterator.hasNext()) {
			SysEmployee sysEmployee = (SysEmployee) iterator.next();
			  for (SysRole sysRole : sysEmployee.getRoles()) {
				  if(sysRole.getRolename().equals("manager"))
				  {
					  employees.add(sysEmployee);
				  }
				}
		}
		dept.setEmps(employees);
		} else if (role.equals("manager"))
		{
			//查找财务部
			dept=	deptDAO.findDeptByCashier();
		}
		else if (role.equals("cashier"))
		{
			//查找总经理办公室
			dept=	deptDAO.findDeptByGeneralmanager();
		}
		else if(role.equals("generalmanager"))
		{
			//查找财务部
			dept=	deptDAO.findDeptByCashier();
		}
		return dept;
	}
	@Override
	public int getClaimVoucherCount(String searchCreateSn,String searchnextDealSn,String hql, SysEmployee sEmploye,
			Boolean isLookThrough) {
		StringBuilder stringBuilder = new StringBuilder("select count(*) from BizClaimVoucher as bv  where 1=1 ");

		//如果为true则为 审核
		if(isLookThrough==false)
		{
		if(null==searchCreateSn&&null==searchnextDealSn)
		{
				if(SecurityUtils.getSubject().isPermitted("all"))
				{
					stringBuilder.append(" ");
				}
				else if (SecurityUtils.getSubject().isPermitted("self"))
				{
					stringBuilder.append(" and bv.createSn.sn = "+sEmploye.getSn()+" ");
				}
				else if (SecurityUtils.getSubject().isPermitted("selfdept"))
				{
					if(SecurityUtils.getSubject().hasRole("财务部"))
					{
						stringBuilder.append(" ");

						return bizClaimVoucherDao.getClaimVoucherCount(stringBuilder.toString(), isLookThrough);

					}
					stringBuilder.append(" and bv.createSn.dept.name = '"+sEmploye.getDept().getName()+"' ");
				}
		}
		else if(null!=searchCreateSn)
		{
			stringBuilder.append("and bv.createSn.sn='"+searchCreateSn+"'");
		}else if (null!=searchnextDealSn)
		{
			stringBuilder.append("and bv.nextDealSn.sn='"+searchnextDealSn+"'");
		}
		}
		else
		{
			stringBuilder.append("and bv.nextDealSn.sn="+sEmploye.getSn()+"");;			
		}
		
		return bizClaimVoucherDao.getClaimVoucherCount(stringBuilder.toString(), isLookThrough);
	}

	
	@Override
	public List<BizClaimVoucher> getClaimVouchers(String searchCreateSn,String searchnextDealSn,String hql,int page, int rows,
			SysEmployee sEmployee, 
			Boolean isLookThrough) {
		StringBuilder stringBuilder = new StringBuilder(" from BizClaimVoucher as bv  where 1=1 ");
		//如果为true则为 审核
		if(isLookThrough==false)
		{
		if(null==searchCreateSn&&null==searchnextDealSn)
		{
				if(SecurityUtils.getSubject().isPermitted("all"))
				{
					stringBuilder.append(" ");
				}
				else if (SecurityUtils.getSubject().isPermitted("self"))
				{
					stringBuilder.append(" and bv.createSn.sn = "+sEmployee.getSn()+" ");
				}
				else if (SecurityUtils.getSubject().isPermitted("selfdept"))
				{
					if(SecurityUtils.getSubject().hasRole("财务部"))
					{
						stringBuilder.append(" ");
						return bizClaimVoucherDao.getClaimVouchers(page, rows, stringBuilder.toString(), isLookThrough);
					}
					stringBuilder.append(" and bv.createSn.dept.name = '"+sEmployee.getDept().getName()+"' ");
				}
		}
		else if(null!=searchCreateSn)
		{
			stringBuilder.append("and bv.createSn.sn='"+searchCreateSn+"'");
		}else if (null!=searchnextDealSn)
		{
			stringBuilder.append("and bv.nextDealSn.sn='"+searchnextDealSn+"'");
		}
		}
		else
		{
			stringBuilder.append("and bv.nextDealSn.sn="+sEmployee.getSn()+"");;			
		}
		return bizClaimVoucherDao.getClaimVouchers(page, rows, stringBuilder.toString(), isLookThrough);
	}
	@Override
	public List<BizClaimVoucher> getClaimVouchersByTask(List<Integer> bizclaimvoucherids, int page, int rows) {
		// TODO Auto-generated method stub
		return bizClaimVoucherDao.getClaimVouchersByTask(bizclaimvoucherids, page, rows);
	}
	@Override
	public int getClaimVouchersCountByTask(List<Integer> bizclaimvoucherids, int page, int rows) {
		// TODO Auto-generated method stub
		return bizClaimVoucherDao.getClaimVouchersCountByTask(bizclaimvoucherids, page, rows);
	}
}
