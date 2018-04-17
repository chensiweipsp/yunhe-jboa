package dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.SysDepartment;

public interface DeptDAO {
	Set<SysDepartment> addFindall(String power); 
	Set<SysDepartment> updateFindall(String power); 
	List<SysDepartment> searchFindall(String power); 
    SysDepartment findDeptByName(String deptname);
    
    SysDepartment findDeptByCashier();
    SysDepartment findDeptByGeneralmanager();
}
