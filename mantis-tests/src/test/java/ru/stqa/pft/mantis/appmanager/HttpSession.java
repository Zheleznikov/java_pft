package ru.stqa.pft.mantis.appmanager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private ApplicationManager app;
    private CloseableHttpClient httpClient;
    private final String htmlPageFragment = "<a href=\"/mantisbt-2.25.2/account_page.php\">%s</a>";

    HttpSession(ApplicationManager app) {
        this.app = app;
        httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(String username, String password) throws IOException {
        String reqPost = app.getProperty("web.baseUrl") + "/login.php";
        HttpPost post = new HttpPost(reqPost);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = httpClient.execute(post);
        String body = geTextFrom(res);
        res.close();
        return body.contains(String.format(htmlPageFragment, username));
    }

    public boolean isLoggedInAs(String username) throws IOException {
        HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
        CloseableHttpResponse response = httpClient.execute(get);
        String body = geTextFrom(response);
        return body.contains(String.format(htmlPageFragment, username));
    }

    private String geTextFrom(CloseableHttpResponse res) throws IOException {
        try (res) {
            return EntityUtils.toString(res.getEntity());
        }
    }


}
