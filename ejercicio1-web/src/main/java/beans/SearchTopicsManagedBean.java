/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import facade.FacadeBeanLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.Topic;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "searchTopicBean")
@SessionScoped
public class SearchTopicsManagedBean implements Serializable {
    @EJB
    private FacadeBeanLocal facadeBean;
    private List<Topic> topics;
    private String topicsCSV;
    private List<Topic> topicsSelected;
    
    @PostConstruct
    protected void init(){
        topics = facadeBean.getTopics();
        List<String> topicNames = Lists.transform(topics, new Function<Topic, String>(){
            @Override
            public String apply(Topic topic) {
                return topic.getName();
            }
        });
        topicsCSV = StringUtils.join(topicNames, ",");
        Logger.getLogger("ostias").log(Level.INFO, topicsCSV);
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public String getTopicsCSV() {
        return topicsCSV;
    }

    public List<Topic> getTopicsSelected() {
        return topicsSelected;
    }

    public void setTopicsSelected(List<Topic> topicsSelected) {
        this.topicsSelected = topicsSelected;
    }
    
    
    
    public void search(){}
    
   
}
