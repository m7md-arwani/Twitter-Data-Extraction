package org.arwani.jsonParser;


import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.arwani.Tweet;

import java.util.ArrayList;
// the class handles one request. Get the results then reuse it in the next request.
public class json {

    private String json;
    private JSONObject jsonObject;



    public void setJson(String json)  {
        this.json = json;
        jsonObject = new JSONObject(json);

    }




    public String getNextToken() {

        JSONObject meta = jsonObject.getJSONObject("meta");
        try {
            return (String) meta.get("next_token");
        } catch (JSONException ex) {
            return "null";
        }

    }

    private ArrayList <JSONObject> getArrayListOfJsonTweets()  {
       try {
           JSONArray tweetsArray =  jsonObject.getJSONArray("data");
           ArrayList<JSONObject> jsonTweets = new ArrayList<>();
           for (int i = 0; i < tweetsArray.length(); i++) {
               jsonTweets.add(tweetsArray.getJSONObject(i));
           }
           return jsonTweets;
       }catch (JSONException ex){
           System.out.println("No results were found!");
           Alert noResults = new Alert(Alert.AlertType.INFORMATION);
           noResults.setHeaderText("No matching results were found");
           noResults.setContentText("We are sorry, no matching results were found.\nPlease consider trying again with different key words");
           noResults.show();
       }
       return null;
    }

    private String getAuthor_id(JSONObject tweetJson) {
        return  (String) tweetJson.get("author_id");

    }

    private String getCreated_at(JSONObject tweetJson) {
        return  (String) tweetJson.get("created_at");

    }

    private String getId(JSONObject tweetJson) {
        return  (String) tweetJson.get("id");

    }

    private String getLanguage(JSONObject tweetJson) {
        return  (String) tweetJson.get("lang");

    }

    private String getText(JSONObject tweetJson) {
        return  (String) tweetJson.get("text");

    }

    private int getLikes(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return  (int) public_metrics.get("like_count");


    }

    private int getRetweets(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return  (int) public_metrics.get("retweet_count");


    }
    private int getQuotes(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return  (int) public_metrics.get("quote_count");


    }

    private int getReplies(JSONObject tweetJson) {
        JSONObject public_metrics = (JSONObject) tweetJson.get("public_metrics");
        return  (int) public_metrics.get("reply_count");


    }



    public ArrayList<Tweet> getArrayListOfTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();
        ArrayList<JSONObject> tweetsINJson = getArrayListOfJsonTweets();
        if (tweetsINJson == null) {
            return null;
        }
        for (int i =0 ; i < tweetsINJson.size(); i++) {
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
