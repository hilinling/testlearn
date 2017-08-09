package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ling on 17/7/20.
 */
@SpringBootApplication
public class ResourceApplication {

    public static void main(String[] args){
        SpringApplication.run(ResourceApplication.class,args);
    }
}
