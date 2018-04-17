package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;


public class MyException {
    
    
    public void doThrowing(JoinPoint jp, Throwable ex) 
    {  
        HttpServletRequest request = SysContent.getRequest();  
        request.setAttribute("error", ex.getMessage());
        request.setAttribute("errorDetail", jp.getTarget().getClass().getName()  + "." + jp.getSignature().getName());
        HttpSession session = SysContent.getSession();
        session.setAttribute("session", "test  session");
    }  
}
