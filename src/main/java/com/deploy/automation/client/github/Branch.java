package com.deploy.automation.client.github;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Branch {
    private Commit commit;
    private String name;
}
