/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author sergio
 */
@Entity(name = "PROVINCES")
@NamedQueries({
    @NamedQuery(name="Province.getAll", query="SELECT p FROM PROVINCES p"),
    @NamedQuery(name="Province.getByCountry", query="SELECT p FROM PROVINCES p WHERE p.country = :country")
})
public class Province implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue( strategy=GenerationType.AUTO )
    private Long id;
    private String name;
    private Long country;

    public Province() {
    }

    public Province(Long id, String name, Long country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Province{" + "id=" + id + ", name=" + name + ", country=" + country + '}';
    }
   
    
}
