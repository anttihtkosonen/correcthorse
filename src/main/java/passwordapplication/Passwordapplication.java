package passwordapplication;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the application. Used for starting the program.
 *
 * @author antti
 */
@SpringBootApplication
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
