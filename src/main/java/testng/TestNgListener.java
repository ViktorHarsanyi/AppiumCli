package testng;


import annotations.ControlNotation;
import com.atlassian.jira.rest.client.api.domain.Issue;
import org.testng.*;
import utils.JiraClient;

public class TestNgListener implements ISuiteListener, ITestListener {

    private String[] resultCodes = new String[]{"Uncaught Error","Pass","Fail","Skip"};
    @Override
    public void onStart(ISuite suite) {
        System.out.println("Suite START: "+suite.getOutputDirectory());
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Suite ["+suite.getName()+createTestID(suite)+"] FINISHED: \n======================\n");
        String res = "null";
        for (IInvokedMethod invokedMethod : suite.getAllInvokedMethods()) {
            res = "|" + invokedMethod.getTestMethod().getMethodName() + ": " + resultCodes[invokedMethod.getTestResult().getStatus()];
            System.out.println(res);

        }
        ControlNotation cntrl = suite.getResults().values().getClass().getAnnotation(ControlNotation.class);
        if(cntrl.canGoToJira()){
            //TODO fill in correct params
            initJiraClient("username","password","urlToAtlassian",cntrl.jiraKey(),res,1L);
        }
    }

    @Override
    public void onTestFailure(ITestResult result){
        System.out.println(result.getMethod().getMethodName());
        result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(ControlNotation.class);
    }

    private void initJiraClient(String username,String password,String jiraUrl,String key,String summary,Long typeOfIssue){
        JiraClient jiraClient = new JiraClient(username, password, jiraUrl);

        final String issueKey = jiraClient.createIssue(key,typeOfIssue,summary);
        Issue issue = jiraClient.getIssue(issueKey);
        System.out.println(issue.getDescription()+" uploaded to "+jiraUrl);
    }

    private String createTestID(ISuite suite){
        return suite.getName()+"_"+suite.getHost();
    }
}