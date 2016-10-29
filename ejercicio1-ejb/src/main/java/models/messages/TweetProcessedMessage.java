/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.messages;

import java.io.Serializable;
import utils.IVisitable;
import visitor.IProcessMessageVisitor;


/**
 *
 * @author sergio
 */
public class TweetProcessedMessage implements Serializable, IVisitable<IProcessMessageVisitor>{
    private String topic;
    private String sentiment;

    public TweetProcessedMessage(String topic, String sentiment) {
        this.topic = topic;
        this.sentiment = sentiment;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "TweetProcessedMessage{" + "topic=" + topic + ", sentiment=" + sentiment + '}';
    }

    @Override
    public void accept(IProcessMessageVisitor visitor) {
        visitor.visit(this);
    }
}
