/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.CountryDAOBeanLocal;
import dao.ProvincesDAOBeanLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import models.Country;
import models.Province;

/**
 *
 * @author sergio
 */
@Stateless
public class LocationServiceBean implements LocationServiceBeanLocal {
    
    @EJB
    private ProvincesDAOBeanLocal provincesDAOBean;
    @EJB
    private CountryDAOBeanLocal countryDAOBean;

    @Override
    public List<Country> getCountries() {
        return countryDAOBean.all();
    }

    @Override
    public List<Province> getProvinces() {
        return provincesDAOBean.all();
    }

    @Override
    public List<Province> getProvincesByCountry(final Long country) {
        return provincesDAOBean.getProvincesByCountry(country);
    }
}
