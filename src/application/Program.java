/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.util.Date;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author Cadu
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("=== Test 1: seller findById ===");
        
        Seller seller = sellerDao.findById(3);
        
        System.out.println(seller);
        
        System.out.println("\n=== Test 2: seller findByDepartmant ===");
        
        List<Seller> sellerDepartment = sellerDao.findByDepartment(seller.getDepartment());
        
        sellerDepartment.forEach(System.out::println);
    }
    
}
