package biz;

import java.util.List;

import entity.SysEmployee;

public interface EmpBIZ {

	SysEmployee Login(String username, String password);
	SysEmployee findUserByUsername(String username);
	SysEmployee findUserByUserSn(int sn);

	List<SysEmployee> findall();
}
