package daoimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.DeptDAO;
import entity.SysDepartment;
import entity.SysEmployee;

@Repository("deptDAO")
public class DeptDAOImpl extends HibernateDaoSupport implements DeptDAO  {




	public static void main(String[] args) {
		List<SysDepartment> depts=null;

		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		depts=	sessionFactory.
				openSession().createQuery("from SysDepartment").setCacheable(true).list();
		depts=	sessionFactory.
				openSession().createQuery("from SysDepartment").setCacheable(true).list();

	}



	@Override
	public Set<SysDepartment> addFindall(String power) {
		List<SysDepartment> temp=null;

		List<SysDepartment> depts=new ArrayList<SysDepartment>();
		Set<SysDepartment> departments=null;
		Session session=null;
		try {

			//二级缓存 不可以用 线程绑定的方式获取SESSION (getHibernateTemplate().getSessionFactory().getCurrentSession())
			//可以使用getsession 或者new Configuration().configure().buildSessionFactory().openSession()


			session=new Configuration().configure().buildSessionFactory().openSession();

			temp=	session.createQuery("from SysDepartment ").setCacheable(true).list();

			for (SysDepartment sysDepartment : temp) {

				Iterator<SysEmployee> empsIterator= sysDepartment.getEmps().iterator();

				while (empsIterator.hasNext()) {

					SysEmployee sysEmployee = (SysEmployee) empsIterator.next();
					if(power.equals("staff"))
					{
						if(sysEmployee.getPosition().getNameEn().equals("manager"))
						{
							Iterator<SysEmployee> iterator=	sysDepartment.getEmps().iterator();
							while (iterator.hasNext()) {
								SysEmployee sysEmployee2 = (SysEmployee) iterator.next();
								if(sysEmployee2.getPosition().getNameEn().equals(("staff")))
								{
									iterator.remove();
								}
							}

							depts.add(sysDepartment);

							//break 跳出循环   可以点击到break 上   相对应的方法体 就会 变亮
							break;
						}
					} else if (power.equals("manager"))
					{
						if(sysEmployee.getPosition().getNameEn().equals("cashier"))
						{
							depts.add(sysDepartment);
						}
					}else if (power.equals("cashier"))
					{
						if(sysEmployee.getPosition().getNameEn().equals("generalmanager"))
						{
							depts.add(sysDepartment);
						}
					}else 
					{
						if(sysEmployee.getPosition().getNameEn().equals("cashier"))
						{
							depts.add(sysDepartment);
						}
					}

					System.err.println(sysEmployee.getName());
				}
			}

			//new hashset  去掉depts LIST中 重复的对象
			departments= new HashSet<SysDepartment>(depts);

			for (SysDepartment sysDepartment : departments) {
				Iterator<SysEmployee> iterator=	sysDepartment.getEmps().iterator();
				while (iterator.hasNext()) {
					SysEmployee sysEmployee = (SysEmployee) iterator.next();
					System.err.println(sysEmployee.getName());
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally{

			session.close();
		}

		return departments;
	}



	@Override
	public Set<SysDepartment> updateFindall(String power) {
		List<SysDepartment> temp=null;

		List<SysDepartment> depts=new ArrayList<SysDepartment>();
		Set<SysDepartment> departments=null;
		Session session=null;
		try {

			//二级缓存 不可以用 线程绑定的方式获取SESSION (getHibernateTemplate().getSessionFactory().getCurrentSession())
			//可以使用getsession 或者new Configuration().configure().buildSessionFactory().openSession()

			session=new Configuration().configure().buildSessionFactory().openSession();

			temp=	session.createQuery("from SysDepartment ").setCacheable(true).list();

			for (SysDepartment sysDepartment : temp) {

				Iterator<SysEmployee> empsIterator= sysDepartment.getEmps().iterator();

				while (empsIterator.hasNext()) {

					SysEmployee sysEmployee = (SysEmployee) empsIterator.next();
					if(power.equals("staff"))
					{
						if(sysEmployee.getPosition().getNameEn().equals("manager"))
						{
							Iterator<SysEmployee> iterator=	sysDepartment.getEmps().iterator();
							while (iterator.hasNext()) {
								SysEmployee sysEmployee2 = (SysEmployee) iterator.next();
								if(sysEmployee2.getPosition().getNameEn().equals(("staff")))
								{
									iterator.remove();
								}
							}

							depts.add(sysDepartment);

							//break 跳出循环   可以点击到break 上   相对应的方法体 就会 变亮
							break;
						}
					} 
					else if (power.equals("manager"))
					{
						if(sysEmployee.getPosition().getNameEn().equals("manager"))
						{
							Iterator<SysEmployee> iterator=	sysDepartment.getEmps().iterator();
							while (iterator.hasNext()) {
								SysEmployee sysEmployee2 = (SysEmployee) iterator.next();
								if(sysEmployee2.getPosition().getNameEn().equals(("staff")))
								{
									iterator.remove();
								}
							}

							depts.add(sysDepartment);

							//break 跳出循环   可以点击到break 上   相对应的方法体 就会 变亮
							break;
						}

						/*if(sysEmployee.getPosition().getNameEn().equals("cashier"))
						{
							depts.add(sysDepartment);
						}*/
					}
					else if (power.equals("cashier"))
					{

						if(sysEmployee.getPosition().getNameEn().equals("cashier"))
						{
							depts.add(sysDepartment);
						}

					}
					else 
					{
						if(sysEmployee.getPosition().getNameEn().equals("generalmanager"))
						{
							depts.add(sysDepartment);
						}
					}

					System.err.println(sysEmployee.getName());
				}
			}

			//new hashset  去掉depts LIST中 重复的对象
			departments= new HashSet<SysDepartment>(depts);

			for (SysDepartment sysDepartment : departments) {
				Iterator<SysEmployee> iterator=	sysDepartment.getEmps().iterator();
				while (iterator.hasNext()) {
					SysEmployee sysEmployee = (SysEmployee) iterator.next();
					System.err.println(sysEmployee.getName());
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally{

			session.close();
		}

		return departments;
	}



	@Override
	public List<SysDepartment> searchFindall(String hql) {
		List<SysDepartment> temp=null;

		List<SysDepartment> depts=new ArrayList<SysDepartment>();
		Set<SysDepartment> departments=null;
		Session session=null;
		try {
			session=new Configuration().configure().buildSessionFactory().openSession();
			temp=	session.createQuery(hql).setCacheable(true).list();
			//二级缓存 不可以用 线程绑定的方式获取SESSION (getHibernateTemplate().getSessionFactory().getCurrentSession())
			//可以使用getsession 或者new Configuration().configure().buildSessionFactory().openSession()

			/*	session=new Configuration().configure().buildSessionFactory().openSession();

			temp=	session.createQuery("from SysDepartment ").setCacheable(true).list();

			for (SysDepartment sysDepartment : temp) {

				Iterator<SysEmployee> empsIterator= sysDepartment.getEmps().iterator();

				while (empsIterator.hasNext()) {

					SysEmployee sysEmployee = (SysEmployee) empsIterator.next();
				 if (power.equals("manager"))
					{
						if(sysEmployee.getPosition().getNameEn().equals("manager"))
						{
						 Iterator<SysEmployee> iterator=	sysDepartment.getEmps().iterator();
							while (iterator.hasNext()) {
								SysEmployee sysEmployee2 = (SysEmployee) iterator.next();
								if(sysEmployee2.getPosition().getNameEn().equals(("manager"))||sysEmployee2.getPosition().getNameEn().equals(("generalmanager"))||sysEmployee2.getPosition().getNameEn().equals(("cashier")))
								{
									iterator.remove();
								}
							}

						 depts.add(sysDepartment);

						 //break 跳出循环   可以点击到break 上   相对应的方法体 就会 变亮
						 break;
						}

						if(sysEmployee.getPosition().getNameEn().equals("cashier"))
						{
							depts.add(sysDepartment);
						}
					}
					else {
						depts=temp;
					}

					System.err.println(sysEmployee.getName());
				}
			}

			//new hashset  去掉depts LIST中 重复的对象
			departments= new HashSet<SysDepartment>(depts);

			for (SysDepartment sysDepartment : departments) {
			 Iterator<SysEmployee> iterator=	sysDepartment.getEmps().iterator();
		          while (iterator.hasNext()) {
					SysEmployee sysEmployee = (SysEmployee) iterator.next();
					System.err.println(sysEmployee.getName());
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally{

			session.close();
		}
			 */
			/*			return departments;
			 */		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return temp;

	}



	@Override
	public SysDepartment findDeptByName(String deptname) {
		
		SysDepartment sysDepartment=null;
		Session session=null;
		try {
			session=new Configuration().configure().buildSessionFactory().openSession();
			sysDepartment=	(SysDepartment) session.createQuery("from SysDepartment where name = '"+deptname+"'").uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sysDepartment;
	}



	@Override
	public SysDepartment findDeptByCashier() {
		SysDepartment sysDepartment=null;
		Session session=null;
		try {
			session=new Configuration().configure().buildSessionFactory().openSession();
			sysDepartment=	(SysDepartment) session.createQuery("from SysDepartment where name = '财务部'").uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sysDepartment;
	}



	@Override
	public SysDepartment findDeptByGeneralmanager() {
		SysDepartment sysDepartment=null;
		Session session=null;
		try {
			session=new Configuration().configure().buildSessionFactory().openSession();
			sysDepartment=	(SysDepartment) session.createQuery("from SysDepartment where name = '总裁办公室'").uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sysDepartment;
	}
	

}
