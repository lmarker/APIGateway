package icat.apigateway.core;

import java.lang.reflect.InvocationTargetException;

import icat.apigateway.core.APICore.APIRunning;
import icat.apigateway.core.APIStore.MappingNode;
import net.sf.json.JSONArray;

/**
 * API网关
 * @author ljh_2015
 *
 */
public class APIGateway {
    
    public Object run(APIRunning running) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	String key = running.getUrl();
	String array = running.getParams();
	MappingNode node = APIStore.getMappingNode(key);
	return node.run(array);
    }
    
}
