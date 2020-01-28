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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                Seller sel = instantiateSeller(rs, instantiateDepartment(rs));
                
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
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,departament.Name as DepName"
                    + " FROM seller INNER JOIN departament"
                    + " ON seller.DepartamentId = departament.Id"
                    + " ORDER BY Name");
            
            rs = st.executeQuery();
            
            List<Seller> list = new ArrayList();
            Map<Integer, Department> map = new HashMap<>();
            
            while(rs.next()){
                Department dep = map.get(rs.getInt("DepartamentId"));
                
                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartamentId"), dep);
                }
                
                list.add(instantiateSeller(rs, dep));
            }
            
            return list;
        } 
        catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResulSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("DepartamentId"));
        dep.setName(rs.getString("DepName"));
        
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sel = new Seller();
        sel.setId(rs.getInt("Id"));
        sel.setName(rs.getString("Name"));
        sel.setEmail(rs.getString("Email"));
        sel.setBaseSalary(rs.getDouble("BaseSalary"));
        sel.setBirthDate(rs.getDate("BirthDate"));
        sel.setDepartment(dep);
        
        return sel;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,departament.Name as DepName"
                    + " FROM seller INNER JOIN departament"
                    + " ON seller.DepartamentId = departament.Id"
                    + " WHERE DepartamentId = ?"
                    + " ORDER BY Name");
            
            st.setInt(1, department.getId());
            rs = st.executeQuery();
            
            List<Seller> list = new ArrayList();
            
            while(rs.next()){
                list.add(instantiateSeller(rs, department));
            }
            
            return list;
        } 
        catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResulSet(rs);
        }
    }
    
}
