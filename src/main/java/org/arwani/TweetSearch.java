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

/**
 * The class TweetSearch initiates and utilizes the Twitter API V2 to look up tweets from the past 7-days.
 * The class makes use of Twitter SDK.
 */
public class TweetSearch {
    private String next_token;
    // not the best practice though :)
    private final String bearerToken = "AAAAAAAAAAAAAAAAAAAAAD1jiQEAAAAAvngEPMKNMkUwvRhjel1J1xAbVC8%3DV2foNAceiGNO3Aq0appUgXkMRLofuMzuoy6N0tCtQQPOC4rkF7";
    private final TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearerToken);
    private final TwitterApi apiInstance = new TwitterApi(credentials);
    // default for now
    private final Set<String> tweetFields = new HashSet<>(Arrays.asList("author_id", "id", "created_at", "entities", "lang", "public_metrics"));
    private final Integer maxResults = 10;
    // All search results will be stored in this Arraylist
    private ArrayList<Tweet> tweets = new ArrayList<>();

    /**
     *
     * @param query This is a string that represents the query search that will be used for Tweet search.
     * The method will keep requesting tweets as long as the next-token is received in the response
     * @return An arraylist of Tweets
     */
    public ArrayList<Tweet> search(String query) {

        // Launching the first request
        try {
            Get2TweetsSearchRecentResponse result = apiInstance.tweets().tweetsRecentSearch(query)
                    .maxResults(maxResults)
                    .tweetFields(tweetFields)
                    .execute();
            // Processing results
            json jsonHandler = new json();
            jsonHandler.setJson(result.toJson());
            try {
                tweets.addAll(jsonHandler.getArrayListOfTweets());
            } catch (NullPointerException ex) {
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
        //if nextToken is detected carry on receiving Tweets until no nextToken is provided
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
