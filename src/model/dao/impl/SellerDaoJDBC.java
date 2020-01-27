/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.impl;

import db.DB;
import db.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author Cadu
 */
public class SellerDaoJDBC implements SellerDao{

    private Connection conn;
    
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void insert(Seller obj) {
        
    }

    @Override
    public void update(Seller obj) {
    }

    @Override
    public void deleteById(Integer id) {
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,departament.Name as DepName "
                    + "FROM seller INNER JOIN departament "
                    + "ON seller.DepartamentId = departament.Id "
                    + "WHERE seller.Id = ?");
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("DepartamentId"));
                dep.setName(rs.getString("DepName"));
                
                Seller sel = new Seller();
                sel.setId(rs.getInt("Id"));
                sel.setName(rs.getString("Name"));
                sel.setEmail(rs.getString("Email"));
                sel.setBaseSalary(rs.getDouble("BaseSalary"));
                sel.setBirthDate(rs.getDate("BirthDate"));
                sel.setDepartment(dep);
                
                return sel;
            }
            
            return null;
        } 
        catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResulSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
    
}
