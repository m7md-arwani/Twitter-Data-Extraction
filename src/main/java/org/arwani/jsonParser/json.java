package org.arwani.jsonParser;


import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.arwani.Tweet;

import java.util.ArrayList;

/**
 * json is a public class responsible for extracting Tweets data from JSON responses provided by the endpoint.
 * The class takes the request in JSON format and prepares an arraylist of type Tweet.
 * A json object deals with one response at a time, and by changing the json string using #setJson(String) the object will be ready for dealing with a new response.
 */

// the class handles one request. Get the results then reuse it in the next request.
public class json {
    private String json;
    private JSONObject jsonObject;

    /**
     * Sets the json that the class will deal with later.
     *
     * @param json the string that represents one response.
     */
    public void setJson(String json) {
        this.json = json;
        jsonObject = new JSONObject(json);

    }

    /**
     * Extracts the NextToken from the response.
     * The extracted token is used to get the next response.
     * @return returns the string of the token, and if not found it returns "null"
     */
    public String getNextToken() {

        JSONObject meta = jsonObject.getJSONObject("meta");
        try {
            return (String) meta.get("next_token");
        } catch (JSONException ex) {
            return "null";
        }

    }

    /**
     * In the response of Tweets request, the tweets are in an array called "data", and it contains Tweets in JSON format.
     * This function takes the tweets and puts them in array of type JSONObject.
     * The method also fires a GUI alert that appears to the user when no results are found.
     * @return arraylist that has tweets in Json format. If not found it returns null
     */
    private ArrayList<JSONObject> getArrayListOfJsonTweets() {
        try {
            JSONArray tweetsArray = jsonObject.getJSONArray("data");
            ArrayList<JSONObject> jsonTweets = new ArrayList<>();
            for (int i = 0; i < tweetsArray.length(); i++) {
                jsonTweets.add(tweetsArray.getJSONObject(i));
            }
            return jsonTweets;
        } catch (JSONException ex) {
            System.out.println("No results were found!");
            Alert noResults = new Alert(Alert.AlertType.INFORMATION);
            noResults.setHeaderText("No matching results were found");
            noResults.setContentText("We are sorry, no matching results were found.\nPlease consider trying again with different key words");
            noResults.show();
        }
        return null;
    }

    // Tweet's attribute getters.
    // These functions extract data from a JSON Tweet
    private String getAuthor_id(JSONObject tweetJson) {
        return (String) tweetJson.get("author_id");

    }

    private String getCreated_at(JSONObject tweetJson) {
        return (String) tweetJson.get("created_at");

    }

    private String getId(JSONObject tweetJson) {
        return (String) tweetJson.get("id");

    }

    private String getLanguage(JSONObject tweetJson) {
        return (String) tweetJson.get("lang");

    }

    private String getText(JSONObject tweetJson) {
        return (String) tweetJson.get("text");

    }

    private int getLikes(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return (int) public_metrics.get("like_count");


    }

    private int getRetweets(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return (int) public_metrics.get("retweet_count");


    }

    private int getQuotes(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return (int) public_metrics.get("quote_count");


    }

    private int getReplies(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return (int) public_metrics.get("reply_count");


    }

    /**
     * Uses the #getArrayListOfJsonTweets function.
     * Iterates over the Json array.
     * Extracts the needed attributes and set them into a Tweet object.
     * Encapsulates the Tweet objects into an arrayList of the same type.
     * @return ArrayList of Tweet objects or null if the Json array of Tweets is null.
     */
    public ArrayList<Tweet> getArrayListOfTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();
        ArrayList<JSONObject> tweetsINJson = getArrayListOfJsonTweets();
        if (tweetsINJson == null) {
            return null;
        }
        for (int i = 0; i < tweetsINJson.size(); i++) {
            JSONObject currentTweet = tweetsINJson.get(i);
            Tweet tweet = new Tweet();
            tweet.setAuthor_id(getAuthor_id(currentTweet));
            tweet.setCreated_at(getCreated_at(currentTweet));
            tweet.setId(getId(currentTweet));
            tweet.setLang(getLanguage(currentTweet));
            tweet.setLike_count(Integer.toString(getLikes(currentTweet)));
            tweet.setQuote_count(Integer.toString(getQuotes(currentTweet)));
            tweet.setReply_count(Integer.toString(getReplies(currentTweet)));
            tweet.setRetweet_count(Integer.toString(getRetweets(currentTweet)));
            tweet.setText(getText(currentTweet));
            tweets.add(tweet);
        }
        return tweets;
    }


}
