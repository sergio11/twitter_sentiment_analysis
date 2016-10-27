/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "TopicsByUser", query = "SELECT t FROM Topic t WHERE t.user.userName = :userName")
})
public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(cascade = ALL, mappedBy = "topic")
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

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
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
