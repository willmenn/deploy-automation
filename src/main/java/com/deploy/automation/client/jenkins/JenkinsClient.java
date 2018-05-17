package com.deploy.automation.client.jenkins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JenkinsClient {

    private RestTemplate template;
    private Environment environment;

    @Autowired
    public JenkinsClient(@Qualifier("jenkinsRestTemplate") RestTemplate template,
                         Environment environment) {
        this.template = template;
        this.environment = environment;
    }

    public JenkinsBuildStatus runBuild() {
        String url = environment.getProperty("JENKINS_URL") + "/api/json?pretty=true";
        return template.getForEntity(url, JenkinsBuildStatus.class).getBody();
    }
}
