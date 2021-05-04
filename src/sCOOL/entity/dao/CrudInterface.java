/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sCOOL.entity.dao;

import java.util.ArrayList;

/**
 *
 * @author tsepe
 */
public interface CrudInterface<T> {
    ArrayList<T> getAll();
    T getById(int id);
    T getByMaxId();
    void create(T t);
    void update(T t);
    void delete(T t);
    boolean exists(T t);
}
