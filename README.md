# deploy-automation

To run the app it needs to have this two variables:
```
$ GITHUB_API_URL={github_api_url}
$ GITHUB_USER={your user}
$ GITHUB_TOKEN={your github token}
$ GITHUB_STORY_REGEX={this is the regex to find the story number in the commit message}
$ GITHUB_RELEASE_BRANCH={this is the prefix name of releases branches to show in the UI}
$ GITHUB_PROJECTS_NAMES={all the project names separeted by comma}
$ JIRA_USER={your jira user}
$ JIRA_PASS={your jira password}
$ JIRA_JQL_SEARCH_URL={your jira api jql serch: /api/search?jql=}
$ JIRA_JQL={jql with %s in the place to add the iteration number}
$ JENKINS_URL={jenkins url}
$ JENKINS_USER={jenkins user}
$ JENKINS_PASS={jenkins password}
$ JENKINS_JOB_NAME_REGEX={this is the regex name to only look into a small set of jobs, like the release jobs}

```
