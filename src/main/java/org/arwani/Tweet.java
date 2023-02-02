package org.arwani;

/**
 * The Tweet class represents a Tweet with all its needed attributes with their setters and getters.
 *
 */
public class Tweet {

    private String author_id;
    private String created_at;
    private String id;
    private String lang;
    private String like_count;
    private String quote_count;
    private String reply_count;
    private String retweet_count;
    private String text;

//    public Tweet(String author_id, String created_at, String id, String lang, String like_count, String quote_count, String reply_count, String retweet_count, String text) {
//        this.author_id = author_id;
//        this.created_at = created_at;
//        this.id = id;
//        this.lang = lang;
//        this.like_count = like_count;
//        this.quote_count = quote_count;
//        this.reply_count = reply_count;
//        this.retweet_count = retweet_count;
//        this.text = text;
//    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getId() {
        return id;
    }

    public String getLang() {
        return lang;
    }

    public String getLike_count() {
        return like_count;
    }

    public String getQuote_count() {
        return quote_count;
    }

    public String getReply_count() {
        return reply_count;
    }

    public String getRetweet_count() {
        return retweet_count;
    }

    public String getText() {
        return text;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setLike_count(String like) {
        this.like_count = like;
    }

    public void setQuote_count(String quote_count) {
        this.quote_count = quote_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public void setRetweet_count(String retweet_count) {
        this.retweet_count = retweet_count;
    }

    public void setText(String text) {
        this.text = text;
    }
}

