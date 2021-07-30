package ru.stqa.pft.rest;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.testng.SkipException;

import static io.restassured.RestAssured.given;

public class TestBase {
    protected final String HOST = "https://bugify.stqa.ru/api/";
    protected final String API_KEY = "288f44776e7bec4bf44fdfeb1e646490";
    private String issueFromApi = null;

    public boolean isIssueOpen(int id) {
        return getIssueFromApi(id)
                .processIssueAsClass()
                .getStateName()
                .equals("Open");
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }


    private TestBase getIssueFromApi(int id) {
        issueFromApi = given()
                .get(HOST + "issues/" + id + ".json")
                .asString();
        return this;
    }


    private Issue processIssueAsClass() {
        JsonElement issue = JsonParser.parseString(issueFromApi)
                .getAsJsonObject()
                .getAsJsonArray("issues")
                .get(0);
        return new Gson().fromJson(issue, Issue.class);
    }


}
