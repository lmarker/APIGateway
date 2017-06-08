package icat.apigateway.service;

import org.springframework.stereotype.Service;

import icat.apigateway.core.APIMapping;
import icat.apigateway.entity.User;

@Service
public class TestService {

    @APIMapping("test:ok")
    public String test(User user) {
	return "see ok :"+user.getId()+" ,"+user.getName();
    }
    
    @APIMapping("test:read")
    public String read() {
	return "see read";
    }
    
    @APIMapping("test:find")
    public String findStringfromId(User user,Integer id) {
	if(user.getId()==id) 
	    return user.getName();
	return "no user found!";
    }
    
}
