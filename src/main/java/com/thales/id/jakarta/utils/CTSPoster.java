package com.thales.id.jakarta.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thales.id.jakarta.entities.request.DeTokenizeRequest;
import com.thales.id.jakarta.entities.request.TokenizeRequest;
import com.thales.id.jakarta.entities.response.DeTokenizeResponse;
import com.thales.id.jakarta.entities.response.TokenizeResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CTSPoster {

  

    private static Logger logger = LogManager.getLogger(CTSPoster.class);

    private static String username;

    private static String password;

    private static String ctsTokenizeUrl;

    private static String ctsDeTokenizeUrl;

    private static String tokenTemplate;

    private static String tokenGroup;



    public static void tokenizeInit(String ctsTokenizeUrl, String username, String password, String tokenTemplate, String tokenGroup){
            CTSPoster.ctsTokenizeUrl = ctsTokenizeUrl;
            CTSPoster.username = username;
            CTSPoster.password = password;
            CTSPoster.tokenTemplate = tokenTemplate;
            CTSPoster.tokenGroup = tokenGroup;
    }

    public static void deTokenizeInit(String ctsDeTokenizeUrl, String username, String password, String tokenTemplate, String tokenGroup){
        CTSPoster.ctsDeTokenizeUrl = ctsDeTokenizeUrl;
        CTSPoster.username = username;
        CTSPoster.password = password;
        CTSPoster.tokenTemplate = tokenTemplate;
        CTSPoster.tokenGroup = tokenGroup;
    }




    private static SSLConnectionSocketFactory getSSLConnectionFactoryRegistry() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        return new SSLConnectionSocketFactory(sslContext,NoopHostnameVerifier.INSTANCE);
    }

    private static BasicHttpClientConnectionManager getBasicHTTPClientConnectionManager() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", getSSLConnectionFactoryRegistry())
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);


        return connectionManager;
    }




    public static TokenizeResponse tokenize(String data) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        if(CTSPoster.tokenTemplate == null || CTSPoster.tokenGroup == null || CTSPoster.username == null || CTSPoster.password == null)
            throw new RuntimeException("Please call init first!");


        TokenizeRequest request = new TokenizeRequest();
        request.setTokentemplate(CTSPoster.tokenTemplate);
        request.setTokengroup(CTSPoster.tokenGroup);
        request.setData(data);

        ObjectMapper mapper = new ObjectMapper();
        String JSON_STRING = null;
        try {
            JSON_STRING = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            logger.error(e);
            e.printStackTrace();
        }



        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(getSSLConnectionFactoryRegistry())
                .setConnectionManager(getBasicHTTPClientConnectionManager()).build();

        HttpPost httpPost = new HttpPost(CTSPoster.ctsTokenizeUrl);
        String encoding = Base64.getEncoder().encodeToString((CTSPoster.username + ":" + CTSPoster.password).getBytes());
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
        httpPost.setHeader("Content-type", "application/json");

        httpPost.setEntity(new StringEntity(JSON_STRING,ContentType.APPLICATION_JSON));

        HttpEntity h = httpPost.getEntity();

        CloseableHttpResponse response = client.execute(httpPost);


        String requestContent = EntityUtils.toString(h);
        logger.info("----------------- Tokenize ---------------------");
        logger.info("request  = " + requestContent);
        String responseString = new BasicResponseHandler().handleResponse(response);
        logger.info("response  = " + responseString);

        ObjectMapper mpr = new ObjectMapper();
        TokenizeResponse result = mpr.readValue(responseString, TokenizeResponse.class);
        return result;
    }

    public static DeTokenizeResponse deTokenize(String token) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        if(CTSPoster.tokenTemplate == null || CTSPoster.tokenGroup == null || CTSPoster.username == null || CTSPoster.password == null)
            throw new RuntimeException("Please call init first!");


        DeTokenizeRequest request = new DeTokenizeRequest();
        request.setTokentemplate(CTSPoster.tokenTemplate);
        request.setTokengroup(CTSPoster.tokenGroup);
        request.setToken(token);

        ObjectMapper mapper = new ObjectMapper();
        String JSON_STRING = null;
        try {
            JSON_STRING = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            logger.error(e);
            e.printStackTrace();
        }



        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(getSSLConnectionFactoryRegistry())
                .setConnectionManager(getBasicHTTPClientConnectionManager()).build();

        HttpPost httpPost = new HttpPost(CTSPoster.ctsDeTokenizeUrl);
        String encoding = Base64.getEncoder().encodeToString((CTSPoster.username + ":" + CTSPoster.password).getBytes());
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
        httpPost.setHeader("Content-type", "application/json");

        httpPost.setEntity(new StringEntity(JSON_STRING,ContentType.APPLICATION_JSON));
        HttpEntity h = httpPost.getEntity();
        CloseableHttpResponse response = client.execute(httpPost);
        String requestContent = EntityUtils.toString(h);
        logger.info("----------------- Detokenize ---------------------");
        logger.info("request  = " + requestContent);
        String responseString = new BasicResponseHandler().handleResponse(response);
        logger.info("response  = " + responseString);

        ObjectMapper mpr = new ObjectMapper();
        DeTokenizeResponse result = mpr.readValue(responseString, DeTokenizeResponse.class);
        return result;
    }














}
