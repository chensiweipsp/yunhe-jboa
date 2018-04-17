package bizimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DeptDAO;

import entity.SysDepartment;
import entity.SysEmployee;
import biz.DeptBIZ;

@Service("deptBIZ")
public class DeptBIZImpl implements DeptBIZ{

	@Autowired
	DeptDAO deptDAO;

	@Override
	public Set<SysDepartment> addFindall(String power) {
		// TODO Auto-generated method stub
		return deptDAO.addFindall(power);
	}


	@Override
	public Set<SysDepartment> updateFindall(String power) {
		// TODO Auto-generated method stub
		return deptDAO.updateFindall(power);
	}

	@Override
	public List<SysDepartment> searchFindall(SysEmployee sysEmployee) {
		// TODO Auto-generated method stub
		StringBuilder hql =new StringBuilder("from SysDepartment as dpt where 1=1 ");

			if(SecurityUtils.getSubject().hasRole("manager"))
			{
				hql.append(" and dpt.name = '"+sysEmployee.getDept().getName()+"' ");
			}
		return deptDAO.searchFindall(hql.toString());
	}


	@Override
	public SysDepartment findDeptByName(String deptname) {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
