/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import models.TweetsBySentiment;
import org.primefaces.model.chart.DonutChartModel;


/**
 *
 * @author sergio
 */
@ManagedBean(name = "chartBean")
@SessionScoped
public class ChartManagedBean {
    
    @EJB
    private FacadeBeanLocal facadeBean;
    @ManagedProperty("#{searchTopicBean}")
    private SearchTopicsManagedBean searchTopicBean;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    
    private final Map<String, DonutChartModel> charts = new HashMap();

    public Map<String, DonutChartModel> getCharts() {
        return charts;
    }

    public SearchTopicsManagedBean getSearchTopicBean() {
        return searchTopicBean;
    }

    public void setSearchTopicBean(SearchTopicsManagedBean searchTopicBean) {
        this.searchTopicBean = searchTopicBean;
    }

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }
    
    public void update(){
        List<String> topicsSelected = searchTopicBean.getTopicsSelected();
        Iterator<String> ite = topicsSelected.iterator();
        while(ite.hasNext()){
            String topic = ite.next();
            if(!charts.containsKey(topic)){
                List<TweetsBySentiment> result = facadeBean.groupedBySentiment(topic);
                DonutChartModel chartModel = new DonutChartModel();
                Map<String, Number> circle = new LinkedHashMap();
                Iterator<TweetsBySentiment> iteTbs = result.iterator();
                while(iteTbs.hasNext()){
                    TweetsBySentiment tbs = iteTbs.next();
                    String label = tbs.getSentiment().name();
                    circle.put(label, tbs.getTweets());
                }
                chartModel.addCircle(circle);
                chartModel.setTitle(i18n.getString("page.results.charts.donut.title"));
                chartModel.setLegendPosition("w");
                charts.put(topic, chartModel);
            }
        }
    }

}
