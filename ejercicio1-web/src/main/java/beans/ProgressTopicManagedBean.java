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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import mapper.ProgressTopicDataMapper;
import models.ProgressTopic;
import models.messages.FinishTopicAnalysisMessage;
import models.messages.StartTopicAnalysisMessage;
import models.messages.TweetProcessedMessage;
import models.messages.TweetsNotFoundForTopic;
import utils.IVisitable;
import visitor.IProcessMessageVisitor;
/**
 *
 * @author sergio
 */
@ManagedBean(name = "progressTopicBean")
@ViewScoped
public class ProgressTopicManagedBean implements Serializable, MessageListener, IProcessMessageVisitor {
    @Resource(mappedName = "jms/tweetsProcessedTopicFactory")
    private ConnectionFactory queueFactory;
    @Resource(mappedName = "jms/tweetsProcessedTopic")
    private Topic tweetsProcessedTopic;
    @ManagedProperty("#{i18n}")
    private ResourceBundle i18n;
    private final ProgressTopicDataMapper progressTopicDataMapper = new ProgressTopicDataMapper();
    private final Map<String, ProgressTopic> topicsInProgress = new HashMap();
    Connection qConnection;

    public ResourceBundle getI18n() {
        return i18n;
    }

    public void setI18n(ResourceBundle i18n) {
        this.i18n = i18n;
    }

    public Map<String, ProgressTopic> getTopicsInProgress() {
        return topicsInProgress;
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
            Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @PreDestroy
    protected void destroy() {
        if (qConnection != null) {
            try {
                qConnection.close();
            } catch (JMSException ex) {
                Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.INFO, "PROCESANDO EVENTO ...");
            Object msg = ((ObjectMessage) message).getObject();
            IVisitable<IProcessMessageVisitor> visitableMsg = (IVisitable<IProcessMessageVisitor>)msg;
            visitableMsg.accept(this);
        } catch (JMSException ex) {
            Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visit(TweetProcessedMessage message) {
        ProgressTopic progressTopic = topicsInProgress != null ? topicsInProgress.get(message.getTopic()) : null;
        Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.INFO, "update chart ...");
        
        if (progressTopic != null) {
            progressTopic.setTweets(progressTopic.getTweets() + 1);
            String label = message.getSentiment();
            Map<String, Number> data = progressTopic.getChart().getData();
            data.put(label, data.get(label).intValue() + 1);
            progressTopic.getChart().setData(data);
            Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.SEVERE, message.toString());
            Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.SEVERE, "Se ha procesado el tweet: " + message.getSentiment());
        }else{
            Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.INFO, "ProgressTopic es nulo");
        }
        
    }

    @Override
    public void visit(StartTopicAnalysisMessage message) {
        Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.SEVERE, "Iniciándo análisis para el topic: " + message.getTopic().getName());
        ProgressTopic progressTopic = progressTopicDataMapper.transform(message.getTopic());
        topicsInProgress.put(message.getTopic().getName(), progressTopic);
    }

    @Override
    public void visit(FinishTopicAnalysisMessage message) {
        Logger.getLogger(ProgressTopicManagedBean.class.getName()).log(Level.SEVERE, "Finalizando análsis para el topic: " + message.getTopic());
        ProgressTopic progressTopic = topicsInProgress != null ? topicsInProgress.get(message.getTopic()) : null;
        progressTopic.setIsFinished(Boolean.TRUE);
    }

    @Override
    public void visit(TweetsNotFoundForTopic message) {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesMessage.setDetail(i18n.getString("errors.tweets.notfound"));
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
