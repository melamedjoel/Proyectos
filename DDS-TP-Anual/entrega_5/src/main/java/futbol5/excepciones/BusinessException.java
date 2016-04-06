package futbol5.excepciones;

import java.lang.RuntimeException;

public class BusinessException extends RuntimeException {

	public BusinessException(String msg){
		super(msg);
	}	
	
}