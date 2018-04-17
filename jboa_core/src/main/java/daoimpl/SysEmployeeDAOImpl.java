package daoimpl;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import dao.SysEmployeeDAO;
import entity.SysEmployee;
@Transactional
@Repository("empDAO")
public class SysEmployeeDAOImpl extends HibernateDaoSupport implements SysEmployeeDAO {
	@Override
	public SysEmployee login(String username, String password) {
		SysEmployee sysEmployee =null;
		try {
			sysEmployee= (SysEmployee) getHibernateTemplate().getSessionFactory().getCurrentSession().
					createQuery("from SysEmployee where name=? and password=?")
					.setString(0, username).setString(1, password).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysEmployee;
	}

	@Override
	public SysEmployee findUserByUsername(String username) {
		SysEmployee sysEmployee =null;
		try {
			sysEmployee= (SysEmployee)getSession().
					createQuery("from SysEmployee where name=? ")
					.setString(0, username).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysEmployee;
	}
	
	
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){  
		 super.setSessionFactory(sessionFactory);  
	}

	@Override
	public SysEmployee findUserByUserSn(int sn) {
		SysEmployee sysEmployee =null;
		try {
			sysEmployee= (SysEmployee)getSession().
					createQuery("from SysEmployee where sn=? ")
					.setInteger(0, sn).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysEmployee;
	}

	@Override
	public List<SysEmployee> findall() {
		
		List<SysEmployee> employees = new ArrayList<>();
		try {
			employees=	getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from SysEmployee").list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
	}  

}