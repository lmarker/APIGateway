package icat.apigateway.core;

public class APIException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String message = null;
    
    public APIException(String message) {
	if(message!=null)
	    this.message = message;
    }

    @Override
    public String getMessage() {
	return message;
    }
    
    
    
}
