package eshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CommandeException extends RuntimeException{

	public CommandeException() {

	}
	
	public CommandeException(String message) {
		super(message);
	}
}
