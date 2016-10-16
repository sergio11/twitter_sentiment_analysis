/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
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

    private DonutChartModel chartModel;

    public DonutChartModel getChartModel() {
        return chartModel;
    }

    @PostConstruct
    protected void init(){
        chartModel = new DonutChartModel();
        List<TweetsBySentiment> result = facadeBean.groupedBySentiment("keane");
        
        Map<String, Number> circle = new LinkedHashMap();
        Iterator<TweetsBySentiment> ite = result.iterator();
        while(ite.hasNext()){
            TweetsBySentiment tbs = ite.next();
            circle.put(tbs.getSentiment().name(), tbs.getTweets());
        }
        chartModel.addCircle(circle);
        chartModel.setTitle("Análisis de sentimiento");
        chartModel.setLegendPosition("w");
    }

}
