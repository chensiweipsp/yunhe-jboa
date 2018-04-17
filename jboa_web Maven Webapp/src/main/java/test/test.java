package test;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import entity.BizClaimVoucher;

public class test {
	@Test
	public void test() {
		List<BizClaimVoucher> bizClaimVouchers = new ArrayList<BizClaimVoucher>();
        Configuration configuration=null;
        SessionFactory sessionFactory = null;
        Session session =null;
        
		try {
			configuration = new Configuration().configure();
			sessionFactory =configuration.buildSessionFactory();
			session =sessionFactory.openSession();
			bizClaimVouchers= session.createQuery("from BizClaimVoucher as bv where bv.createSn.name=? ").setString(0, "siwei").list();
		
			System.err.println(bizClaimVouchers.size());
			for (BizClaimVoucher bizClaimVoucher : bizClaimVouchers) {
				System.out.println(bizClaimVoucher.getNextDealSn().getName());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}