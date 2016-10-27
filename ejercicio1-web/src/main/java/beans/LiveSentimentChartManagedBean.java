/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import models.Tweet;
import org.primefaces.model.chart.PieChartModel;
/**
 *
 * @author sergio
 */
@ManagedBean(name = "liveSentimentChartBean")
@ViewScoped
public class LiveSentimentChartManagedBean implements Serializable, MessageListener {
    @Resource(mappedName = "jms/tweetsProcessedTopicFactory")
    private ConnectionFactory queueFactory;
    @Resource(mappedName = "jms/tweetsProcessedTopic")
    private Topic tweetsProcessedTopic;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private final Map<String, PieChartModel> liveCharts = new HashMap();
    Connection qConnection;

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public Map<String, PieChartModel> getLiveCharts() {
        return liveCharts;
    }
    
    @PostConstruct
    protected void init(){
        try {
            qConnection = queueFactory.createConnection();
            Session session = qConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(tweetsProcessedTopic);
            consumer.setMessageListener(this);
            qConnection.start();
        } catch (Exception ex) {
            Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @PreDestroy
    protected void destroy() {
        if (qConnection != null) {
            try {
                qConnection.close();
            } catch (JMSException ex) {
                Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void createChart(String topic){
        PieChartModel pieChart = new PieChartModel();
        pieChart.setTitle("Custom Pie");
        pieChart.setLegendPosition("e");
        pieChart.setFill(false);
        pieChart.setShowDataLabels(true);
        pieChart.setDiameter(150);
        liveCharts.put(topic, pieChart);
        Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.INFO, "Topic: " + topic);
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage msg = (ObjectMessage) message;
            Tweet tweetProcessed = (Tweet) msg.getObject();
            PieChartModel chart =  liveCharts != null ? liveCharts.get(tweetProcessed.getTopic().getName()) : null;
            Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.INFO, "update chart ...");
            if(chart != null){
                String label = tweetProcessed.getSentiment().name();
                Map<String, Number> data = chart.getData();
                data.put(label, data.get(label).intValue() + 1);
                chart.setData(data);
            }
            Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.SEVERE, "Se ha procesado el tweet: " + tweetProcessed.getText());
        } catch (JMSException ex) {
            Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
