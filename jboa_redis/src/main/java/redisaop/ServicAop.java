package redisaop;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import entity.BizClaimVoucher;

@Aspect
@Component
public class ServicAop implements MethodInterceptor {






	@Around("execution(* bizimpl.BizClaimVoucherBizImpl.getClaimVouchers(..))")
	public Object send(ProceedingJoinPoint joinPoint)
	{

		Object object[]=	joinPoint.getArgs();

		//获取类名
		String classname=joinPoint.getClass().getName();
		//获取方法名 
		String method = joinPoint.getSignature().getName();
		//获取页码
		int page=(Integer) object[3];
		//获取条数
		int pagenum=(Integer) object[4];

		
		StringBuilder key = new StringBuilder();
		key.append(classname+"_"+method+"_"+"pagesize="+page+"_pagenum="+pagenum+"");

 
		try {
			// 判断是否有缓存
			if (exists(key.toString())) {
				return getCache(key.toString());
			}
			// 写入缓存并贯穿持久层
			List<BizClaimVoucher> bizClaimVouchers = (List<BizClaimVoucher>) joinPoint.proceed();
			
			if (bizClaimVouchers != null) {
				final String tkey = key.toString();
				final Object value = bizClaimVouchers;
				new Thread(new Runnable() {
					public void run() {
						setCache(tkey, value, defaultCacheExpireTime);
					}
				}).start();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}


		return joinPoint;
	}







	private RedisTemplate<Serializable, Object> redisTemplate;
	private Long defaultCacheExpireTime = 10l; // 缓存默认的过期时间,这里设置了10秒


	/**
	 * 创建缓存key
	 *
	 * @param targetName
	 * @param methodName
	 * @param arguments
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sbu = new StringBuffer();
		sbu.append(targetName).append("_").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sbu.append("_").append(arguments[i]);
			}
		}
		return sbu.toString();
	}



	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object getCache(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		result = operations.get(key);
		return result;
	}



	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setCache(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setRedisTemplate(
			RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}



	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}
}