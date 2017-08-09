package bookmarks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ling on 17/7/18.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId){
        super("Could not find user '"+userId+"'.");
    }
}
