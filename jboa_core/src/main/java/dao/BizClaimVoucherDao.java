package dao;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;


import entity.BizClaimVoucher;
import entity.SysDepartment;
import entity.SysEmployee;



public interface BizClaimVoucherDao {

	BizClaimVoucher findByID(String id);


/*	int getClaimVoucherCount(SysEmployee sysEmploye,Boolean isLookThrough);
*/
	int getClaimVoucherCount(String hql,Boolean isLookThrough);

/*	 List<BizClaimVoucher> getClaimVouchers(int page,int rows,SysEmployee sysEmploye,Boolean isLookThrough);
*/
	 List<BizClaimVoucher> getClaimVouchers(int page,int rows,String hql,Boolean isLookThrough);

	 
	 
	 BizClaimVoucher SaveOrUpdateClaimVouchers(BizClaimVoucher BizClaimVoucher);

	int deleteClaimVouchers(int id);
	
	int pass(String pass,long id,String comm,int sn,int cn);
	
	int notgo(String pass,long id,String comm,int sn,int cn);

	Set<SysDepartment> getnextDealSn(int cn );
	List<BizClaimVoucher> getClaimVouchersByTask(List<Integer> bizclaimvoucherids,int page,int rows);
	int getClaimVouchersCountByTask(List<Integer> bizclaimvoucherids,int page,int rows);

}
