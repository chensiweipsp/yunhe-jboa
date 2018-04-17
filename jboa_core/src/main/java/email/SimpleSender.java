package email;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class SimpleSender {
	
	
	private JavaMailSender javaMailSender;
	
	
	
	
	public void send()
	{
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		
		mailMessage.setSubject("最近还玩LOL吗");
		mailMessage.setText("你太菜了 带不动");
		mailMessage.setTo("1766723331@qq.com");
		mailMessage.setFrom("295255658@qq.com");
		
		
		javaMailSender.send(mailMessage);
		
	}
	
	
	
	
	
	public void send2() throws MessagingException, UnsupportedEncodingException
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
		
		messageHelper.setSubject("最近还玩LOL吗");
		messageHelper.setText("<button>你太菜了 带不动</button>",true);
		messageHelper.setTo("1766723331@qq.com");
		messageHelper.setFrom("295255658@qq.com");
	
		
		
		ClassPathResource classPathResource1 = new ClassPathResource("com/attachfiles/Quartz.png");
		ClassPathResource classPathResource2 = new ClassPathResource("com/attachfiles/test.doc");
		ClassPathResource classPathResource3 = new ClassPathResource("com/attachfiles/附件测试文件.doc");

		
		messageHelper.addAttachment(classPathResource1.getFilename(), classPathResource1);
		messageHelper.addAttachment(classPathResource2.getFilename(), classPathResource2);
		messageHelper.addAttachment(MimeUtility.encodeWord(classPathResource3.getFilename()) , classPathResource3);

		
		javaMailSender.send(message);
		
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		SimpleSender sender= (SimpleSender) new ClassPathXmlApplicationContext("email.xml").getBean("email.SimpleSender");
		try {
			sender.send2();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	
	
	

}
