package krystianrytel.sklepaukcyjny.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such item")
public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(){
        super(String.format("Przedmiot nie istnieje"));
    }

    public ItemNotFoundException(Long id){
        super(String.format("Przedmiot o id %d nie istnieje", id));
    }
}
