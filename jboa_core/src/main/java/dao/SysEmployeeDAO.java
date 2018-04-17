package dao;

import java.util.List;

import entity.SysEmployee;
public interface SysEmployeeDAO  {
	public SysEmployee login(String username,String password);
	SysEmployee findUserByUsername(String username);
	SysEmployee findUserByUserSn(int sn);

	public List<SysEmployee> findall();
}
