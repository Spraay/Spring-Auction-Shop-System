package krystianrytel.sklepaukcyjny.controllers;

import krystianrytel.sklepaukcyjny.exceptions.ItemNotFoundException;
import krystianrytel.sklepaukcyjny.exceptions.UserInsufficientFundsException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice//można wskazać, których konkretnie kontrolerów ma dotyczyć porada
@Log4j2
public class GlobalControlerAdvice {

    @ExceptionHandler(ItemNotFoundException.class)
    //@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such vehicle")
    public String handleItemNotFoundError(Model model, HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/error404itemNotFound";
    }

    @ExceptionHandler(UserInsufficientFundsException.class)
    //@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such vehicle")
    public String handleUserInsufficientFunds(Model model, HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/userInsufficientFunds";
    }

    @ExceptionHandler({JDBCConnectionException.class, DataIntegrityViolationException.class})
    public String handleDbError(Model model, HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/databaseErrorView";
    }
	
	@ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFounds(Model model, HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());

        return "errors/noHandlerFoundsError";
    }

	
	
}
