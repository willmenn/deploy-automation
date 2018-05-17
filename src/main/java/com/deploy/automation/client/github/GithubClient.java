package com.deploy.automation.client.github;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
@Slf4j
public class GithubClient {

    private static final String GITHUB_TOKEN = "GITHUB_TOKEN";
    private static final String GITHUB_USER = "GITHUB_USER";
    private RestTemplate restTemplate;
    private Environment environment;

    @Autowired
    private GithubClient(@Qualifier(value = "githubRestTemplate") RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public List<Branch> fetchAllBranchs(String repo){
        String url=environment.getProperty("GITHUB_API_URL")+repo+"/branches";
        HttpEntity httpEntity = buildGithubHeader();
        return restTemplate
                .exchange(
                        url,
                        GET,
                        httpEntity,
                        new ParameterizedTypeReference<List<Branch>>() {
                        }).getBody();
    }

    public BranchDiff diffFromBranch(String repo, String baseBranch, String branchToCompareWith){
        String url=environment.getProperty("GITHUB_API_URL")+"/api/v3/repos/plan" +
                "/"+repo+"/compare/"+baseBranch+"..."+ branchToCompareWith;
        HttpEntity httpEntity = buildGithubHeader();
        return restTemplate
                .exchange(
                        url,
                        GET,
                        httpEntity,
                        new ParameterizedTypeReference<BranchDiff>() {
                        }).getBody();
    }

    private HttpEntity buildGithubHeader() {
        HttpHeaders header = new HttpHeaders();
        List<String> headerValues = new ArrayList<>();
        headerValues.add(environment.getProperty(GITHUB_TOKEN));
        header.put(environment.getProperty(GITHUB_USER), headerValues);
        return new HttpEntity(header);
    }

}
