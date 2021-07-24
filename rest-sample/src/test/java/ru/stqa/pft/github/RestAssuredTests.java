package ru.stqa.pft.github;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase {

    @BeforeClass
    public void init() {
        authentication = basic(API_KEY, "");
    }

    @Test
    public void testCreateIssue() {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue()
                .withSubject("Test issue")
                .withDescription("New test issue");
        int newIssueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(newIssueId));
        assertEquals(newIssues, oldIssues);
    }


    private Set<Issue> getIssues() {
        String json2 = given().get(HOST + "issues.json").asString();
        JsonElement issues = JsonParser.parseString(json2).getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }


    private int createIssue(Issue newIssue) {
        String json2 = given()
                .param("subject", newIssue.getSubject())
                .param("description", newIssue.getDescription())
                .post(HOST + "issues.json")
                .asString();
        return JsonParser.parseString(json2).getAsJsonObject().get("issue_id").getAsInt();
    }



    @Test
    public void testIsIssueOpen() {
        Issue newIssue = new Issue()
                .withSubject("Test issue")
                .withDescription("New test issue");
        int id = createIssue(newIssue);

        isIssueOpen(id);
//        skipIfNotFixed(id);
    }

}
