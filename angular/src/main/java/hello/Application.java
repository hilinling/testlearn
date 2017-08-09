package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by ling on 17/7/19.
 */
@SpringBootApplication
@EnableZuulProxy
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
