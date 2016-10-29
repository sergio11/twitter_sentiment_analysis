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
public class FinishTopicAnalysisMessage implements Serializable, IVisitable<IProcessMessageVisitor> {

    private String topic;
    private Integer tweetsCount;

    public FinishTopicAnalysisMessage(String topic, Integer tweetsCount) {
        this.topic = topic;
        this.tweetsCount = tweetsCount;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getTweetsCount() {
        return tweetsCount;
    }

    public void setTweetsCount(Integer tweetsCount) {
        this.tweetsCount = tweetsCount;
    }

    @Override
    public String toString() {
        return "FinishTopicAnalysisMessage{" + "topic=" + topic + ", tweetsCount=" + tweetsCount + '}';
    }

    @Override
    public void accept(IProcessMessageVisitor visitor) {
        visitor.visit(this);
    }
}
