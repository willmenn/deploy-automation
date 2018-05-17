package com.deploy.automation.service;

import com.deploy.automation.client.jenkins.JenkinsClient;
import com.deploy.automation.client.jenkins.JenkinsBuildStatus.JenkinsJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class JenkinsService {

    private static final String BLUE_JENKINS_COLOR = "blue";
    private JenkinsClient jenkinsClient;
    private Environment environment;

    @Autowired
    public JenkinsService(JenkinsClient jenkinsClient, Environment environment) {
        this.jenkinsClient = jenkinsClient;
        this.environment = environment;
    }

    public List<JenkinsJob> getAndFilterJobs() {
        return jenkinsClient.runBuild().getJobs()
                .stream()
                .filter(job -> job.getName().contains(environment.getProperty("JENKINS_JOB_NAME_REGEX")))
                .filter(job -> job.getColor().equals(BLUE_JENKINS_COLOR))
                .collect(toList());
    }
}
