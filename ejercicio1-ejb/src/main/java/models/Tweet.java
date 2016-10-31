/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author sergio
 */
@Entity
@Table(name="tweets")
@SqlResultSetMapping(
    name = "TweetsBySentimentResult",
    classes = {
        @ConstructorResult(
            targetClass = TweetsBySentiment.class,
            columns = {
                @ColumnResult(name = "sentiment"),
                @ColumnResult(name = "tweets")
            }
        )
    } 
)
@NamedNativeQuery(
    name = "TweetsBySentiment",
    query = "SELECT sentiment, COUNT(*) AS tweets from tweets A JOIN topics B ON(A.id_topic=B.id) WHERE LOWER(B.name) = ?1 GROUP BY A.SENTIMENT",
    resultSetMapping = "TweetsBySentimentResult"
)
@NamedQueries(
        @NamedQuery(name="Tweets.byTopic", query="SELECT t FROM Tweet t WHERE t.topic.name = :topic")
)
public class Tweet implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createAt;
    private Double lat;
    private Double lon;
    private String lang;
    private Integer reTweetCount;
    private String text;
    private Sentiment sentiment;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_TOPIC")
    private Topic topic;

    public Long getId() {
        return id;
    }
    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getReTweetCount() {
        return reTweetCount;
    }

    public void setReTweetCount(Integer reTweetCount) {
        this.reTweetCount = reTweetCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
        topic.addTweet(this);
    }
}
