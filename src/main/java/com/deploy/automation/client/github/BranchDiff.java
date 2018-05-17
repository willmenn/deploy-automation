package com.deploy.automation.client.github;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
public class BranchDiff {

    private BaseCommit base_commit;
    private BaseCommit merge_base_commit;
    private List<BaseCommit> commits;

    public Set<String> getStories(String storyRegex) {
        return this.commits.stream()
                .filter(commit -> commit.getCommit().getMessage().matches(storyRegex))
                .map(commit -> {
                    String message = commit.getCommit().getMessage();
                    return getStoryFromMessage(message);
                })
                .collect(Collectors.toSet());
    }

    private static String getStoryFromMessage(String message) {
        if (message.contains(":")) {
            return message.split(":")[0];
        } else {
            return message.split(" ")[0];
        }
    }

    @Data
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class BaseCommit {
        private String sha;
        private CommitDiff commit;
    }

    @Data
    @AllArgsConstructor
    public static class CommitDiff {
        private String message;
    }
}
