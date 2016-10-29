/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.messages;

import java.io.Serializable;

/**
 *
 * @author sergio
 */
public class TopicAnalysisErrorMessage implements Serializable{
    
    private String topic;

    public TopicAnalysisErrorMessage(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
