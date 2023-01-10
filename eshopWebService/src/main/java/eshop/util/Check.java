package eshop.util;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

public class Check {

    public static void checkBindingResultHasError(BindingResult br) {
        
        if (br.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}