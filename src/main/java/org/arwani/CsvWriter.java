package org.arwani;

import java.io.*;

import static org.arwani.TweetSearchResultsController.twee;

/**
 * This class writes a CSV file that is similar to what the user can see inside the program.
 * CSV stands for comma separated values. That's why the values are delimited by commas.
 */
public class CsvWriter {

    /**
     * Opens a file named "tweets.csv" in the given path.
     * Writes the attributes on the file delimited by commas
     * @param path The location of the resultant file
     * @throws IOException Raised when any error occurs during the writing process
     */
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
