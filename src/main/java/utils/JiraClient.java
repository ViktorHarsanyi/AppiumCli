package utils;


import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.net.URI;

public class JiraClient {

    private String userName,password,jiraUrl;
    private JiraRestClient restClient;

    public JiraClient(String userName, String password, String jiraUrl){
        this.userName=userName;
        this.password=password;
        this.jiraUrl=jiraUrl;
        restClient = getRestClient();
    }

    private JiraRestClient getRestClient(){
        return new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(getJiraUri(),this.userName,this.password);
    }

    private URI getJiraUri(){
        return URI.create(this.jiraUrl);
    }

    public String createIssue(String projectKey, Long issueType, String issueSummary){
        IssueRestClient issueRestClient = restClient.getIssueClient();
        IssueInput newIssue = new IssueInputBuilder(
                projectKey,issueType,issueSummary
        ).build();
        return issueRestClient.createIssue(newIssue).claim().getKey();
    }

    public void updateIssueDescription(String issueKey, String newDescription){
        IssueInput input = new IssueInputBuilder()
                .setDescription(newDescription)
                .build();
        restClient.getIssueClient()
                .updateIssue(issueKey,input)
                .claim();
    }

    public Issue getIssue(String issueKey) {
        return restClient.getIssueClient()
                .getIssue(issueKey)
                .claim();
    }

    public void printIssue(){

    }


}
