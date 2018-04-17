package bizimpl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import dao.SysEmployeeDAO;
import entity.SysEmployee;
import entity.SysRole;
import biz.EmpBIZ;
@Service("empBIZ")
public class EmpBIZImpl implements EmpBIZ{

	@Resource
	SysEmployeeDAO empDAO;

	@Override
	public SysEmployee Login(String username, String password) {
		// TODO Auto-generated method stub
		return empDAO.login(username, password);
	}

	@Override
	public SysEmployee findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return empDAO.findUserByUsername(username);
	}

	@Override
	public SysEmployee findUserByUserSn(int sn) {
		// TODO Auto-generated method stub
		return empDAO.findUserByUserSn(sn);
	}

	@Override
	public List<SysEmployee> findall() {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringBuilder2 = new StringBuilder();
		List<SysEmployee> employees = empDAO.findall();
		for (SysEmployee sysEmployee : employees) {

			for (SysRole sysRole : sysEmployee.getRoles()) {

				stringBuilder.append(","+sysRole.getRolename()+"");
				stringBuilder2.append(","+sysRole.getDescribe()+"");


			}
			String [] temp1=stringBuilder.toString().split(",");
			String [] temp2=stringBuilder2.toString().split(",");
			StringBuilder temp3 = new StringBuilder();;
			StringBuilder temp4 = new StringBuilder();;

			for (int i=0;i<=temp1.length-1; i++) {
				temp3.append(temp1[i]);

			}
			for (int i=0;i<=temp2.length-1; i++) {
				temp4.append(temp2[i]);

			}
			sysEmployee.setRolesnames(temp3.toString());

			sysEmployee.setRolesdescribes(temp4.toString());
			stringBuilder = new StringBuilder();
			stringBuilder2 = new StringBuilder();
		}



		return employees;
	}

}
