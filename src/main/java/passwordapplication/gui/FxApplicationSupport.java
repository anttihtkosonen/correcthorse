package passwordapplication.gui;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This is an abstract class that implements methods needed for initializing 
 * and launching the Spring-application with JavaFX
 * 
 * @author antti
 */
@Component
public abstract class FxApplicationSupport extends Application {

    static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = SpringApplication.run(getClass());
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }

    public static void launchApp(Class<? extends FxApplicationSupport> appClass) {
        System.out.println("launchApp");
        Application.launch(appClass);
    }

}