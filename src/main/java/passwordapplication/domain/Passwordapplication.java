package passwordapplication.domain;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main class of the application. Used for starting the program.
 *
 * @author antti
 */
@SpringBootApplication
@ComponentScan({"passwordapplication.services"})
@ComponentScan({"passwordapplication.domain"})
@ComponentScan({"passwordapplication.dao"})
@ComponentScan({"passwordapplication.models"})
public class Passwordapplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Passwordapplication.class);
    }

    @Autowired
    Textinterface textinterface;

    @Override
    public void run(String... args) throws Exception {
        Scanner input = new Scanner(System.in);
        textinterface.start(input);
    }

}
