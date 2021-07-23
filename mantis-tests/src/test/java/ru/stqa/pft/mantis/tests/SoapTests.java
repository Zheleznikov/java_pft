package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {
    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();

        Issue issue = new Issue()
                .withSummary("test issue")
                .withDescription("test issue desc")
                .withProject(projects.iterator().next());

        Issue created = app.soap().addIssue(issue);
        assertEquals(created.getSummary(), issue.getSummary());
    }


    @Test
    public void findOpenIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue()
                .withSummary("test issue2")
                .withDescription("test issue desc2")
                .withProject(projects.iterator().next());
        Issue createdIssue = app.soap().addIssue(issue);
        skipIfNotFixed(createdIssue.getId());
    }

}
