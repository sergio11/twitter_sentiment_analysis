/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Local;

/**
 *
 * @author sergio
 */
@Local
public interface FacadeBeanLocal {

    void analyzeTopic(String topic);
    
}