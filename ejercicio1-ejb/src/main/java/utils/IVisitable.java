/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author sergio
 * @param <T>
 */
public interface IVisitable<T extends IVisitor> {
    void accept(T visitor);
}
