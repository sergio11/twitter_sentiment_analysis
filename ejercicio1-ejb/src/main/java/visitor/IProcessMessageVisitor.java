/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitor;

import models.messages.FinishTopicAnalysisMessage;
import models.messages.StartTopicAnalysisMessage;
import models.messages.TweetProcessedMessage;
import models.messages.TweetsNotFoundForTopic;
import utils.IVisitor;

/**
 *
 * @author sergio
 */
public interface IProcessMessageVisitor extends IVisitor {
    void visit(TweetProcessedMessage message);
    void visit(StartTopicAnalysisMessage message);
    void visit(FinishTopicAnalysisMessage message);   
    void visit(TweetsNotFoundForTopic message);
}
