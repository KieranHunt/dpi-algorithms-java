package casa.kieran;

import casa.kieran.dpi.test.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Test test;

        if (args.length == 0) {
            test = new Test("resources/testConfiguration.json");
        } else {
            test = new Test(args[0]);
        }

        test.run();

        LOGGER.info("");

    }
}
