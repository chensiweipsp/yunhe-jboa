package redisaop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
public class BizClaimVoucherServicAop implements MethodInterceptor {



	@Around("execution(* bizimpl.BizClaimVoucherBizImpl.getClaimVouchers(..))")
	public Object getClaimVouchersCache(ProceedingJoinPoint joinPoint)
	{

		Object object[]=	joinPoint.getArgs();

		//获取类名
		String classname=joinPoint.getTarget().toString();
		//获取方法名 
		String method = joinPoint.getSignature().getName();
		//获取页码
		int page=(Integer) object[3];
		//获取条数
		int pagenum=(Integer) object[4];
		
		Boolean isLookThrough =(Boolean) object[6];

		StringBuilder key = new StringBuilder();
		key.append(classname+"_"+method+"_"+"pagesize="+page+"_pagenum="+pagenum+"");
		List<BizClaimVoucher> bizClaimVouchers = new ArrayList<>();
		try {
			// 判断是否有缓存
			if (exists(key.toString())&&!isLookThrough ) {
				return getCache(key.toString());
			}
			
			// 写入缓存并贯穿持久层
			bizClaimVouchers = (List<BizClaimVoucher>) joinPoint.proceed();
			
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
		return bizClaimVouchers;
	}

	
	@Around("execution(* bizimpl.BizClaimVoucherBizImpl.getClaimVoucherCount(..))")
	public Object getClaimVoucherCountCache(ProceedingJoinPoint joinPoint)
	{
		Object object[]=	joinPoint.getArgs();
		//获取类名
		String classname=joinPoint.getTarget().toString();
		//获取方法名 
		String method = joinPoint.getSignature().getName();
		
		Boolean isLookThrough =(Boolean) object[4];

		
		StringBuilder key = new StringBuilder();
		key.append(classname+"_"+method+"");
		int count=0;
		try {
			// 判断是否有缓存
			if (exists(key.toString())&&!isLookThrough) {
				return getCache(key.toString());
			}
			// 写入缓存并贯穿持久层
			  count = (Integer) joinPoint.proceed();
			
			if (count != 0) {
				final String tkey = key.toString();
				final Object value = count;
				new Thread(new Runnable() {
					public void run() {
						setCache(tkey, value, defaultCacheExpireTime);
					}
				}).start();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return count;
	}



	@Around("execution(* bizimpl.BizClaimVoucherBizImpl.SaveOrUpdateClaimVouchers(..))")
	public Object SaveOrUpdateClaimVouchersClearCache(ProceedingJoinPoint joinPoint)
	{
		String key ="bizimpl.BizClaimVoucherBizImpl";
		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//清除缓存
		clearCache(key);
		
		return result;
	}


	@Around("execution(* bizimpl.BizClaimVoucherBizImpl.ispass(..))")
	public Object ispaddClearCache(ProceedingJoinPoint joinPoint)
	{
		
		String key ="bizimpl.BizClaimVoucherBizImpl";
		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//清除缓存
		clearCache(key);
		return result;
	}
	@Around("execution(* bizimpl.BizClaimVoucherBizImpl.deleteClaimVouchers(..))")
	public Object deleteClaimVouchersClearCache(ProceedingJoinPoint joinPoint)
	{
		
		String key ="bizimpl.BizClaimVoucherBizImpl";
		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//清除缓存
		clearCache(key);
		return result;
	}


	private RedisTemplate<Serializable, Object> redisTemplate;
	private Long defaultCacheExpireTime = 10000l; // 缓存默认的过期时间,这里设置了10秒


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


	
	public boolean clearCache(String className) {
		boolean result = false;
		try {
			
			Set<Serializable> keys = redisTemplate.keys(className+"*");			
            redisTemplate.delete(keys);	
            result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
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