/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sergio
 */
@Entity
@Table(
        name = "topics",
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"name"}))
@NamedQueries({
    @NamedQuery(name = "Topic.all", query = "SELECT t FROM Topic t"),
    @NamedQuery(name = "TopicsByUser", query = "SELECT t FROM Topic t WHERE t.user.userName = :userName ORDER BY t.createAt DESC"),
    @NamedQuery(name = "Topic.count", query = "SELECT COUNT(T) FROM Topic t"),
    @NamedQuery(name = "Topic.recent", query = "SELECT t FROM Topic t ORDER BY t.createAt DESC"),
    @NamedQuery(name = "Topic.find", query = "SELECT t FROM Topic t WHERE t.name = :name")
})
public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date createAt = new Date();
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="topic")
    private List<Tweet> tweets;
    @ManyToOne
    private User user;

    public Topic(String name) {
        this.name = name;
    }

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
    
    public void addTweet(final Tweet tweet){
        this.tweets.add(tweet);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Topic{" + "id=" + id + ", name=" + name + ", tweets=" + tweets + ", user=" + user + '}';
    }
}
