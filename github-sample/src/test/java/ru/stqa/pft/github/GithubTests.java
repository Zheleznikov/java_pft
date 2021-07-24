package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GithubTests {
    Properties properties = new Properties();

    public GithubTests() throws IOException {
        properties.load(new FileReader("src/test/resources/git.properties"));
    }

    @Test
    public void testCommits() {
        Github github = new RtGithub(properties.getProperty("access"));
        Repo repo = github.repos().get(new Coordinates.Simple("Zheleznikov/test_mesto"));
        RepoCommits commits = repo.commits();

        commits.iterate((new ImmutableMap.Builder<String, String>().build())).forEach(commit -> {
            try {
                System.out.println(new RepoCommit.Smart(commit).message());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}


