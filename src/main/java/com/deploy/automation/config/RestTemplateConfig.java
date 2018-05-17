package com.deploy.automation.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @Qualifier(value = "jiraRestTemplate")
    public RestTemplate createRestTemplateForJira(Environment environment) throws GeneralSecurityException {
        HttpClient client = getHttpClientWithSSL();

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));

        ClientHttpRequestInterceptor basicAuthInterceptor =
                new BasicAuthorizationInterceptor(environment.getProperty("JIRA_USER"),
                        environment.getProperty("JIRA_PASS"));

        restTemplate.getInterceptors().add(basicAuthInterceptor);
        return restTemplate;
    }

    @Bean
    @Qualifier(value = "jenkinsRestTemplate")
    public RestTemplate createRestTemplateForJenkins(Environment environment) throws GeneralSecurityException {
        ClientHttpRequestInterceptor basicAuthInterceptor =
                new BasicAuthorizationInterceptor(environment.getProperty("JENKINS_USER"),
                        environment.getProperty("JENKINS_PASS"));

        CloseableHttpClient client = HttpClients.custom()
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setSSLSocketFactory(new SSLConnectionSocketFactory(getSslContext(), new NoopHostnameVerifier()))
                .build();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
        restTemplate.getInterceptors().add(basicAuthInterceptor);
        return restTemplate;
    }

    @Bean
    @Qualifier(value = "githubRestTemplate")
    public RestTemplate createRestTemplateForGithub() throws GeneralSecurityException {
        HttpClient client = getHttpClientWithSSL();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
    }

    private HttpClient getHttpClientWithSSL() throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException {
        return HttpClientBuilder
                .create()
                .useSystemProperties()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(getSslContext()))
                .build();
    }

    private SSLContext getSslContext() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        return new SSLContextBuilder()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
    }
}
