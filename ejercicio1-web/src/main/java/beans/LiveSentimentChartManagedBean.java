/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import models.TweetsBySentiment;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "liveSentimentChartBean")
@SessionScoped
public class LiveSentimentChartManagedBean implements Serializable {
    
    @EJB
    private FacadeBeanLocal facadeBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private final Map<String, PieChartModel> liveCharts = new HashMap();

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public Map<String, PieChartModel> getLiveCharts() {
        return liveCharts;
    }
  
   
    public void updateChart(final String topic){
        PieChartModel chart =  liveCharts != null ? liveCharts.get(topic) : null;
        Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.INFO, "update chart ...");
        if(chart != null){
            List<TweetsBySentiment> tbsList = facadeBean.groupedBySentiment(topic);
            Logger.getLogger(LiveSentimentChartManagedBean.class.getName()).log(Level.INFO, "Result count: " + tbsList.size());
            Iterator<TweetsBySentiment> iteTbs = tbsList.iterator();
            while (iteTbs.hasNext()) {
                TweetsBySentiment tbs = iteTbs.next();
                String label = tbs.getSentiment().name();
                chart.set(label, tbs.getTweets());
            }
        }
        liveCharts.put(topic, chart);
    
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
    
    
}
