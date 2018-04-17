package dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import entity.BizClaimVouyearStatistics;


public interface ClaimVouyearDAO {

	int getClaimVouyearCount();

	int getClaimVouyearCount(String sql);

	 List<BizClaimVouyearStatistics> getClaimVouyears(int page,int rows);

	 List<BizClaimVouyearStatistics> getClaimVouyears(String sql,int page,int rows);
	
	 List<BizClaimVouyearStatistics> getAllClaimVouyears();

}
