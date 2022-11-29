package org.arwani;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2TweetsSearchRecentResponse;
import org.arwani.jsonParser.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TweetSearch {
    private String next_token;
    // not the best practice though :)
    private String bearerToken = "AAAAAAAAAAAAAAAAAAAAAD1jiQEAAAAAvngEPMKNMkUwvRhjel1J1xAbVC8%3DV2foNAceiGNO3Aq0appUgXkMRLofuMzuoy6N0tCtQQPOC4rkF7";
    private TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearerToken);
    private TwitterApi apiInstance = new TwitterApi(credentials);
    // default for now
    private Set<String> tweetFields = new HashSet<>(Arrays.asList("author_id", "id", "created_at", "entities", "lang", "public_metrics"));
    private Integer maxResults = 10;
    private ArrayList<Tweet> tweets = new ArrayList<>();


    public ArrayList<Tweet> search(String query) {


        try {
            Get2TweetsSearchRecentResponse result = apiInstance.tweets().tweetsRecentSearch(query)
                    .maxResults(maxResults)
                    .tweetFields(tweetFields)
                    .execute();

            json jsonHandler = new json();
            jsonHandler.setJson(result.toJson());
            try {
                tweets.addAll(jsonHandler.getArrayListOfTweets());
            }catch (NullPointerException ex) {
                System.out.println("No tweets to add, because no results were found.");
            }
            next_token = jsonHandler.getNextToken();

        } catch (ApiException e) {
            System.err.println("Exception when calling TweetsApi#tweetsRecentSearch");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        if (!next_token.equals("null")) {
            do {
                try {
                    Get2TweetsSearchRecentResponse result = apiInstance.tweets().tweetsRecentSearch(query)
                            .maxResults(maxResults)
                            .tweetFields(tweetFields)
                            .nextToken(next_token)
                            .execute();

                    json jsonHandler = new json();
                    jsonHandler.setJson(result.toJson());
                    tweets.addAll(jsonHandler.getArrayListOfTweets());
                    next_token = jsonHandler.getNextToken();

                } catch (ApiException e) {
                    System.err.println("Exception when calling TweetsApi#tweetsRecentSearch");
                    System.err.println("Status code: " + e.getCode());
                    System.err.println("Reason: " + e.getResponseBody());
                    System.err.println("Response headers: " + e.getResponseHeaders());
                    e.printStackTrace();
                }

            } while (!next_token.equals("null"));
        }
        return tweets;


    }


}
