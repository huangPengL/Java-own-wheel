package com.hpl.web.dispatcher;

import com.hpl.web.handler.HandlerMapping;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 10:56
 */
public class DispatcherServlet extends BaseHttpServlet{

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    private Properties defaultStrategies;

    public static final String DEFAULT_STRATEGIES_PATH = "DispatcherServlet.properties";

    public DispatcherServlet(ApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    /**
     * 组件初始化
     * @param webApplicationContext
     */
    @Override
    protected void onRefresh(ApplicationContext webApplicationContext) {
        initHandlerMapping(webApplicationContext);
        initHandlerMethodAdapter(webApplicationContext);
        initHandlerException(webApplicationContext);
    }

    private void initHandlerException(ApplicationContext webApplicationContext) {
    }

    private void initHandlerMethodAdapter(ApplicationContext webApplicationContext) {
    }



    private void initHandlerMapping(ApplicationContext webApplicationContext) {
        // 从容器中拿
        Map<String, HandlerMapping> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                webApplicationContext, HandlerMapping.class, true, false);
        if(!ObjectUtils.isEmpty(map)){
            this.handlerMappings.addAll(map.values());
        }
        // 容器没有从配置文件拿
        else{
            this.handlerMappings.addAll(getDefaultStrategies(webApplicationContext,HandlerMapping.class));
        }

        // 排序
        this.handlerMappings.sort(Comparator.comparingInt(Ordered::getOrder));
    }

    protected <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface) {
        if (defaultStrategies == null) {
            try {
                ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);
                defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException ex) {
                throw new IllegalStateException("Could not load '" + DEFAULT_STRATEGIES_PATH + "': " + ex.getMessage());
            }
        }

        String key = strategyInterface.getName();
        String value = defaultStrategies.getProperty(key);
        if (value != null) {
            String[] classNames = StringUtils.commaDelimitedListToStringArray(value);
            List<T> strategies = new ArrayList<>(classNames.length);
            for (String className : classNames) {
                try {
                    Class<?> clazz = ClassUtils.forName(className, DispatcherServlet.class.getClassLoader());
                    Object strategy = createDefaultStrategy(context, clazz);
                    strategies.add((T) strategy);
                } catch (ClassNotFoundException ex) {
                    throw new BeanInitializationException(
                            "Could not find DispatcherServlet's default strategy class [" + className +
                                    "] for interface [" + key + "]", ex);
                } catch (LinkageError err) {
                    throw new BeanInitializationException(
                            "Unresolvable class definition for DispatcherServlet's default strategy class [" +
                                    className + "] for interface [" + key + "]", err);
                }
            }
            return strategies;
        } else {
            return Collections.emptyList();
        }
    }

    private Object createDefaultStrategy(ApplicationContext context, Class<?> clazz) {
        return context.getAutowireCapableBeanFactory().createBean(clazz);
    }
}
