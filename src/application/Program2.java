/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

/**
 *
 * @author Cadu
 */
public class Program2 {
    public static void main(String[] args){
        DepartmentDao depDao = DaoFactory.createDepartmetDao();
        
        System.out.println("=== test 1: Department findAll ===");
        
        List<Department> list = depDao.findAll();
        
        list.forEach(System.out::println);
        
        System.out.println("\n=== test 2: Department findById ===");
        
        System.out.println(depDao.findById(2));
        
        System.out.println("\n=== test 3: Department update ===");
        
        Department dep = depDao.findById(1);
        
        dep.setName("Computers");
        
        depDao.update(dep);
        
        System.out.println("Atualização completa");
        
        System.out.println("\n=== test 4: Department inset ===");
        
        dep = new Department(null, "Ferramentas");
        
        depDao.insert(dep);
        
        System.out.println("Departamento criado! id: " + dep.getId());
    }
}
