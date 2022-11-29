package org.arwani;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class SearchTask extends Task<ArrayList <Tweet>> {

    @Override
    protected ArrayList<Tweet> call() throws Exception {
        TweetSearch tweetSearch = new TweetSearch();
         return tweetSearch.search(QueryBuilderController.getSearchQuery());
    }
}
