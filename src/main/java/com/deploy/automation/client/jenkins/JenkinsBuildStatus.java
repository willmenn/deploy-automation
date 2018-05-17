package com.deploy.automation.client.jenkins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JenkinsBuildStatus {

    private List<JenkinsJob> jobs;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class JenkinsJob {
        private String name;
        private String url;
        private String color;
    }
}
