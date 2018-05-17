package com.deploy.automation.controller;

import com.deploy.automation.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class JiraController {

    private JiraService service;

    @Autowired
    public JiraController(JiraService service) {
        this.service = service;
    }

    @RequestMapping(value = "/jira/iterations/{iteration}/done", method = GET)
    public List<String> findStoriesInDone(@PathVariable("iteration") String iteration) throws MalformedURLException {
        return service.fetchJiraStories(iteration);
    }
}
