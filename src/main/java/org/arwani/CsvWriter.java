package org.arwani;

import java.io.*;

import static org.arwani.TweetSearchResultsController.twee;

public class CsvWriter {

public static void writeCsv(String path) throws IOException {
    Writer writer = null;
    try {
        File file = new File(path+"/tweets.csv.");
        writer = new BufferedWriter(new FileWriter(file));
        for (Tweet tweet : twee) {

            String row = tweet.getAuthor_id() + "," + tweet.getCreated_at() + "," + tweet.getId() + "," + tweet.getLang() +
                    "," + tweet.getLike_count() + "," + tweet.getQuote_count() + "," + tweet.getReply_count()
                    + "," + tweet.getRetweet_count() + "," + tweet.getText() + "\n";



            writer.write(row);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    finally {

        assert writer != null;
        writer.flush();
        writer.close();
    }

}
}
