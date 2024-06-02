package com.thales.id.jakarta.controller;

import com.ingrian.security.nae.IngrianProvider;
import com.ingrian.security.nae.NAEKey;
import com.ingrian.security.nae.NAESession;
import com.thales.id.jakarta.dao.repository.ThalesConfigRepository;
import com.thales.id.jakarta.entities.ThalesStaticKey;
import com.thales.id.jakarta.entities.request.EncryptDecryptRequest;
import com.thales.id.jakarta.utils.ConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author :
 * sandy.haryono@thalesgroup.com
 */

@Controller
public class EncryptionController {

	private final AtomicLong counter = new AtomicLong();	
	
	private static final Logger logger = LogManager.getLogger(EncryptionController.class);

	@Autowired
	private ThalesConfigRepository configDao;

	private void init(){
		loadCADPParameters();
	}
	private void loadCADPParameters(){


		String CADP_NAE_VERSION 				= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_VERSION);
		String CADP_NAE_IP 						= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_IP);
		String CADP_NAE_PORT 					= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_PORT);
		String CADP_NAE_DNS_RESOLUTION_FAILURE 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_DNS_RESOLUTION_FAILURE);
		String CADP_NAE_KMIP_PORT 				= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_KMIP_PORT);
		String CADP_NAE_PROTOCOL				= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_PROTOCOL);
		String CADP_NAE_VERIFY_SSL				= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_VERIFY_SSL);
		String CADP_NAE_LOAD_BALANCING_ALG		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_NAE_LOAD_BALANCING_ALG);


		Properties property = System.getProperties();
		property.setProperty("com.ingrian.security.nae.Version",CADP_NAE_VERSION);
		property.setProperty("com.ingrian.security.nae.NAE_IP.1",CADP_NAE_IP);
		property.setProperty("com.ingrian.security.nae.NAE_Port",CADP_NAE_PORT);
		property.setProperty("com.ingrian.security.nae.Ignore_DNS_Resolution_Failure",CADP_NAE_DNS_RESOLUTION_FAILURE);
		property.setProperty("com.ingrian.security.nae.KMIP_Port",CADP_NAE_KMIP_PORT);
		property.setProperty("com.ingrian.security.nae.Protocol",CADP_NAE_PROTOCOL);
		property.setProperty("com.ingrian.security.nae.Verify_SSL_Certificate",CADP_NAE_VERIFY_SSL);
		property.setProperty("com.ingrian.security.nae.SSL_Handshake_Timeout","");
		property.setProperty("Use_Persistent_Connections","yes");
		property.setProperty("Size_of_Connection_Pool","300");
		property.setProperty("Load_Balancing_Algorithm",CADP_NAE_LOAD_BALANCING_ALG);
		property.setProperty("Connection_Idle_Timeout","600000");
		property.setProperty("Unreachable_Server_Retry_Period","60000");
		property.setProperty("Maximum_Server_Retry_Period","0");
		property.setProperty("Connection_Timeout","1m");
		property.setProperty("Connection_Read_Timeout","7000");
		property.setProperty("Connection_Retry_Interval","600000");
		property.setProperty("Client_Cert_Alias","");
		property.setProperty("Client_Cert_Passphrase","");
		property.setProperty("Use_Etoken","no");
		property.setProperty("Etoken_Name","");
		property.setProperty("Key_Store_Location","");
		property.setProperty("Key_Store_Password","");
		property.setProperty("Cluster_Synchronization_Delay","170");
		property.setProperty("Symmetric_Key_Cache_Enabled","no");
		property.setProperty("Asymmetric_Key_Cache_Enabled","no");
		property.setProperty("Symmetric_Key_Cache_Expiry","43200");
		property.setProperty("Symmetric_Key_Cache_AutoRefresh_Interval","0");
		property.setProperty("Local_Cipher_Cache_Expiry","0");
		property.setProperty("Local_Crypto_Provider","");
		property.setProperty("Persistent_Cache_Enabled","no");
		property.setProperty("Persistent_Cache_Expiry_Keys","43200");
		property.setProperty("Persistent_Cache_Directory","");
		property.setProperty("Persistent_Cache_Max_Size","100");
		property.setProperty("CA_File","");
		property.setProperty("Cert_File","");
		property.setProperty("Key_File","");
		property.setProperty("Passphrase","");
		property.setProperty("Credentials_Encrypted","no");
		property.setProperty("Passphrase_Encrypted","no");
		property.setProperty("Log_Level","WARN");
		property.setProperty("Log_File","");
		property.setProperty("com.ingrian.security.nae.Log_Rotation","Daily");
		property.setProperty("Log_GMT","no");
		property.setProperty("Log_Size_Limit","100k");
		property.setProperty("SysLog_IP","");
		property.setProperty("SysLog_Port","");
		property.setProperty("SysLog_Protocol","");
		property.setProperty("SysLog_SSLKeystore","");
		property.setProperty("SysLog_SSLKeystorePassword","");
		property.setProperty("Log_Config_Advanced","");
		property.setProperty("Key_non_exportable_policy","no");
		property.setProperty("Log_MaxBackupIndex","-1");
		property.setProperty("SNI_HostUsed","");
		property.setProperty("ReceiveBufferSize","64");


		System.setProperties(property);

	}



	@PostMapping(path = "/cadp/encrypt", consumes = "application/x-www-form-urlencoded")
	public ModelAndView doEncrypt(EncryptDecryptRequest encryptionRequest) {
		String plainValue = encryptionRequest.getPlainValue();
		logger.info("text input = " + plainValue);
		this.init();

		String CADP_KEY_NAME_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_KEY_NAME_DEFAULT);
		String CADP_DEFAULT_ALGO 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_DEFAULT_ALGO);
		String CADP_USERNAME_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_USERNAME_DEFAULT);
		String CADP_PASSWORD_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_PASSWORD_DEFAULT);

		NAESession session = NAESession.getSession (CADP_USERNAME_DEFAULT,CADP_PASSWORD_DEFAULT.toCharArray());
		Key key = NAEKey.getSecretKey(CADP_KEY_NAME_DEFAULT, session);

		Cipher cipher = null;
		ModelAndView mav = new ModelAndView("cadp_form");

		try {
			cipher = Cipher.getInstance(CADP_DEFAULT_ALGO, "IngrianProvider");

			//encrypt
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String encryptedResult = IngrianProvider.byteArray2Hex(cipher.doFinal(plainValue.getBytes()));
			logger.info("encrypted result " + encryptedResult);

			EncryptDecryptRequest res = new EncryptDecryptRequest();
			res.setPlainValue(plainValue);
			res.setConvertedValue(encryptedResult);
			mav.addObject("theresult",res);
			mav.addObject("algo", CADP_DEFAULT_ALGO);
			mav.addObject("key", CADP_KEY_NAME_DEFAULT);
			mav.addObject("username",CADP_USERNAME_DEFAULT);
			mav.addObject("password",CADP_PASSWORD_DEFAULT);

			return mav;

		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (NoSuchProviderException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}


	}



	@GetMapping(path = "/cadp/decrypt/{text}")
	public ModelAndView doDecrypt(@PathVariable("text") String text) {
		logger.info("cipher input = " + text);
		this.init();

		String CADP_KEY_NAME_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_KEY_NAME_DEFAULT);
		String CADP_DEFAULT_ALGO 		= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_DEFAULT_ALGO);
		String CADP_USERNAME_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_USERNAME_DEFAULT);
		String CADP_PASSWORD_DEFAULT 	= ConfigUtil.getConfigValue(configDao, ThalesStaticKey.CADP_PASSWORD_DEFAULT);

		NAESession session = NAESession.getSession (CADP_USERNAME_DEFAULT,CADP_PASSWORD_DEFAULT.toCharArray());
		Key key = NAEKey.getSecretKey(CADP_KEY_NAME_DEFAULT, session);

		Cipher cipher = null;
		ModelAndView mav = new ModelAndView("cadp_form_decrypted");

		try {
			cipher = Cipher.getInstance(CADP_DEFAULT_ALGO, "IngrianProvider");
			//encrypt
			cipher.init(Cipher.DECRYPT_MODE, key);
			String decryptedResult = IngrianProvider.toString(cipher.doFinal(IngrianProvider.hex2ByteArray(text)));
			logger.info("decrypted result :  " + decryptedResult);

			EncryptDecryptRequest res = new EncryptDecryptRequest();
			res.setEncryptionValue(text);
			res.setConvertedValue(decryptedResult);
			mav.addObject("theresult",res);
			mav.addObject("algo", CADP_DEFAULT_ALGO);
			mav.addObject("key", CADP_KEY_NAME_DEFAULT);
			mav.addObject("username",CADP_USERNAME_DEFAULT);
			mav.addObject("password",CADP_PASSWORD_DEFAULT);
			return mav;

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchProviderException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}


	}

}
