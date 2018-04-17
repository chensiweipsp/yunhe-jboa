package controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.poifs.storage.ListManagedBlock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.SysEmployee;

import util.JsonData;

import biz.EmpBIZ;

@Controller
@RequestMapping("power.do")
public class PowerController {

	@Resource
	EmpBIZ empBIZ;
	
	@RequestMapping(params="method=getEmpRole",method=RequestMethod.POST)
	public @ResponseBody JsonData getEmpRole()
	{
		
		List<SysEmployee> employees  =empBIZ.findall();
		
		JsonData jsonData = new JsonData();
		
		jsonData.setRows(employees);
		jsonData.setTotal(employees.size());
		
		return jsonData;
		
	}
	
	
}
