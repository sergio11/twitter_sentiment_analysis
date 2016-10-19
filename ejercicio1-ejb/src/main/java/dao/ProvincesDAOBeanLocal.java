/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;
import models.Province;

/**
 *
 * @author sergio
 */
@Local
public interface ProvincesDAOBeanLocal {

    List<Province> all();

    List<Province> getProvincesByCountry(final Long country);
    
}
