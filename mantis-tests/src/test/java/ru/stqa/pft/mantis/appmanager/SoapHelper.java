package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private final ApplicationManager app;


    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        String username = app.getProperty("web.adminLogin");
        String password = app.getProperty("web.pass");
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(username, password);
        return Arrays.asList(projects).stream().map(p -> new Project().withName(p.getName()).withId(p.getId().intValue())).collect(Collectors.toSet());
    }


    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        String username = app.getProperty("web.adminLogin");
        String password = app.getProperty("web.pass");

        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories(username, password, BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger id = mc.mc_issue_add(username, password, issueData);
        IssueData createdIssueData = mc.mc_issue_get(username, password, id);
        boolean isOpen = !createdIssueData.getStatus().getName().equals("closed");

        return new Issue()
                .withId(createdIssueData.getId().intValue())
                .withDescription(createdIssueData.getDescription())
                .withSummary(createdIssueData.getSummary())
                .withProject(new Project()
                        .withId(createdIssueData.getProject().getId().intValue())
                        .withName(createdIssueData.getProject().getName()))
                .withOpen(isOpen);

    }

    public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        String username = app.getProperty("web.adminLogin");
        String password = app.getProperty("web.pass");

        MantisConnectPortType mc = getMantisConnect();
        IssueData issueData = mc.mc_issue_get(username, password, BigInteger.valueOf(issueId));
        boolean isOpen = !issueData.getStatus().getName().equals("closed");
        return new Issue()
                .withId(issueData.getId().intValue())
                .withDescription(issueData.getDescription())
                .withSummary(issueData.getSummary())
                .withProject(new Project()
                        .withId(issueData.getProject().getId().intValue())
                        .withName(issueData.getProject().getName()))
                .withOpen(isOpen);
    }


    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        MantisConnectPortType mc = new MantisConnectLocator().
                getMantisConnectPort(new URL(app.getProperty("soap.baseUrl")));
        return mc;
    }
}
