package biz;

import java.util.List;
import java.util.Set;

import entity.BizClaimVoucher;
import entity.SysDepartment;
import entity.SysEmployee;


public interface BizClaimVoucherBiz {

	BizClaimVoucher findByID(String id);

/*	int getClaimVoucherCount(SysEmployee sEmploye,String roles,String permissions,Boolean isLookThrough);
*/
	int getClaimVoucherCount(String searchCreateSn,String searchnextDealSn,String hql,SysEmployee sEmploye,Boolean isLookThrough);

	 List<BizClaimVoucher> getClaimVouchers(String searchCreateSn,String searchnextDealSn,String hql,int page,int rows,SysEmployee sEmployee,Boolean isLookThrough);

/*	 List<BizClaimVoucher> getClaimVouchers(String sql,int page,int rows,SysEmployee sEmployee,String roles,String permissions,Boolean isLookThrough);
*/
	int SaveOrUpdateClaimVouchers(BizClaimVoucher BizClaimVoucher);

	int deleteClaimVouchers(int id);
	
	int ispass(String pass,long id,String comm,int sn,int cn);
	
	SysDepartment getnextDealSn(SysDepartment dept,String role  );
	
}
