package org.arwani;

import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Because the JavaFX's app main thread is busy handling the GUI, firing up a heavy task such as Tweet search can easily make the app crash.
 * For that, Tasks are supported by JavaFX to allocate a special thread for the needed task.
 *
 */
public class SearchTask extends Task<ArrayList <Tweet>> {

    @Override
    protected ArrayList<Tweet> call() throws Exception {
        TweetSearch tweetSearch = new TweetSearch();
         return tweetSearch.search(QueryBuilderController.getSearchQuery());
    }
}
