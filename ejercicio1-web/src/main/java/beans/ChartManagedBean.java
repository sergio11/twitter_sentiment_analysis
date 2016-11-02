/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.PieChartModel;
import services.TweetsServiceBeanLocal;


/**
 *
 * @author sergio
 */
@ManagedBean(name = "chartBean")
@SessionScoped
public class ChartManagedBean {
    @EJB
    private TweetsServiceBeanLocal tweetsServiceBean;
    @ManagedProperty("#{topicsManagedBean}")
    private TopicsManagedBean topicsManagedBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    
    private final Map<String, DonutChartModel> donutCharts = new HashMap();
    private final Map<String, PieChartModel> pieCharts = new HashMap();

    public Map<String, DonutChartModel> getDonutCharts() {
        return donutCharts;
    }

    public Map<String, PieChartModel> getPieCharts() {
        return pieCharts;
    }

    public TopicsManagedBean getTopicsManagedBean() {
        return topicsManagedBean;
    }

    public void setTopicsManagedBean(TopicsManagedBean topicsManagedBean) {
        this.topicsManagedBean = topicsManagedBean;
    }
 
    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }
    
    private DonutChartModel createDonutChart(List<TweetsBySentiment> tbsList) {
        DonutChartModel chartModel = new DonutChartModel();
        Map<String, Number> circle = new LinkedHashMap();
        Iterator<TweetsBySentiment> iteTbs = tbsList.iterator();
        while (iteTbs.hasNext()) {
            TweetsBySentiment tbs = iteTbs.next();
            String label = tbs.getSentiment().name();
            circle.put(label, tbs.getTweets());
        }
        chartModel.addCircle(circle);
        chartModel.setTitle(i18n.getString("page.results.charts.donut.title"));
        chartModel.setLegendPosition("w");
        return chartModel;
    }
    
    private PieChartModel createPieChart(List<TweetsBySentiment> tbsList){
        PieChartModel pieChart = new PieChartModel();
        Iterator<TweetsBySentiment> iteTbs = tbsList.iterator();
        while (iteTbs.hasNext()) {
            TweetsBySentiment tbs = iteTbs.next();
            String label = tbs.getSentiment().name();
            pieChart.set(label, tbs.getTweets());
        }
        pieChart.setTitle(i18n.getString("page.results.charts.donut.title"));
        pieChart.setLegendPosition("e");
        pieChart.setFill(false);
        pieChart.setShowDataLabels(true);
        pieChart.setDiameter(150);
        return pieChart;
    }
    
    
    public void update(){
        List<String> topicsSelected = topicsManagedBean.getTopicsSelected();
        Iterator<String> ite = topicsSelected.iterator();
        while(ite.hasNext()){
            String topic = ite.next();
            if(!donutCharts.containsKey(topic) || !pieCharts.containsKey(topic)){
                List<TweetsBySentiment> result = tweetsServiceBean.groupedBySentiment(topic);
                Logger.getLogger(ChartManagedBean.class.getName()).log(Level.INFO, "Result Count: " + result.size());
                if(!donutCharts.containsKey(topic))
                    donutCharts.put(topic, createDonutChart(result));
                if(!pieCharts.containsKey(topic))
                    pieCharts.put(topic, createPieChart(result));
            }
        }
    }

}
