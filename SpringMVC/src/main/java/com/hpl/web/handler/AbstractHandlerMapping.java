package com.hpl.web.handler;

import com.hpl.web.exception.HttpRequestMethodNotSupport;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，
 * 凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 * @Author: huangpenglong
 * @Date: 2023/12/14 15:48
 */
public abstract class AbstractHandlerMapping extends ApplicationObjectSupport implements HandlerMapping, InitializingBean {

    protected int order;
    protected  MapperRegister mapperRegister = new MapperRegister();

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {

        final HandlerMethod handlerInternal = getHandlerInternal(request);

        final HandlerExecutionChain chain = new HandlerExecutionChain(handlerInternal);

        // 拦截器 TODO

        return null;
    }

    protected abstract HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception;


    /**
     * 找到所有处理映射的方法（可以理解成Controller中的方法）
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethod();
    }

    /**
     * 找到所有的HandlerMethod
     */
    protected void initHandlerMethod() throws Exception {

        // 获取所有bean
        final ApplicationContext applicationContext = obtainApplicationContext();
        final String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                applicationContext, Object.class);
        for(String name: names){
            Class<?> type = applicationContext.getType(name);
            if(type != null && isHandler(type)){
                // 判断是否为Handler(子类实现)
                detectHandlerMethod(name);
            }
        }


    }

    /**
     * 找到这个bean当中的所有HandlerMethod
     * @param name
     * @throws Exception
     */
    protected abstract void detectHandlerMethod(String name) throws Exception;

    /**
     * 判断是否是一个handler
     * @param type
     * @return
     */
    protected abstract boolean isHandler(Class<?> type);

    public void setOrder(int order) {
        this.order = order;
    }

    protected void registerMappers(List<HandlerMethod> handlerMethods) throws Exception {
        for (HandlerMethod handlerMethod : handlerMethods) {
            mapperRegister.register(handlerMethod);
        }
    }

    protected void registerMapper(HandlerMethod handlerMethod) throws Exception {
        mapperRegister.register(handlerMethod);
    }
    
    @Override
    public int getOrder() {
        return this.order;
    }


    /**
     * 按照路径对HandlerMethod进行分组
     */
    class MapperRegister{

        /**
         * 精确路径
         */
        Map<String, Set<HandlerMethod>> accurateMatchingPath = new HashMap<>();

        /**
         * 模糊路径
         */
        Map<String,Set<HandlerMethod>> fuzzyMatchingPath = new HashMap<>();

        public void register(HandlerMethod handlerMethod) throws Exception {
            if(handlerMethod == null){
                return;
            }

            // 获取请求路径
            String path = handlerMethod.getPath();
            if (path.contains("{") && path.contains("}")) {
                // /order/get/{id} -> /order/get/(\w+)
                path = path.replaceAll("\\{\\w+\\}", "(\\\\w+)");
                register(fuzzyMatchingPath, path, handlerMethod);
            }else {
                // 根据请求路径的不同分别保存HandlerMethod
                register(accurateMatchingPath, path, handlerMethod);
            }
        }

        private void register(Map<String, Set<HandlerMethod>> mapPath,String path,HandlerMethod handlerMethod) throws Exception {
            Set<HandlerMethod> handlerMethods = mapPath.get(path);

            // map中没有注册该请求映射器方法
            if(CollectionUtils.isEmpty(handlerMethods)){
                handlerMethods = new HashSet<>();
                mapPath.put(path, handlerMethods);
            }

            if(handlerMethods.contains(handlerMethod)){
                throw new HttpRequestMethodNotSupport(Arrays.toString(handlerMethod.getRequestMethods()) + handlerMethod.getPath() +"HandlerMethod相同");
            }

            mapPath.get(path).add(handlerMethod);
        }

        public Map<String, Set<HandlerMethod>> getAccurateMatchingPath() {
            return accurateMatchingPath;
        }

        public Map<String, Set<HandlerMethod>> getFuzzyMatchingPath() {
            return fuzzyMatchingPath;
        }

    }
}
