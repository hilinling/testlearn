package hello;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ling on 17/8/1.
 */
public class PlainApplication {

    public static void main(String[] args){
        Dog dog = new Dog();
        List<String> list = dog.getDogs();
        list.add(0,"dslkds");
        System.out.println("dogs: "+list);
        System.out.println("dogs: "+dog.getDogs());
    }
}
