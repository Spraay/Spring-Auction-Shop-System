package krystianrytel.sklepaukcyjny.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Insufficient Funds")
public class UserInsufficientFundsException extends RuntimeException{

    public UserInsufficientFundsException(){
        super(String.format("Nie wystarczające środki na koncie! Doładuj Portfel"));
    }

}