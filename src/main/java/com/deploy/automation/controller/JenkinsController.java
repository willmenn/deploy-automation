package com.deploy.automation.controller;

import com.deploy.automation.client.jenkins.JenkinsBuildStatus.JenkinsJob;
import com.deploy.automation.service.JenkinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class JenkinsController {

    private JenkinsService service;

    @Autowired
    public JenkinsController(JenkinsService service) {
        this.service = service;
    }

    @RequestMapping(value = "/jenkins/build/release/status", method = GET)
    protected List<JenkinsJob> startBuild() {
        return service.getAndFilterJobs();
    }
}
