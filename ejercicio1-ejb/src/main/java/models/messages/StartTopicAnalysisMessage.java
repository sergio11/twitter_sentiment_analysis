/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.messages;

import java.io.Serializable;
import models.Topic;
import utils.IVisitable;
import visitor.IProcessMessageVisitor;

/**
 *
 * @author sergio
 */
public class StartTopicAnalysisMessage implements Serializable, IVisitable<IProcessMessageVisitor>{
    
    private Topic topic;

    public StartTopicAnalysisMessage(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public void accept(IProcessMessageVisitor visitor) {
        visitor.visit(this);
    }
    
    
    
}
