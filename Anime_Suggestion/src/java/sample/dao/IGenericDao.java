/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface IGenericDao<T,PK> {
    
    T findById(PK entityId,T entity);
    
    void create(T entity);
    
    T update(T entity);
    
    void deleteById(PK entityId,T entity);
}
