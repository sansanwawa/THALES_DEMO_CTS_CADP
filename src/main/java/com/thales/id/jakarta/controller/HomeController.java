package com.thales.id.jakarta.controller;

import com.thales.id.jakarta.dao.repository.ThalesConfigRepository;
import com.thales.id.jakarta.entities.ThalesStaticKey;
import com.thales.id.jakarta.entities.request.CADPConfigRequest;
import com.thales.id.jakarta.entities.request.CTSConfigRequest;
import com.thales.id.jakarta.entities.request.EncryptDecryptRequest;
import com.thales.id.jakarta.utils.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author :
 * sandy.haryono@thalesgroup.com
 */

@Controller
public class HomeController {

	@Autowired
	private ThalesConfigRepository configDao;

	@GetMapping("/cts/form/demo")
	public ModelAndView ctsDemoFrom() {
		ModelAndView mav = new ModelAndView("cts_form");
		return mav;
	}

	@GetMapping("/cadp/form/demo")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("cadp_form");
		return mav;
	}

	@GetMapping("/")
	public ModelAndView home() {
		//loaded home.html

 		String CTS_TOKENIZE_URL 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKENIZE_URL);
		String CTS_DETOKENIZE_URL 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_DETOKENIZE_URL);
		String CTS_USER_NAME 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_NAME);
		String CTS_USER_PASSWORD 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_PASSWORD);
		String CTS_TOKEN_TEMPLATE 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_TEMPLATE);
		String CTS_TOKEN_GROUP 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_GROUP);

		CTSConfigRequest conf = new CTSConfigRequest();
 		conf.setCtsTokenizeUrl(CTS_TOKENIZE_URL);
		conf.setCtsDeTokenizeUrl(CTS_DETOKENIZE_URL);
		conf.setCtsPassword(CTS_USER_PASSWORD);
		conf.setCtsTokenTemplate(CTS_TOKEN_TEMPLATE);
		conf.setCtsTokenGroup(CTS_TOKEN_GROUP);
		conf.setCtsUsername(CTS_USER_NAME);


		ModelAndView mav = new ModelAndView("home");
		mav.addObject("configrequest",conf);
		return mav;
	}



	@PostMapping("/cts-config")
	public ModelAndView ctsConfig() {

		String CTS_TOKENIZE_URL 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKENIZE_URL);
		String CTS_DETOKENIZE_URL 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_DETOKENIZE_URL);
		String CTS_USER_NAME 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_NAME);
		String CTS_USER_PASSWORD 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_PASSWORD);
		String CTS_TOKEN_TEMPLATE 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_TEMPLATE);
		String CTS_TOKEN_GROUP 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_GROUP);

		CTSConfigRequest conf = new CTSConfigRequest();
		conf.setCtsTokenizeUrl(CTS_TOKENIZE_URL);
		conf.setCtsDeTokenizeUrl(CTS_DETOKENIZE_URL);
		conf.setCtsPassword(CTS_USER_PASSWORD);
		conf.setCtsTokenTemplate(CTS_TOKEN_TEMPLATE);
		conf.setCtsTokenGroup(CTS_TOKEN_GROUP);
		conf.setCtsUsername(CTS_USER_NAME);


		ModelAndView mav = new ModelAndView("cts_config");
		mav.addObject("configrequest",conf);
		return mav;
	}





	@PostMapping("/cadp-config")
	public ModelAndView cadpConfig() {

		String CADP_NAE_IP 					= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_IP);
		String CADP_NAE_PORT 				= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_PORT);
		String CADP_NAE_VERIFY_SSL 			= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_VERIFY_SSL);
		String CADP_NAE_PROTOCOL 			= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_PROTOCOL);
		String CADP_NAE_LOAD_BALANCING_ALG 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_LOAD_BALANCING_ALG);
		String CADP_KEY_NAME_DEFAULT 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_KEY_NAME_DEFAULT);
		String CADP_DEFAULT_ALGO 			= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_DEFAULT_ALGO);
		String CADP_USERNAME_DEFAULT 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_USERNAME_DEFAULT);
		String CADP_PASSWORD_DEFAULT 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_PASSWORD_DEFAULT);



		CADPConfigRequest conf = new CADPConfigRequest();
		conf.setCadpNAEIP(CADP_NAE_IP);
		conf.setCadpNAEPort(CADP_NAE_PORT);
		conf.setCadpVerifySSL(CADP_NAE_VERIFY_SSL);
		conf.setCadpNAEProtokol(CADP_NAE_PROTOCOL);
		conf.setCadpLoadBalancingAlg(CADP_NAE_LOAD_BALANCING_ALG);
		conf.setCadpKeyName(CADP_KEY_NAME_DEFAULT);
		conf.setCadpAlgorithm(CADP_DEFAULT_ALGO);
		conf.setCadpUsername(CADP_USERNAME_DEFAULT);
		conf.setCadpPassword(CADP_PASSWORD_DEFAULT);



		ModelAndView mav = new ModelAndView("cadp_config");
		mav.addObject("configrequest",conf);
		return mav;
	}




}
