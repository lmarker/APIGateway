package icat.apigateway.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * API核心类
 * @author ljh_2015
 *
 */
public class APICore {

    private final static APICore core = new APICore();
    
    private APIGateway apiGateway = new APIGateway();

    public static APICore getInstance() {
	return core;
    }
    
    public Object run(String url) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	APIRunning run= new APIRunning(url);
	return apiGateway.run(run);
    }

    //内部类
    class APIRunning {

	//url
	private String url;
	//参数传递
	private String params;
	//get/post方法
	private String method;

	/**
	 * 通过jsonUrl配置获取相应信息
	 * @param jsonUrl
	 */
	public APIRunning(String jsonUrl) {
	    openController(jsonUrl);
	}

	private void openController(String jsonUrl) {
	    JSONObject obj = JSONObject.fromObject(jsonUrl);
	    Iterator<String> it = obj.keys();
	    while(it.hasNext()) {
		String key = it.next().toString();
		if(key.equalsIgnoreCase("url")) {
		    url = obj.getString(key);
		}

		if(key.equalsIgnoreCase("params")) {
		    params = obj.getString(key);
		}

		if(key.equalsIgnoreCase("method")) {
		    method=obj.getString(key);
		}
	    }

	}

	public String getUrl() {
	    return url;
	}

	public String getParams() {
	    return params;
	}

	public String getMethod() {
	    return method;
	}
	
	public void run() {
	    
	}

    }
}
