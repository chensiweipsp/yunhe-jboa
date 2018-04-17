package biz;

import java.util.List;

import entity.BizClaimVouyearStatistics;



public interface ClaimVouyearBIZ {

	int getClaimVouyearCount();

	int getClaimVouyearCount(String sql);

	 List<BizClaimVouyearStatistics> getClaimVouyears(int page,int rows);

	 List<BizClaimVouyearStatistics> getClaimVouyears(String sql,int page,int rows);
	 List<BizClaimVouyearStatistics> getAllClaimVouyears();

}
