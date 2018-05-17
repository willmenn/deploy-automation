package com.deploy.automation.client.jira;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JiraStories {

    private Integer total;
    private List<JiraIssue> issues;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JiraIssue {
        private String key;
    }
}
