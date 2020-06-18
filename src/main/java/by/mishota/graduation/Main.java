package by.mishota.graduation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger= LogManager.getLogger();
        logger.error("error");
        logger.info("info");
        logger.debug("debug");
        logger.trace(new RuntimeException("runtime exception"));
    }
}
