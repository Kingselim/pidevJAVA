/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.services;


import com.esprit.models.DemandeSponsoring;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author House
 */
public interface IService<T> {
    void ajouter(T t) throws SQLException;
    void delete(T t) throws SQLException;
    DemandeSponsoring update(T t) throws SQLException;
    List<T> readAll() throws SQLException;
}

