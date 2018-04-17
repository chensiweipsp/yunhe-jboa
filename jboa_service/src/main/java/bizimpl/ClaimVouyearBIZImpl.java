package bizimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import biz.ClaimVouyearBIZ;
import dao.ClaimVouyearDAO;
import entity.BizClaimVoucher;
import entity.BizClaimVouyearStatistics;
@Service("ClaimVouyearBIZ")
public class ClaimVouyearBIZImpl implements ClaimVouyearBIZ {
	@Autowired
	ClaimVouyearDAO ClaimVouyearDAO;
	@Override
	public int getClaimVouyearCount() {
		// TODO Auto-generated method stub
		return ClaimVouyearDAO.getClaimVouyearCount();
	}
	@Override
	public int getClaimVouyearCount(String sql) {
		// TODO Auto-generated method stub
		return ClaimVouyearDAO.getClaimVouyearCount(sql);
	}
	@Override
	public List<BizClaimVouyearStatistics> getClaimVouyears(int page, int rows) {
		// TODO Auto-generated method stub
		return ClaimVouyearDAO.getClaimVouyears(page, rows);
	}
	@Override
	public List<BizClaimVouyearStatistics> getClaimVouyears(String sql,
			int page, int rows) {
		// TODO Auto-generated method stub
		return ClaimVouyearDAO.getClaimVouyears(sql, page, rows);
	}
	@Override
	public List<BizClaimVouyearStatistics> getAllClaimVouyears() {
		// TODO Auto-generated method stub
		return ClaimVouyearDAO.getAllClaimVouyears();
	}
}
