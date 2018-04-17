/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.xml.XmlValidationModeDetector;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.xml.sax.InputSource;



@Controller
public class ZToolController {

   

    //从xml文本动态加载bean
    public Object createBeanFromXmlText(String beanContent,String beanId) {
    	//需要改进的：beanContent在数据库中更新后，可以在界面上选择bean重新注册，这样取bean的时候就不用每次都从数据库取，只有更新的时候，才去重新注册
    	ApplicationContext ac =  new ClassPathXmlApplicationContext("applicationContext.xml");
    	ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ac;  
    	BeanDefinitionRegistry reg= (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();  
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(reg);
		// VALIDATION_NONE只支持正常的xml写法 VALIDATION_XSD支持p命名空间
		reader.setValidationMode(XmlValidationModeDetector.VALIDATION_XSD); 
		String beanXmlPrefix=(String)ac.getBean("beanXmlPrefix");
		String beanXmlSuffix=(String)ac.getBean("beanXmlSuffix");
		InputSource is=  new InputSource(new StringReader(beanXmlPrefix+beanContent+beanXmlSuffix));
		reader.loadBeanDefinitions(is);
		return ac.getBean(beanId);
	}
    
    
    /**
	 *  gridBean 是经常要改动的，通过这个方法的访问可以，可以在不启动tomcat的情况下，把修改的bean重新注册覆盖，耗时几乎可忽略
	 *  访问的入口：http://localhost:8080/gzfs/ztool/refreshGridBeans.html
     */
    @RequestMapping(value="/ztool/refreshGridBeans")
    public @ResponseBody String refreshGridBeans() throws BeanDefinitionStoreException, IOException {
    	
	        String[] configLocations = new String[]{"/WEB-INF/applicationContext-query*.xml","/WEB-INF/applicationContext-sql*.xml",
			   		"/WEB-INF/applicationContext-summary.xml"
	        };  
	        refreshBeans(configLocations);
           System.out.println("refreshGridBeans finished!");
           return "<br/><br/><br/><br/><br/><br/><br/><br/><center><span style='font-size:20px'>refreshGridBeans finished!</span></center>";
           
       }  

    
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
