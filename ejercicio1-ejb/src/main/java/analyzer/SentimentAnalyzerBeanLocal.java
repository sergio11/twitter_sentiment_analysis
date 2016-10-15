/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;

import javax.ejb.Local;
import models.Sentiment;

/**
 *
 * @author sergio
 */
@Local
public interface SentimentAnalyzerBeanLocal {

    Sentiment findSentiment(final String text);
    
}
