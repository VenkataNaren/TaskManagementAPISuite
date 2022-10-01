package nz.co.solnet;

/**
 * Starting point of Spring Boot application for Task Management API Suite
 *
 * @author Venkata Narendra
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nz.co.solnet.helper.DatabaseHelper;

@SpringBootApplication
public class BootstrapConfig implements ServletContextListener {

	private static final Logger logger = LogManager.getLogger(BootstrapConfig.class);

	public static void main(String[] args) {
		SpringApplication.run(BootstrapConfig.class, args);

	}

	/**
	 * This method gets invoked when the servlet context is initialised.
	 */
	public void contextInitialized(ServletContextEvent sce) {

		DatabaseHelper.initialiseDB();
		logger.info("DB initialised successfully");
	}

	/**
	 * This method gets invoked when the servlet context is destroyed.
	 */
	public void contextDestroyed(ServletContextEvent sce) {

		DatabaseHelper.cleanupDB();
		logger.info("DB shutdown successfully");
	}
}
