package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.BizClaimVouyearStatistics;
import biz.ClaimVouyearBIZ;
import util.JsonData;

@Controller
@RequestMapping("ClaimVouyear.do")
public class ClaimVouyearController {

	@Autowired
	ClaimVouyearBIZ ClaimVouyearBIZ;
	
	@RequestMapping(params="method=ClaimVouyearShow")
	public @ResponseBody JsonData execute(HttpServletRequest request ,HttpServletResponse response)  {
		String previous = request.getParameter("previous");
		String createSn=request.getParameter("createSn");
		String nextDeal=request.getParameter("nextDeal");
		int page=Integer.parseInt( request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		if ("true".equals(previous)) {
			page = page - 1;
		} 
		JsonData jsonData = new JsonData();
		if (createSn != null && createSn.length() > 0) {
			jsonData.setTotal(ClaimVouyearBIZ.getClaimVouyearCount("where bv.createSn.sn='"+createSn+"'"));
			jsonData.setRows(ClaimVouyearBIZ.getClaimVouyears("where bv.createSn.sn='"+createSn+"'", page, rows));
		}
		else if
		(nextDeal != null && nextDeal.length() > 0) {
			jsonData.setTotal(ClaimVouyearBIZ.getClaimVouyearCount("where bv.nextDealSn.sn='"+nextDeal+"'"));
			jsonData.setRows(ClaimVouyearBIZ.getClaimVouyears("where bv.nextDealSn.sn='"+nextDeal+"'", page, rows));
		}
		else
		{
			jsonData.setTotal(ClaimVouyearBIZ.getClaimVouyearCount());
			jsonData.setRows(ClaimVouyearBIZ.getClaimVouyears(page, rows));
		}
		return jsonData;
		
		
	}
	
	@RequestMapping(params="method=AllClaimVouyear")
	public @ResponseBody JsonData execute02(HttpServletRequest request ,HttpServletResponse response)  {
		JsonData jsonData = new JsonData();
		List<BizClaimVouyearStatistics> bizClaimVouyearStatistics=ClaimVouyearBIZ.getAllClaimVouyears();
		jsonData.setTotal(bizClaimVouyearStatistics.size());
		jsonData.setRows(bizClaimVouyearStatistics);
		
		return jsonData;
	}
	
}
