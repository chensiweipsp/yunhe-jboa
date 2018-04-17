package biz;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.SysDepartment;
import entity.SysEmployee;


public interface DeptBIZ {

	Set<SysDepartment> addFindall(String power); 
	Set<SysDepartment> updateFindall(String power); 
     SysDepartment findDeptByName(String deptname);
	List<SysDepartment> searchFindall(SysEmployee employee); 

}
