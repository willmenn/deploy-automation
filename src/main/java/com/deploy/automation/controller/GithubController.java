package com.deploy.automation.controller;

import com.deploy.automation.client.github.GithubClient;
import com.deploy.automation.client.github.BranchDiff;
import com.deploy.automation.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/github/repositories")
public class GithubController {

    private GithubClient client;
    private GithubService service;
    private Environment environment;

    @Autowired
    private GithubController(GithubClient client, GithubService service, Environment environment) {
        this.client = client;
        this.service = service;
        this.environment = environment;
    }

    @RequestMapping(method = GET)
    public List<String> fetchAllRepositories() {
        return service.getRepositories();
    }

    @RequestMapping(value = "/{repo}/branches", method = GET)
    public List<String> fetchAllBranches(@PathVariable("repo") String repo) {
        return service.getBranchesExceptRelease(repo);
    }

    @RequestMapping(value = "/{repo}/branches/release", method = GET)
    public List<String> fetchAllReleaseBranches(@PathVariable("repo") String repo) {
        return service.getOnlyReleaseBranches(repo);
    }

    @RequestMapping(value = "/{repo}/branches/diff/{baseBranch}/{branch}", method = GET)
    public Set<String> diff(@PathVariable("repo") String repo,
                            @PathVariable("baseBranch") String basebranch,
                            @PathVariable("branch") String branch) {
        return client.diffFromBranch(repo, basebranch, branch)
                .getStories(environment.getProperty("GITHUB_STORY_REGEX"));
    }
}
