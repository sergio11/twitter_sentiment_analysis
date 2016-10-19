/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import facade.FacadeBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.Country;
import models.Province;

/**
 *
 * @author sergio
 */
@ManagedBean(name = "locationBean")
@RequestScoped
public class LocationManagedBean {
    
    @EJB
    private FacadeBeanLocal facadeBean;
    private Long country;
    private List<Country> countries = new ArrayList();
    private List<Province> provincesSelected = new ArrayList();
    
    @PostConstruct
    public void init(){
        // load countries
        countries = facadeBean.getCountries();
    }

    public List<Country> getCountries() {
        return countries;
    }
    
    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public List<Province> getProvincesSelected() {
        return provincesSelected;
    }

    public void setProvincesSelected(List<Province> provincesSelected) {
        this.provincesSelected = provincesSelected;
    }
    
    public void changeCountry(){
        provincesSelected = facadeBean.getProvincesByCountry(country);
    }
    
}
