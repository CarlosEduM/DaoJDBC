/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Seller;

/**
 *
 * @author Cadu
 */
public interface SellerDao {
    
    public void insert(Seller obj);
    public void update(Seller obj);
    public void deleteById(Integer id);
    public Seller findById(Integer id);
    public List<Seller> findAll();
}
