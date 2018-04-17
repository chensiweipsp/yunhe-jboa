package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonData;
import entity.Email;

@Controller
@RequestMapping("email.do")
public class EmailController {


	@RequestMapping(params="method=getemail")
	public @ResponseBody JsonData getemail()
	{


		List<Email> emails = new ArrayList<Email>();
		Email email = new Email();
		String key = null ;
		Properties prop = new Properties();     
		try{
			//读取属性文件a.properties
			InputStream in = new BufferedInputStream (new FileInputStream("C:\\Users\\Administrator\\Desktop\\jboa\\jboa\\jboa_core\\src\\main\\resources\\email.properties"));
			prop.load(in);     ///加载属性列表

			email.setHost(prop.getProperty("host"));
			email.setPort(prop.getProperty("port"));
			email.setUsername(prop.getProperty("username"));
			email.setPassword(prop.getProperty("password"));
			in.close();

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		emails.add(email);
		JsonData jsonData = new JsonData();
		jsonData.setRows(emails);
		jsonData.setTotal(1);


		return jsonData;

	}

	@RequestMapping(params="method=update")
	public @ResponseBody String update(HttpServletRequest request) 

	{

		try {
			Properties prop = new Properties();     

			InputStream in = new BufferedInputStream (new FileInputStream("C:\\Users\\Administrator\\Desktop\\jboa\\jboa\\jboa_core\\src\\main\\resources\\email.properties"));
			prop.load(in);     ///加载属性列表
			///保存属性到b.properties文件
			FileOutputStream oFile = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\jboa\\jboa\\jboa_core\\src\\main\\resources\\email.properties", true);//true表示追加打开
			System.err.println(request.getParameter("host"));
			System.err.println(request.getParameter("port"));
			System.err.println(request.getParameter("username"));
			System.err.println(request.getParameter("password"));

			prop.setProperty("host", request.getParameter("host"));
			prop.setProperty("port", request.getParameter("port"));
			prop.setProperty("username", request.getParameter("username"));
			prop.setProperty("password", request.getParameter("password"));

			prop.store(oFile, "The New properties file");
			oFile.close();


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		   ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		   ac.setBeanName("");
 	   XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)ac.getBeanFactory());  

        try {
			beanDefinitionReader.loadBeanDefinitions(ac.getResources("applicationContext.xml"));
		} catch (BeanDefinitionStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		return "ok";

	}
	
    
    /**
	 *  gridBean 是经常要改动的，通过这个方法的访问可以，可以在不启动tomcat的情况下，把修改的bean重新注册覆盖，耗时几乎可忽略
	 *  访问的入口：http://localhost:8080/gzfs/ztool/refreshGridBeans.html
     */

    
    public  void refreshBeans(String[] configLocations ) throws BeanDefinitionStoreException, IOException {
    	   ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	   XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)ac.getBeanFactory());  
           beanDefinitionReader.setResourceLoader(ac);  
           beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(ac));  
           for(int i=0;i<configLocations.length;i++)  {
               beanDefinitionReader.loadBeanDefinitions(ac.getResources(configLocations[i]));  
           }

       }  

}
