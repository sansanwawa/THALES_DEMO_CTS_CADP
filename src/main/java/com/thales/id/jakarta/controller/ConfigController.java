package com.thales.id.jakarta.controller;

import com.thales.id.jakarta.dao.repository.ThalesConfigRepository;
import com.thales.id.jakarta.entities.ThalesConfig;
import com.thales.id.jakarta.entities.ThalesStaticKey;
import com.thales.id.jakarta.entities.request.CADPConfigRequest;
import com.thales.id.jakarta.entities.request.CTSConfigRequest;
import com.thales.id.jakarta.utils.ConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * Author : sandy.haryono@thalesgroup.com
 *
 */

@Controller
public class ConfigController {

	private static final Logger logger = LogManager.getLogger(ConfigController.class);


	@Autowired
	private ThalesConfigRepository configDao;
	@PostMapping(path = "/save/cts/config", consumes = "application/x-www-form-urlencoded")
	public ModelAndView saveCTSConfig(CTSConfigRequest configRequest) {

		String PARAM_CTS_TOKENIZE_URL = configRequest.getCtsTokenizeUrl();
		String PARAM_CTS_DETOKENIZE_URL = configRequest.getCtsDeTokenizeUrl();
		String PARAM_CTS_USER_NAME = configRequest.getCtsUsername();
		String PARAM_CTS_USER_PASSWORD = configRequest.getCtsPassword();
		String PARAM_CTS_TOKEN_TEMPLATE = configRequest.getCtsTokenTemplate();
		String PARAM_CTS_TOKEN_GROUP = configRequest.getCtsTokenGroup();

		//save the params
		configDao.save(new ThalesConfig(ThalesStaticKey.CTS_TOKENIZE_URL, PARAM_CTS_TOKENIZE_URL));
		configDao.save(new ThalesConfig(ThalesStaticKey.CTS_DETOKENIZE_URL, PARAM_CTS_DETOKENIZE_URL));
		configDao.save(new ThalesConfig(ThalesStaticKey.CTS_USER_NAME, PARAM_CTS_USER_NAME));
		configDao.save(new ThalesConfig(ThalesStaticKey.CTS_USER_PASSWORD, PARAM_CTS_USER_PASSWORD));
		configDao.save(new ThalesConfig(ThalesStaticKey.CTS_TOKEN_TEMPLATE, PARAM_CTS_TOKEN_TEMPLATE));
		configDao.save(new ThalesConfig(ThalesStaticKey.CTS_TOKEN_GROUP, PARAM_CTS_TOKEN_GROUP));


		String CTS_USER_NAME 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_NAME);
		String CTS_TOKENIZE_URL 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKENIZE_URL);
		String CTS_DETOKENIZE_URL 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_DETOKENIZE_URL);
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


	@PostMapping(path = "/save/cadp/config", consumes = "application/x-www-form-urlencoded")
	public ModelAndView saveCADPConfig(CADPConfigRequest configRequest) {

		String PARAM_CADP_NAE_IP = configRequest.getCadpNAEIP();
		String PARAM_CADP_NAE_PORT = configRequest.getCadpNAEPort();
		String PARAM_CADP_KEY_NAME_DEFAULT = configRequest.getCadpKeyName();
		String PARAM_CADP_DEFAULT_ALGO = configRequest.getCadpAlgorithm();

		String PARAM_CADP_USERNAME_DEFAULT = configRequest.getCadpUsername();
		String PARAM_CADP_PASSWORD_DEFAULT = configRequest.getCadpPassword();

		//save the params
		configDao.save(new ThalesConfig(ThalesStaticKey.CADP_NAE_IP, PARAM_CADP_NAE_IP));
		configDao.save(new ThalesConfig(ThalesStaticKey.CADP_NAE_PORT, PARAM_CADP_NAE_PORT));
		configDao.save(new ThalesConfig(ThalesStaticKey.CADP_KEY_NAME_DEFAULT, PARAM_CADP_KEY_NAME_DEFAULT));
		configDao.save(new ThalesConfig(ThalesStaticKey.CADP_DEFAULT_ALGO, PARAM_CADP_DEFAULT_ALGO));
		configDao.save(new ThalesConfig(ThalesStaticKey.CADP_USERNAME_DEFAULT, PARAM_CADP_USERNAME_DEFAULT));
		configDao.save(new ThalesConfig(ThalesStaticKey.CADP_PASSWORD_DEFAULT, PARAM_CADP_PASSWORD_DEFAULT));


		String CADP_NAE_IP 				= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_IP);
		String CADP_NAE_PORT 			= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_PORT);
		String CADP_KEY_NAME_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_KEY_NAME_DEFAULT);
		String CADP_DEFAULT_ALGO 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_DEFAULT_ALGO);
		String CADP_USERNAME_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_USERNAME_DEFAULT);
		String CADP_PASSWORD_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_PASSWORD_DEFAULT);


		CADPConfigRequest conf = new CADPConfigRequest();
		conf.setCadpNAEIP(CADP_NAE_IP);
		conf.setCadpNAEPort(CADP_NAE_PORT);
		conf.setCadpKeyName(CADP_KEY_NAME_DEFAULT);
		conf.setCadpAlgorithm(CADP_DEFAULT_ALGO);
		conf.setCadpUsername(CADP_USERNAME_DEFAULT);
		conf.setCadpPassword(CADP_PASSWORD_DEFAULT);


		ModelAndView mav = new ModelAndView("cadp_config");
		mav.addObject("configrequest",conf);
		return mav;

	}






}
