/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import models.ProgressTopic;
import models.Topic;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author sergio
 */
public class ProgressTopicDataMapper implements IDataMapper<ProgressTopic, Topic>{

    @Override
    public ProgressTopic transform(Topic model) {
        if(model == null)
            throw new UnsupportedOperationException("Cannot transform a null value");
        ProgressTopic progressTopic = new ProgressTopic();
        PieChartModel pieChart = new PieChartModel();
        pieChart.setTitle(model.getName());
        pieChart.setLegendPosition("e");
        pieChart.setFill(false);
        pieChart.setShowDataLabels(true);
        pieChart.setDiameter(150);
        progressTopic.setChart(pieChart);
        progressTopic.setTopic(model.getName());
        progressTopic.setTweets(model.getTweets().size());
        progressTopic.setIsFinished(Boolean.FALSE);
        return progressTopic;
    }

    @Override
    public Collection<ProgressTopic> transform(Collection<Topic> models) {
        Collection<ProgressTopic> progressTopicCollection;
        if (models != null && !models.isEmpty()) {
            progressTopicCollection = new ArrayList<>();
            for (Topic topic : models) {
                progressTopicCollection.add(transform(topic));
            }
        } else {
            progressTopicCollection = Collections.emptyList();
        }

        return progressTopicCollection;
    }
    
}
