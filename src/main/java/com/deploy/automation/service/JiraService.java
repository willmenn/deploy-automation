package com.deploy.automation.service;

import com.deploy.automation.client.jira.JiraStories;
import com.deploy.automation.client.jira.JiraClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JiraService {

    private JiraClient client;

    @Autowired
    public JiraService(JiraClient client) {
        this.client = client;
    }

    public List<String> fetchJiraStories(String iteration){
        return client.fetchStoriesInDone(iteration).getIssues()
                .stream()
                .map(JiraStories.JiraIssue::getKey)
                .collect(Collectors.toList());
    }
}
