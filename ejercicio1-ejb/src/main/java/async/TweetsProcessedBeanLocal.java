/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package async;

import javax.ejb.Local;
import models.Topic;
import models.Tweet;

/**
 *
 * @author sergio
 */
@Local
public interface TweetsProcessedBeanLocal {

    void addProcessedTweet(final Tweet tweet);

    void finishTopicAnalysis(final Topic topic);

    void topicAnalysisError(final Topic topic);

    void startTopicAnalysis(final Topic topic);
    
}
