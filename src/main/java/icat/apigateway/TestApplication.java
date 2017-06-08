package icat.apigateway;

import java.lang.reflect.InvocationTargetException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icat.apigateway.core.APICore;
import icat.apigateway.core.APIStore;

@SpringBootApplication
@RestController
public class TestApplication {
    
    public static void main(String[] args) {
	SpringApplication.run(TestApplication.class, args);
	APIStore.getBeans();
    }
    
    @RequestMapping("apigateway")
    public Object test(@RequestParam("conf") String jsonUrl) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	return APICore.getInstance().run(jsonUrl);
    }
    
}
