package hello;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ling on 17/7/13.
 */
@RestController
public class GreetingController {

    private static final  String template  ="Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin(origins = "http://localhost:63341")
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required = false,defaultValue = "World",value = "name")String name){
        System.out.println("=====in greeting====");
        return new Greeting(counter.incrementAndGet(),String.format(template,name));
    }

    @GetMapping("/greeting-javaconfig")
    public Greeting greetingWithJavaconfig(@RequestParam(required = false,defaultValue ="world" ,value = "name")String name){
        System.out.println("=====in greeting====");
        return new Greeting(counter.incrementAndGet(),String.format(template,name));
    }
}
