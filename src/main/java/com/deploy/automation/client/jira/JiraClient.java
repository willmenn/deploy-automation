package com.deploy.automation.client.jira;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.http.HttpMethod.GET;

@Component
@Slf4j
public class JiraClient {

    private RestTemplate restTemplate;
    private Environment environment;

    @Autowired
    public JiraClient(@Qualifier(value = "jiraRestTemplate") RestTemplate restTemplate,
                      Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public JiraStories fetchStoriesInDone(String iteration) {
        String url = jqlFindDoneStoriesInASpecificIteration(environment.getProperty("JIRA_JQL_SEARCH_URL") + environment.getProperty("JIRA_JQL"),
                iteration);
        URI uri = UriComponentsBuilder.fromUriString(url).build().encode().toUri();
        HttpEntity httpEntity = buildJiraHeader();
        try {
            return restTemplate
                    .exchange(
                            uri,
                            GET,
                            httpEntity,
                            JiraStories.class
                    ).getBody();
        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString());
            throw e;
        }
    }

    private HttpEntity buildJiraHeader() {
        HttpHeaders header = new HttpHeaders();
        header.put("Content-type", newArrayList("application/json"));

        return new HttpEntity(header);
    }

    private static String jqlFindDoneStoriesInASpecificIteration(String baseUrl, String iteration) {
        String jql = String.format(baseUrl, iteration);

        return baseUrl + jql;
    }
}
