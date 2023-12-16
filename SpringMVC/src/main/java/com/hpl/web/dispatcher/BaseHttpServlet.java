package com.hpl.web.dispatcher;

import com.hpl.web.context.AbstractRefreshableWebApplicationContext;
import com.hpl.web.context.WebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/14 10:54
 */
public abstract class BaseHttpServlet extends HttpServlet {
    protected ApplicationContext webApplicationContext;

    public BaseHttpServlet(ApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    /**
     * web ioc的初始化和配置
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletConfig servletConfig = getServletConfig();

        // 获取父容器
        ApplicationContext rootContext = (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_NAME);

        // 若web ioc不为空，则设置父容器，配置上下文，刷新容器
        if(!ObjectUtils.isEmpty(webApplicationContext)){
            if(!(this.webApplicationContext instanceof AnnotationConfigApplicationContext)){
                // 需要转换
                AbstractRefreshableWebApplicationContext wac =
                        (AbstractRefreshableWebApplicationContext) this.webApplicationContext;

                // 设置父容器
                wac.setParent(rootContext);

                // 配置上下文
                wac.setServletContext(servletContext);
                wac.setServletConfig(servletConfig);

                // 刷新容器
                wac.refresh();
            }
        }

        // 刷新Servlet(子类实现)
        onRefresh(webApplicationContext);
    }

    protected abstract void onRefresh(ApplicationContext webApplicationContext);
}
