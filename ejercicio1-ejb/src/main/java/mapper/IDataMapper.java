/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapper;

import java.util.Collection;

/**
 *
 * @author sergio
 * @param <T> Model Target
 * @param <E> Model to Map
 */
public interface IDataMapper<T, E> {
    T transform(E model);
    Collection<T> transform(Collection<E> models);
}
