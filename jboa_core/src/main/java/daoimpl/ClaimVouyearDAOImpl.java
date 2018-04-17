package daoimpl;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.ClaimVouyearDAO;
import entity.BizClaimVouyearStatistics;

@Transactional
@Repository("ClaimVouyearDAO")
public class ClaimVouyearDAOImpl extends HibernateDaoSupport implements ClaimVouyearDAO  {

	@Override
	public int getClaimVouyearCount() {
		int count = 0;
		try {
			count = getHibernateTemplate().getSessionFactory().getCurrentSession().
					createQuery("from BizClaimVouyearStatistics").list().size();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getClaimVouyearCount(String sql) {
		int count = 0;
		try {
			count = getHibernateTemplate().getSessionFactory().getCurrentSession().
					createQuery("from BizClaimVouyearStatistics as bv "+sql+"").list().size();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<BizClaimVouyearStatistics> getClaimVouyears(int page, int rows) {
		List<BizClaimVouyearStatistics> BizClaimVouyearStatisticss = new ArrayList<BizClaimVouyearStatistics>();
		try {
			BizClaimVouyearStatisticss= getHibernateTemplate().getSessionFactory().getCurrentSession().
					createQuery("from BizClaimVouyearStatistics ").setFirstResult((page-1)*rows).setMaxResults(rows).list();
		
			for (BizClaimVouyearStatistics bizClaimVouyearStatistics : BizClaimVouyearStatisticss) {
				System.out.println(bizClaimVouyearStatistics.getEmp().getDept().getName());
				bizClaimVouyearStatistics.setDept(bizClaimVouyearStatistics.getEmp());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BizClaimVouyearStatisticss;
	}
	@Override
	public List<BizClaimVouyearStatistics> getClaimVouyears(String sql,
			int page, int rows) {
		List<BizClaimVouyearStatistics> BizClaimVouyearStatisticss = new ArrayList<BizClaimVouyearStatistics>();
		try {
			BizClaimVouyearStatisticss=(ArrayList<BizClaimVouyearStatistics>) getHibernateTemplate().getSessionFactory().getCurrentSession().
					createQuery("from BizClaimVouyearStatistics as bv "+sql+"").setFirstResult((page-1)*rows).setMaxResults(rows).list();
			for (BizClaimVouyearStatistics bizClaimVouyearStatistics : BizClaimVouyearStatisticss) {
				System.out.println(bizClaimVouyearStatistics.getEmp().getDept().getName());
				bizClaimVouyearStatistics.setDept(bizClaimVouyearStatistics.getEmp());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BizClaimVouyearStatisticss;
	}

	@Override
	public List<BizClaimVouyearStatistics> getAllClaimVouyears() {
		List<BizClaimVouyearStatistics> BizClaimVouyearStatisticss = new ArrayList<BizClaimVouyearStatistics>();
		try {
			BizClaimVouyearStatisticss=(ArrayList<BizClaimVouyearStatistics>) getHibernateTemplate().getSessionFactory().getCurrentSession().
					createQuery("from BizClaimVouyearStatistics" ).list();
			for (BizClaimVouyearStatistics bizClaimVouyearStatistics : BizClaimVouyearStatisticss) {
				System.out.println(bizClaimVouyearStatistics.getEmp().getDept().getName());
				bizClaimVouyearStatistics.setDept(bizClaimVouyearStatistics.getEmp());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BizClaimVouyearStatisticss;
	}

}
