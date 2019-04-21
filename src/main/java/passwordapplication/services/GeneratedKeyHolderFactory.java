package passwordapplication.services;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * This class generates and returns a keyholder to hold a database index key. It
 * exists for the sole reason of facilitating testing, since the keyholder can
 * only be mocked when generated through a separate factory-object, using the
 * testing technology employed in this project.
 *
 * @author antti
 */
@Component
public class GeneratedKeyHolderFactory {

    /**
     * Method to generate a keyholder
     * @return keyholder
     */
    public KeyHolder newKeyHolder() {
        return new GeneratedKeyHolder();
    }
}
