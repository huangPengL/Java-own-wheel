package com.hpl.spring.core.impl;

import com.hpl.spring.annotation.Bean;
import com.hpl.spring.annotation.Di;
import com.hpl.spring.core.ApplicationContext;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangpenglong
 * @Date: 2023/7/1 22:53
 */
public class AnnotationApplicationContext implements ApplicationContext {

    private Map<Class, Object> beanFactory = new HashMap<Class, Object>();
    private static String rootPath;

    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    public AnnotationApplicationContext(String basePackage){

        try {
            // 1 将包名的.替换成路径斜杠
            String packageDirName = basePackage.replaceAll("\\.", "\\\\");

            // 2 获取编译后的包路径
            URL resource = Thread.currentThread().getContextClassLoader().getResource(packageDirName);
            if(resource == null){
                return;
            }

            // 3 转码，将编码后的斜杠进行解码
            String filePath = URLDecoder.decode(resource.getFile(), "UTF-8");

            // 4 设置根路径
            rootPath = filePath.substring(0, filePath.length() - basePackage.length());

            // 5 包扫描
            loadBean(new File(filePath));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 6 依赖注入
        loadDi();
    }

    /**
     * 依赖注入
     * 遍历beanFactory，查看对象中的属性是否包含@Di，是则进行注入
     */
    private void loadDi() {
        for(Map.Entry<Class, Object> entry: beanFactory.entrySet()){
            Object obj = entry.getValue();
            Class<?> clazz = obj.getClass();
            for(Field field: clazz.getDeclaredFields()){
                Di annotation = field.getAnnotation(Di.class);
                if(annotation != null){
                    field.setAccessible(true);

                    try {
                        field.set(obj, beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 包扫描
     * @param file
     */
    private void loadBean(File file) {
        // 1 判断当前路径是否为文件夹
        if(!file.isDirectory()){
            return;
        }

        // 2 判断文件夹内是否有内容
        File[] files = file.listFiles();
        if(files == null || files.length == 0){
            return;
        }

        // 3 遍历文件夹内容
        for(File childFile: files) {
            // 3.1 若内容中仍然有文件夹，递归
            if(childFile.isDirectory()){
                loadBean(childFile);
                continue;
            }
            // 3.2 若内容中有文件，先得到包路径 + 类名部分。如com/hpl/spring/service/impl/TestImpl.class
            String pathWithClass = childFile.getAbsolutePath().substring(rootPath.length()-1);

            // 4 判断该文件是不是Class文件
            if(!pathWithClass.endsWith(".class")){
                continue;
            }

            // 5 把/替换成. 然后把.class去掉。如com.hpl.spring.service.impl.TestImpl （因为接下来要通过反射获取类的信息，Class.forName("全路径")）
            String className = pathWithClass
                    .replaceAll("\\\\", "\\.")
                    .replaceAll(".class", "");

            try {
                // 6.1 获取类的Class对象
                Class<?> clazz = Class.forName(className);

                // 6.2 判断是否为接口
                if(clazz.isInterface()){
                    continue;
                }

                // 6.3 判断该类有没有@Bean注解，若有则进行实例化，放到beanFactory中
                Bean annotation = clazz.getAnnotation(Bean.class);
                if(annotation == null){
                    continue;
                }
                // 实例化
                Object obj = clazz.getConstructor().newInstance();

                // 放入beanFactory
                beanFactory.put(clazz, obj);

                // 若该类有接口，把接口作为key，放入beanFactory
                for(Class interFaceClazz: clazz.getInterfaces()){
                    beanFactory.put(interFaceClazz, obj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
