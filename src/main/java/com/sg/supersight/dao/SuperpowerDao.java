/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersight.dao;

import com.sg.supersight.model.Superpower;
import java.util.List;

/**
 *
 * @author Thomas
 */
public interface SuperpowerDao {
    
    Superpower getPowerById(int id);
    List<Superpower> getAllPowers();
    Superpower addPower(Superpower power);
    void updatePower(Superpower power);
    void deletePowerById(int id);
}
