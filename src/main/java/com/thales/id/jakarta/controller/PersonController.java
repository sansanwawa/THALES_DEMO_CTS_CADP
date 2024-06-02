package com.thales.id.jakarta.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import com.thales.id.jakarta.dao.repository.PersonRepository;
import com.thales.id.jakarta.dao.repository.ThalesConfigRepository;
import com.thales.id.jakarta.entities.Person;
import com.thales.id.jakarta.entities.ThalesStaticKey;
import com.thales.id.jakarta.entities.request.PersonRequest;
import com.thales.id.jakarta.utils.CTSPoster;
import com.thales.id.jakarta.utils.ConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author :
 * sandy.haryono@thalesgroup.com
 */

@Controller
public class PersonController {

	private final AtomicLong counter = new AtomicLong();	
	
	private static final Logger logger = LogManager.getLogger(PersonController.class);

	@Autowired
	private PersonRepository personDao;

	@Autowired
	private ThalesConfigRepository configDao;



	@PostMapping(path = "/create/person", consumes = "application/x-www-form-urlencoded")
	public ModelAndView registerPerson(PersonRequest personRequest) {
		logger.info("Parameter received = " + personRequest);
		String name,email,address,cardName,cardNumber, city,cvv, expiredDate,state,zip;


		name 		= personRequest.getName();
		email 		= personRequest.getEmail();
		address 	= personRequest.getAddress();
		cardName 	= personRequest.getCardName();
		cardNumber 	= personRequest.getCardNumber();
		city 		= personRequest.getCity();
		cvv 		= personRequest.getCvv();
		expiredDate = personRequest.getExpDate();
		state		= personRequest.getState();
		zip			= personRequest.getZip();

		Person personReq = new Person();
		personReq.setName(name);
		personReq.setEmail(email);
		personReq.setAddress(address);
		personReq.setCardName(cardName);
		personReq.setCardNumber(cardNumber);
		personReq.setCity(city);
		personReq.setCvv(cvv);
		personReq.setExpDate(expiredDate);
		personReq.setState(state);
		personReq.setZip(zip);

		CTSPoster.tokenizeInit(
				ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKENIZE_URL),
				ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_NAME),
				ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_PASSWORD),
				ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_TEMPLATE),
				ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_GROUP));


		Person person = new Person();
		try {
			person.setName(CTSPoster.tokenize(name).getToken());
			person.setEmail(CTSPoster.tokenize(email).getToken());
			person.setAddress(CTSPoster.tokenize(address).getToken());
			person.setCardName(CTSPoster.tokenize(cardName).getToken());
			person.setCardNumber(CTSPoster.tokenize(cardNumber).getToken());
			person.setCity(CTSPoster.tokenize(city).getToken());
			person.setCvv(CTSPoster.tokenize(cvv).getToken());
			person.setExpDate(CTSPoster.tokenize(expiredDate).getToken());
			person.setState(CTSPoster.tokenize(state).getToken());
			person.setZip(CTSPoster.tokenize(zip).getToken());
			//save the object
			personDao.save(person);


		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (KeyStoreException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}



		Pageable pageAble = PageRequest.of(0, 10,Sort.by(Sort.Order.desc("id")));
		List<Person> dataPerson = personDao.getAllPerson(pageAble);
		ModelAndView mav = new ModelAndView("success");
		mav.addObject("personDatas", dataPerson);
		mav.addObject(personReq);

		return mav;

	}


	@GetMapping(path = "/person/detail/{id}")
	public ModelAndView detailPerson(@PathVariable("id") Long id) {

		Optional<Person> loaded 	= personDao.findById(id);
		Person data 				= loaded.get();
		String tokenizeName 		= data.getName();
		String tokenizeEmail 		= data.getEmail();
		String tokenizeAddress 		= data.getAddress();
		String tokenizeCity 		= data.getCity();
		String tokenizeState 		= data.getState();
		String tokenizeZip 			= data.getZip();
		String tokenizeNameOnCard 	= data.getCardName();
		String tokenizeCreditCard	= data.getCardNumber();
		String tokenizeExpDate 		= data.getExpDate();
		String tokenizeCVV			= data.getCvv();


		logger.info(String.format("tokenizeName : %s", tokenizeName));
		logger.info(String.format("tokenizeEmail : %s", tokenizeEmail));
		logger.info(String.format("tokenizeAddress : %s", tokenizeAddress));
		logger.info(String.format("tokenizeCity : %s", tokenizeCity));
		logger.info(String.format("tokenizeState : %s", tokenizeState));
		logger.info(String.format("tokenizeZip : %s", tokenizeZip));
		logger.info(String.format("tokenizeNameOnCard : %s", tokenizeNameOnCard));
		logger.info(String.format("tokenizeCreditCard : %s", tokenizeCreditCard));
		logger.info(String.format("tokenizeExpDate : %s", tokenizeExpDate));
		logger.info(String.format("tokenizeCVV : %s", tokenizeCVV));



		try {

			CTSPoster.deTokenizeInit(
					ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_DETOKENIZE_URL),
					ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_NAME),
					ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_USER_PASSWORD),
					ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_TEMPLATE),
					ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CTS_TOKEN_GROUP)
			);


			String name 		= CTSPoster.deTokenize(tokenizeName).getData();
			String email 		= CTSPoster.deTokenize(tokenizeEmail).getData();
			String address 		= CTSPoster.deTokenize(tokenizeAddress).getData();
			String city 		= CTSPoster.deTokenize(tokenizeCity).getData();
			String state 		= CTSPoster.deTokenize(tokenizeState).getData();
			String zip 			= CTSPoster.deTokenize(tokenizeZip).getData();
			String nameOnCard 	= CTSPoster.deTokenize(tokenizeNameOnCard).getData();
			String cc 			= CTSPoster.deTokenize(tokenizeCreditCard).getData();
			String expDate 		= CTSPoster.deTokenize(tokenizeExpDate).getData();
			String cvv 			= CTSPoster.deTokenize(tokenizeCVV).getData();

			Person person = new Person();
			person.setName(name);
			person.setEmail(email);
			person.setAddress(address);
			person.setCity(city);
			person.setState(state);
			person.setZip(zip);
			person.setCardName(nameOnCard);
			person.setCardNumber(cc);
			person.setExpDate(expDate);
			person.setCvv(cvv);


			ModelAndView mav = new ModelAndView("detail");
			mav.addObject(person);
			return mav;

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		}


	}
}
