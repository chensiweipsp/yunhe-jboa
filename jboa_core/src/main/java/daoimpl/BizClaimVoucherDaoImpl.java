package daoimpl;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import dao.BizClaimVoucherDao;

import entity.BizCheckResult;
import entity.BizClaimVoucher;
import entity.BizClaimVouyearStatistics;
import entity.SysDepartment;
import entity.SysEmployee;
import entity.SysRole;
import util.Page;
@Transactional
@Repository("bizClaimVoucherDao")
public class BizClaimVoucherDaoImpl extends HibernateDaoSupport implements BizClaimVoucherDao
{




	@SuppressWarnings("unchecked")
	public List<BizClaimVoucher> getClaimVouchers(int page, int rows,SysEmployee sysEmployee,String hql,String searchHQL,Boolean isLookThrough) {
		List<BizClaimVoucher> bizClaimVouchers = new ArrayList<BizClaimVoucher>();
		try {
			if(null!=sysEmployee)
			{
				if(isLookThrough)
				{
					bizClaimVouchers= getHibernateTemplate().getSessionFactory().getCurrentSession().
							createQuery("from BizClaimVoucher as bv where bv.nextDealSn.name=?  ").setString( 0, sysEmployee.getName()).setFirstResult((page-1)*rows).setMaxResults(rows).list();
				}
				//部门经理查看
				else 
				{

					bizClaimVouchers= getHibernateTemplate().getSessionFactory().getCurrentSession().
							createQuery("from BizClaimVoucher   ").setFirstResult((page-1)*rows).setMaxResults(rows).list();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bizClaimVouchers;
	}

	@Override
	public BizClaimVoucher SaveOrUpdateClaimVouchers(BizClaimVoucher BizClaimVoucher) {
		Session session=null;
		try {
			session= getHibernateTemplate().getSessionFactory().getCurrentSession();

			if(null!=BizClaimVoucher.getId())
			{

				BizClaimVoucher bizClaimVoucher2= (entity.BizClaimVoucher) session.load(BizClaimVoucher.class, BizClaimVoucher.getId());
				SysEmployee  cn=(SysEmployee) session.load (SysEmployee.class,BizClaimVoucher.getCreateSn().getSn() );
				SysEmployee  nn=(SysEmployee) session.load(SysEmployee.class,BizClaimVoucher.getNextDealSn().getSn() );

				bizClaimVoucher2.setCreateSn(cn);
				bizClaimVoucher2.setNextDealSn(nn);
				bizClaimVoucher2.setCreateTime(BizClaimVoucher.getCreateTime());
				bizClaimVoucher2.setEvent(BizClaimVoucher.getEvent());
				bizClaimVoucher2.setTotalAccount(BizClaimVoucher.getTotalAccount());
				bizClaimVoucher2.setStatus(BizClaimVoucher.getStatus());

				String rolesn = null;

				for(SysRole role2:bizClaimVoucher2.getNextDealSn().getRoles())
				{
					rolesn=role2.getRolename();
				}

				String rolecn = null;

				for(SysRole role2:bizClaimVoucher2.getCreateSn().getRoles())
				{
					rolecn=role2.getRolename();
				}


				if(rolesn.equals("cashier"))
				{
					bizClaimVoucher2.setSchedule(2);
				}else if (rolesn.equals("cashier")&&rolecn.equals("generalmanager")) {
					bizClaimVoucher2.setSchedule(2);
				}else if (rolesn.equals("generalmanager"))
				{
					bizClaimVoucher2.setSchedule(3);
				}else {
					bizClaimVoucher2.setSchedule(1);
				}


			}
			else {

				int createSn =BizClaimVoucher.getCreateSn().getSn();
				int nextdealsn =BizClaimVoucher.getNextDealSn().getSn();

				SysEmployee create=	(SysEmployee) session.get(SysEmployee.class, createSn);
				SysEmployee nextdeal=	(SysEmployee) session.get(SysEmployee.class, nextdealsn);;


				String rolesn = null;

				for(SysRole role2:nextdeal.getRoles())
				{
					rolesn=role2.getRolename();
				}

				String rolecn = null;

				for(SysRole role2:create.getRoles())
				{
					rolecn=role2.getRolename();
				}

				if(rolesn.equals("cashier"))
				{
					BizClaimVoucher.setSchedule(2);
				}else if (rolesn.equals("cashier")&&rolecn.equals("generalmanager")) {
					BizClaimVoucher.setSchedule(2);
				}else if (rolesn.equals("generalmanager"))
				{
					BizClaimVoucher.setSchedule(3);

				}else {
					BizClaimVoucher.setSchedule(1);
				}
				BizClaimVoucher.setCreateSn(create);
				BizClaimVoucher.setNextDealSn(nextdeal);
				BizClaimVoucher.setStatus("新创建");
				session.save(BizClaimVoucher);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// spring aop  异常捕获原理：被拦截的方法需显式抛出异常，并不能经任何处理，这样aop代理才能捕获到方法的异常，才能进行回滚，默认情况下aop只捕获runtimeexception的异常，但可以通过  
			//在service的方法中不使用try catch 或者在catch中最后加上throw new runtimeexcetpion（），这样程序异常时才能被aop捕获进而回滚
			throw new RuntimeException();

		}
		return BizClaimVoucher;
	}

	public int deleteClaimVouchers(int id) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().delete(new BizClaimVoucher(id));
		return 0;
	}


	public int pass(HttpServletRequest request,String pass, long id, String comm,int sn,int cn) {
		Session session=null;

		try {
			session=	getHibernateTemplate().getSessionFactory().getCurrentSession();

			//根据报销单ID 获取报销单
			BizClaimVoucher bizClaimVoucher = (BizClaimVoucher) session.get(BizClaimVoucher.class, id);


			if(SecurityUtils.getSubject().hasRole("manager"))
			{
				//修改报销单的状态和待处理人

				bizClaimVoucher.setNextDealSn(new SysEmployee(sn));
				bizClaimVoucher.setSchedule(2);


				//添加一条报销单审核记录
				BizCheckResult bizCheckResult = new BizCheckResult();
				bizCheckResult.setId(id);

				bizCheckResult.setNextEmp(new SysEmployee(sn));
				bizCheckResult.setCreatEmp(new SysEmployee(cn));

				bizCheckResult.setCheckTime(new Timestamp(System.currentTimeMillis()));
				bizCheckResult.setClaimId(id);
				bizCheckResult.setComm(comm);
				bizCheckResult.setResult("通过");

				session.save(bizCheckResult);




			}
			else if(SecurityUtils.getSubject().hasRole("cashier"))
			{
				//修改报销单的状态和待处理人
				bizClaimVoucher.setNextDealSn(new SysEmployee(sn));
				bizClaimVoucher.setSchedule(3);

				//添加一条报销单审核记录
				BizCheckResult bizCheckResult = new BizCheckResult();
				bizCheckResult.setId(id);

				bizCheckResult.setNextEmp(new SysEmployee(sn));
				bizCheckResult.setCreatEmp(new SysEmployee(cn));

				bizCheckResult.setCheckTime(new Timestamp(System.currentTimeMillis()));
				bizCheckResult.setClaimId(id);
				bizCheckResult.setComm(comm);
				bizCheckResult.setResult("通过");

				session.save(bizCheckResult);

			}
			else if(SecurityUtils.getSubject().hasRole("generalmanager"))
			{
				//修改报销单的状态
				bizClaimVoucher.setNextDealSn(new SysEmployee(cn));
				bizClaimVoucher.setSchedule(4);
				bizClaimVoucher.setStatus("已通过审核");
				//添加一条报销单审核记录
				BizCheckResult bizCheckResult = new BizCheckResult();
				bizCheckResult.setId(id);
				//如果审核通过 该报销单的待审核人为填报人本人
				bizCheckResult.setNextEmp(new SysEmployee(cn));
				bizCheckResult.setCreatEmp(new SysEmployee(cn));

				bizCheckResult.setCheckTime(new Timestamp(System.currentTimeMillis()));
				bizCheckResult.setClaimId(id);
				bizCheckResult.setComm(comm);
				bizCheckResult.setResult("通过");

				session.save(bizCheckResult);

				//年度报表累加
				BizClaimVouyearStatistics bizclaimvouyearstatistics=  (BizClaimVouyearStatistics) session.createQuery("from BizClaimVouyearStatistics as bys where bys.emp.sn=? ").setInteger(0, sn).uniqueResult();

				if(null==bizclaimvouyearstatistics)
				{
					bizclaimvouyearstatistics=new BizClaimVouyearStatistics();
					bizclaimvouyearstatistics.setEmp(bizClaimVoucher.getCreateSn());
					bizclaimvouyearstatistics.setModifyTime(new Timestamp(System.currentTimeMillis()));
					bizclaimvouyearstatistics.setYear((short)new Date().getYear());
					bizclaimvouyearstatistics.setTotalCount(bizClaimVoucher.getTotalAccount());
					session.save(bizclaimvouyearstatistics);
				}
				else {
					bizclaimvouyearstatistics.setTotalCount(bizclaimvouyearstatistics.getTotalCount()+bizClaimVoucher.getTotalAccount());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// spring aop  异常捕获原理：被拦截的方法需显式抛出异常，并不能经任何处理，这样aop代理才能捕获到方法的异常，才能进行回滚，默认情况下aop只捕获runtimeexception的异常，但可以通过  
			//在service的方法中不使用try catch 或者在catch中最后加上throw new runtimeexcetpion（），这样程序异常时才能被aop捕获进而回滚
			throw new RuntimeException();
		}
		return 0;
	}

	@Override
	public int notgo(HttpServletRequest request,String pass, long id, String comm,int sn,int cn) {
		Session session=null;

		try {
			session=	getHibernateTemplate().getSessionFactory().getCurrentSession();

			//根据报销单ID 获取报销单
			BizClaimVoucher bizClaimVoucher=	(BizClaimVoucher) session.get(BizClaimVoucher.class, id);

			//修改报销单的状态
			bizClaimVoucher.setNextDealSn(new SysEmployee(cn));
			bizClaimVoucher.setStatus("审核不通过");
			bizClaimVoucher.setSchedule(5);

			//添加一条报销单审核记录
			BizCheckResult bizCheckResult = new BizCheckResult();
			bizCheckResult.setNextEmp(new SysEmployee(sn));
			bizCheckResult.setCreatEmp(new SysEmployee(cn));
			bizCheckResult.setCheckTime(new Timestamp(System.currentTimeMillis()));
			bizCheckResult.setClaimId(id);
			bizCheckResult.setComm(comm);
			bizCheckResult.setResult("审核不通过");
			session.save(bizCheckResult);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return 0;
	}

	@Override
	public BizClaimVoucher findByID(String id) {
		BizClaimVoucher bizClaimVoucher = null;
		try {
			bizClaimVoucher = (BizClaimVoucher) getHibernateTemplate().getSessionFactory().getCurrentSession().get(BizClaimVoucher.class, Long.valueOf(id));
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bizClaimVoucher;
	}


	@Override
	public Set<SysDepartment> getnextDealSn(int cn) {
		List<SysDepartment> temp=null;
		List<SysDepartment> depts=new ArrayList<SysDepartment>();
		Set<SysDepartment> departments=null;
		Session session=null;
		try {
			//二级缓存 不可以用 线程绑定的方式获取SESSION (getHibernateTemplate().getSessionFactory().getCurrentSession())
			//可以使用getsession 或者new Configuration().configure().buildSessionFactory().openSession()
			session=new Configuration().configure().buildSessionFactory().openSession();
			SysEmployee sysEmployee3 = (SysEmployee) session.get(SysEmployee.class,cn);
			//获取权限
			String power =sysEmployee3.getPosition().getNameEn();

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
	public int getClaimVoucherCount(String hql, Boolean isLookThrough) {
		int count=0;
		try {
			count = ((Long) getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return count;
	}

	@Override
	public List<BizClaimVoucher> getClaimVouchers(int page, int rows,
			String hql, Boolean isLookThrough) {
		List<BizClaimVoucher> bizClaimVouchers=new ArrayList<>();
		try {
			bizClaimVouchers = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setFirstResult((page-1)*rows).setMaxResults(rows).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bizClaimVouchers;
	}

	@Override
	public List<BizClaimVoucher> getClaimVouchersByTask(List<Integer> bizclaimvoucherids, int page, int rows) {
		// TODO Auto-generated method stub

		List<BizClaimVoucher> bizClaimVouchers=new ArrayList<>();
		Page page1 = new Page();

		try {

			for (int id : bizclaimvoucherids) {

				BizClaimVoucher bizClaimVoucher=(BizClaimVoucher) getHibernateTemplate().getSessionFactory().getCurrentSession().get(BizClaimVoucher.class, (long)id);
				if(null!=bizClaimVoucher)
				bizClaimVouchers.add(bizClaimVoucher);

			}
			//刚开始的页面为第一页
			if (page1.getCurrentPage() == null){
				page1.setCurrentPage(page);
			} else {
				page1.setCurrentPage(page1.getCurrentPage());
			}
			//设置每页数据为十条
			page1.setPageSize(10);
			//每页的开始数
			page1.setStar((page1.getCurrentPage() - 1) * page1.getPageSize());
			//list的大小
			int count = bizClaimVouchers.size();
			//设置总页数
			page1.setTotalPage(count % rows == 0 ? count / rows : count / rows + 1);
			//对list进行截取
			page1.setDataList(bizClaimVouchers.subList(page1.getStar(),count-page1.getStar()>page1.getPageSize()?page1.getStar()+page1.getPageSize():count));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (List<BizClaimVoucher>) page1.getDataList();
	}

	@Override
	public int getClaimVouchersCountByTask(List<Integer> bizclaimvoucherids, int page, int rows) {
		// TODO Auto-generated method stub

		List<BizClaimVoucher> bizClaimVouchers=new ArrayList<>();

		try {

			for (int id : bizclaimvoucherids) {

				BizClaimVoucher bizClaimVoucher=(BizClaimVoucher) getHibernateTemplate().getSessionFactory().getCurrentSession().get(BizClaimVoucher.class, (long)id);
				if(null!=bizClaimVoucher)
					bizClaimVouchers.add(bizClaimVoucher);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bizClaimVouchers.size();
	}

	@Override
	public BizClaimVoucher SaveOrUpdateClaimVouchers2(BizClaimVoucher BizClaimVoucher,String taskid) {
		Session session=null;
		try {
			session= getHibernateTemplate().getSessionFactory().getCurrentSession();

			if(null!=BizClaimVoucher.getId())
			{

				BizClaimVoucher bizClaimVoucher2= (entity.BizClaimVoucher) session.load(BizClaimVoucher.class, BizClaimVoucher.getId());
				SysEmployee  cn=(SysEmployee) session.load (SysEmployee.class,BizClaimVoucher.getCreateSn().getSn() );
				SysEmployee  nn=(SysEmployee) session.load(SysEmployee.class,BizClaimVoucher.getNextDealSn().getSn() );

				bizClaimVoucher2.setCreateSn(cn);
				bizClaimVoucher2.setNextDealSn(nn);
				bizClaimVoucher2.setCreateTime(BizClaimVoucher.getCreateTime());
				bizClaimVoucher2.setEvent(BizClaimVoucher.getEvent());
				bizClaimVoucher2.setTotalAccount(BizClaimVoucher.getTotalAccount());
				bizClaimVoucher2.setStatus(BizClaimVoucher.getStatus());
				bizClaimVoucher2.setTaskid(taskid);


			}
			else {

				int createSn =BizClaimVoucher.getCreateSn().getSn();
				int nextdealsn =BizClaimVoucher.getNextDealSn().getSn();

				SysEmployee create=	(SysEmployee) session.get(SysEmployee.class, createSn);
				SysEmployee nextdeal=	(SysEmployee) session.get(SysEmployee.class, nextdealsn);;


				BizClaimVoucher.setCreateSn(create);
				BizClaimVoucher.setNextDealSn(nextdeal);
				session.save(BizClaimVoucher);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// spring aop  异常捕获原理：被拦截的方法需显式抛出异常，并不能经任何处理，这样aop代理才能捕获到方法的异常，才能进行回滚，默认情况下aop只捕获runtimeexception的异常，但可以通过  
			//在service的方法中不使用try catch 或者在catch中最后加上throw new runtimeexcetpion（），这样程序异常时才能被aop捕获进而回滚
			throw new RuntimeException();

		}
		return BizClaimVoucher;
	}





}
