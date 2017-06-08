package icat.apigateway.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





/**
 * APIStore
 * 存储所有带有注解APIMapping的方法
 * @author ljh_2015
 *
 */
@Component
public class APIStore implements ApplicationContextAware {

    private Logger log = LoggerFactory.getLogger(APIStore.class);

    private static ConcurrentMap<String,MappingNode> apimaps = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext = null;

    @Override public void setApplicationContext(ApplicationContext context) throws BeansException {
	if(applicationContext == null) {
	    applicationContext = context;
	}
	log.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+applicationContext+"========");
    }

    /**
     * 初始化
     */
    public static void getBeans() {

	//从springboot获取全部的beans
	Map<String,Object> beans = applicationContext.getBeansWithAnnotation(Service.class);
	//遍历beans
	for(String key:beans.keySet()) {
	    Class<?> cls = beans.get(key).getClass();
	    Method[] methods = cls.getDeclaredMethods();
	    for(Method method:methods) {
		if(method.getDeclaredAnnotationsByType(APIMapping.class) != null) {
		    APIBinding(method,key);
		}
	    }
	} 
    }

    /**
     * 绑定注释与方法
     * @param method
     * @param targetClassName目标类名称
     */
    private static void APIBinding(Method method,String targetClassName) {
	APIMapping api = method.getAnnotation(APIMapping.class);
	//获取参数的类型
	apimaps.put(api.value(), new MappingNode(method,targetClassName,method.getParameters()));
    }

    /**
     * 通过api调用方法
     * @param key
     * @return
     */
    public static MappingNode getMappingNode(String key) {
	return apimaps.get(key);
    }

    public static Object getBean(String bean) {
	return applicationContext.getBean(bean);
    }


    /**
     * 映射节点
     * @author ljh_2015
     *
     */
    static class MappingNode {

	//目标方法
	private Method method;
	//方法运行目标类
	private String targetClassName;
	//目标参数
	private Parameter[] params;
	//目标参数个数
	private int paramsNum;

	public MappingNode(Method method,String targetClassName,Parameter[] params) {
	    this.targetClassName = targetClassName;
	    this.method = method;
	    this.params = params;
	    this.paramsNum = params.length;
	}

	public Object run(String args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

	    Object[] objs = new Object[paramsNum];
	    //参数长度
	    JSONArray array = JSONArray.fromObject(args);
	    if(paramsNum!=array.size())
		throw new APIException("参数长度不一致");
	    for(int i=0;i<paramsNum;i++) {
		//获取参数的参数类型
		Class<?> cls = params[i].getType();
		JSONObject json = array.getJSONObject(i);
		Iterator<String> keys = json.keys();
		while(keys.hasNext()) {
		    String key = keys.next();
		    Object obj = json.get(key);
		    objs[i] = obj.getClass().getName().equals(cls.getName())?obj:JSONObject.toBean(json.getJSONObject(key),cls);
		}
	    }
	    return method.invoke(APIStore.getBean(targetClassName),objs); 
	}


    }



}
