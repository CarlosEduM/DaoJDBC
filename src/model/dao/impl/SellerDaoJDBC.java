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
import java.sql.Statement;
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
        PreparedStatement st = null;
        
        try{
            st = conn.prepareStatement("INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartamentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)", 
                    Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            
            int rowsAffected = st.executeUpdate();
            
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResulSet(rs);
            }
            else{
                throw new DBException("Erro inesperado nenhuma linha afetada");
            }
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        
        try{
            st = conn.prepareStatement("UPDATE seller "
                    + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartamentId = ? "
                    + "WHERE Id = ?");
            
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());
            
            st.executeUpdate();
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        
        try{
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            
            st.setInt(1, id);
            
            int rowsAffected  = st.executeUpdate();
            
            if(rowsAffected == 0){
                throw new DBException("linha não existe");
            }
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
        
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
