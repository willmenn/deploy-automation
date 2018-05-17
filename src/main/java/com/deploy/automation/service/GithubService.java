package com.deploy.automation.service;

import com.deploy.automation.client.github.GithubClient;
import com.deploy.automation.client.github.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@Service
public class GithubService {

    private GithubClient client;
    private Environment environment;

    @Autowired
    public GithubService(GithubClient client, Environment environment) {
        this.client = client;
        this.environment = environment;
    }

    public List<String> getOnlyReleaseBranches(String repo) {
        List<Branch> branches = client.fetchAllBranchs(repo);

        return branches.stream()
                .filter(branch -> branch.getName().contains(environment.getProperty("GITHUB_RELEASE_BRANCH")))
                .sorted(Comparator.comparing(Branch::getName))
                .map(Branch::getName)
                .collect(toList());
    }

    public List<String> getBranchesExceptRelease(String repo) {
        List<Branch> branches = client.fetchAllBranchs(repo);

        return branches.stream()
                .filter(branch -> !branch.getName().contains(environment.getProperty("GITHUB_RELEASE_BRANCH")))
                .sorted(Comparator.comparing(Branch::getName))
                .map(Branch::getName)
                .collect(toList());
    }

    public List<String> getRepositories() {
        return newArrayList(environment.getProperty("GITHUB_PROJECTS_NAMES").split(","));
    }
}
