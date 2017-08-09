package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ling on 17/7/12.
 */
@SpringBootApplication
public class Application implements CommandLineRunner{
    @Autowired
    private CustomerRepository repository;

    public static void main(String[] args){

        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        repository.save(new Customer("Alice","Smith"));
        repository.save(new Customer("Bob","Smith"));

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for(Customer customer:repository.findAll()){
            System.out.println(customer);
        }
        System.out.println();

        System.out.println("Customers found with findByFirstName('Alice')");
        System.out.println("-------------------------------");

        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith')");
        System.out.println("-------------------------------");

        for(Customer customer:repository.findByLastName("Smith")){

            System.out.println(customer);
        }
    }
}
