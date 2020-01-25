/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Department;

/**
 *
 * @author Cadu
 */
public interface DepartmentDao {
    
    public void insert(Department obj);
    public void update(Department obj);
    public void deleteById(Integer id);
    public Department findById(Integer id);
    public List<Department> findAll();
}
