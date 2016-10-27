/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.ejb.Local;
import models.Country;
import models.Province;

/**
 *
 * @author sergio
 */
@Local
public interface LocationServiceBeanLocal {

    List<Country> getCountries();

    List<Province> getProvinces();

    List<Province> getProvincesByCountry(final Long country);
    
}
