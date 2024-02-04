package com.hpl.dbrouter.starter.aop;

import com.hpl.dbrouter.starter.annotation.DBRouter;
import com.hpl.dbrouter.starter.model.DBRouterConfig;
import com.hpl.dbrouter.starter.util.DBContextHolder;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @Author: huangpenglong
 * @Date: 2024/2/4 15:03
 */


@Aspect
@Component("db-router-point")
public class DBRouterJoinPoint {
    private Logger logger = LoggerFactory.getLogger(DBRouterJoinPoint.class);

    @Resource
    private DBRouterConfig dbRouterConfig;

    @Pointcut("@annotation(com.hpl.dbrouter.starter.annotation.DBRouter)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint pj, DBRouter dbRouter) throws Throwable {
        // 路由key
        String dbKey = dbRouter.key();
        if (StringUtils.isBlank(dbKey)){
            throw new RuntimeException("annotation DBRouter key is null！");
        }

        // 计算路由具体值
        String dbKeyAttr = getAttrValue(dbKey, pj.getArgs());
        int size = dbRouterConfig.getDbCount() * dbRouterConfig.getTbCount();

        // 扰动函数
        int idx = (size - 1) & (dbKeyAttr.hashCode() ^ (dbKeyAttr.hashCode() >>> 16));

        // 库表索引
        int dbIdx = idx / dbRouterConfig.getTbCount() + 1;
        int tbIdx = idx - dbRouterConfig.getTbCount() * (dbIdx - 1);

        // 设置到 ThreadLocal
        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
        DBContextHolder.setTBKey(String.format("%02d", tbIdx));
        logger.info("数据库路由 method：{} dbIdx：{} tbIdx：{}", getMethod(pj).getName(), dbIdx, tbIdx);

        try {
            return pj.proceed();
        } finally {
            DBContextHolder.clearDBKey();
            DBContextHolder.clearTBKey();
        }
    }


    /**
     * 从 被@DBRouter注解标记的方法参数列表中 获取属性为路由key的值
     * @param dbKey
     * @param methodArgs
     * @return
     */
    public String getAttrValue(String dbKey, Object[] methodArgs) {
        String filedValue = null;
        for (Object arg : methodArgs) {
            try {
                if (StringUtils.isNotBlank(filedValue)){
                    break;
                }
                filedValue = BeanUtils.getProperty(arg, dbKey);
            } catch (Exception e) {
                logger.error("获取路由属性值失败 attr：{}", dbKey, e);
            }
        }
        return filedValue;
    }

    /**
     * 获取方法信息
     * @param jp
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }
}
