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
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

/**
 *
 * @author Cadu
 */
public class DepartmentDaoJDBC implements DepartmentDao{
    
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void insert(Department obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        
        try{
            st = conn.prepareStatement("UPDATE departament SET Name = ? WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());
            
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try{
            st = this.conn.prepareStatement("SELECT * FROM coursejdbc.departament WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                return this.instantiateDepartment(rs);
            }
            
            return null;
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResulSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try{
            st = this.conn.prepareStatement("SELECT * FROM coursejdbc.departament");
            rs = st.executeQuery();
            
            List<Department> list = new ArrayList<>();
            
            while(rs.next()){
                list.add(this.instantiateDepartment(rs));
            }
            return list;
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResulSet(rs);
        }
    }
    
    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        
        return dep;
    }
}
