package com.thales.id.jakarta;


import com.thales.id.jakarta.dao.repository.ThalesConfigRepository;
import com.thales.id.jakarta.entities.ThalesConfig;
import com.thales.id.jakarta.entities.ThalesStaticKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * WARNING : THIS IS ONLY FOR TESTING PURPOSE!!!
 * Author :
 * sandy.haryono@thalesgroup.com
 */

@SpringBootApplication
public class App implements CommandLineRunner {

	private static Logger logger = LogManager.getLogger(App.class);

	@Autowired
	private ThalesConfigRepository configDao;


	public static void main(String[] args) {
		logger.info("-------------------THALES DEMO APP IS STARTING----------------------");
		SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}

	public void run(String... args) {

		//load default config
		//CTS default configuration
		String CTS_URL 							= "https://YOUR_CTS_FQDN_OR_IP";
		String CTS_TOKENIZE_URL 				= String.format("%s/vts/rest/v2.0/tokenize/", CTS_URL);
		String CTS_DETOKENIZE_URL 				= String.format("%s/vts/rest/v2.0/detokenize/", CTS_URL);
		String CTS_USER_NAME 					= "YOUR_CTS_USERNAME";
		String CTS_USER_PASSWORD 				= "YOUR_CTS_PASSWORD";
		String CTS_TOKEN_TEMPLATE 				= "YOUR_CTS_TEMPLATE";
		String CTS_TOKEN_GROUP 					= "YOUR_CTS_GROUP";

		//CADP configuration
		String CADP_NAE_VERSION 				= "2.4";
		String CADP_NAE_IP 						= "YOUR_CM_IP";
		String CADP_NAE_PORT 					= "YOUR_CM_PORT";
		String CADP_NAE_DNS_RESOLUTION_FAILURE 	= "false";
		String CADP_NAE_KMIP_PORT 				= "5696";
		String CADP_NAE_PROTOCOL 				= "tcp";
		String CADP_NAE_VERIFY_SSL 				= "no";
		String CADP_NAE_LOAD_BALANCING_ALG 		= "round-robin";
		String CADP_KEY_NAME_DEFAULT 			= "YOUR_CADP_KEY";
		String CADP_DEFAULT_ALGO 				= "AES";
		String CADP_USERNAME_DEFAULT			= "YOUR_NAE_USERNAME";
		String CADP_PASSWORD_DEFAULT			= "YOUR_NAE_PASSWORD";



		ArrayList<ThalesConfig> confs = new ArrayList<ThalesConfig>(Arrays.asList(

				//CTS default configuration
				new ThalesConfig(ThalesStaticKey.CTS_TOKENIZE_URL, 		CTS_TOKENIZE_URL),
				new ThalesConfig(ThalesStaticKey.CTS_DETOKENIZE_URL, 	CTS_DETOKENIZE_URL),
				new ThalesConfig(ThalesStaticKey.CTS_USER_NAME, 		CTS_USER_NAME),
				new ThalesConfig(ThalesStaticKey.CTS_USER_PASSWORD, 	CTS_USER_PASSWORD),
				new ThalesConfig(ThalesStaticKey.CTS_TOKEN_TEMPLATE, 	CTS_TOKEN_TEMPLATE),
				new ThalesConfig(ThalesStaticKey.CTS_TOKEN_GROUP, 		CTS_TOKEN_GROUP),

				//CADP basic configuration
				new ThalesConfig(ThalesStaticKey.CADP_NAE_VERSION, 						CADP_NAE_VERSION),
				new ThalesConfig(ThalesStaticKey.CADP_NAE_IP, 							CADP_NAE_IP),
				new ThalesConfig(ThalesStaticKey.CADP_NAE_PORT, 						CADP_NAE_PORT),
				new ThalesConfig(ThalesStaticKey.CADP_NAE_DNS_RESOLUTION_FAILURE, 		CADP_NAE_DNS_RESOLUTION_FAILURE),
				new ThalesConfig(ThalesStaticKey.CADP_NAE_KMIP_PORT, 					CADP_NAE_KMIP_PORT),
				new ThalesConfig(ThalesStaticKey.CADP_NAE_PROTOCOL, 					CADP_NAE_PROTOCOL),
				new ThalesConfig(ThalesStaticKey.CADP_NAE_VERIFY_SSL, 					CADP_NAE_VERIFY_SSL),
				new ThalesConfig(ThalesStaticKey.CADP_NAE_LOAD_BALANCING_ALG, 			CADP_NAE_LOAD_BALANCING_ALG),
				new ThalesConfig(ThalesStaticKey.CADP_KEY_NAME_DEFAULT, 				CADP_KEY_NAME_DEFAULT),
				new ThalesConfig(ThalesStaticKey.CADP_DEFAULT_ALGO, 					CADP_DEFAULT_ALGO),
				new ThalesConfig(ThalesStaticKey.CADP_USERNAME_DEFAULT, 				CADP_USERNAME_DEFAULT),
				new ThalesConfig(ThalesStaticKey.CADP_PASSWORD_DEFAULT, 				CADP_PASSWORD_DEFAULT)




				));

		confs.forEach(conf ->
				configDao.save(conf)
		);
	}


	
}
