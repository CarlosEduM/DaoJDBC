/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import db.DB;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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
        Scanner sc = new Scanner(System.in);
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("=== Test 1: seller findById ===");
        
        Seller seller = sellerDao.findById(3);
        
        System.out.println(seller);
        
        System.out.println("\n=== Test 2: seller findByDepartmant ===");
        
        List<Seller> list = sellerDao.findByDepartment(seller.getDepartment());
        
        list.forEach(System.out::println);
        
        System.out.println("\n=== Test 3: seller findAll ===");
        
        list = sellerDao.findAll();
        
        list.forEach(System.out::println);
        
        System.out.println("\n=== Test 4: seller insert ===");
        
        Seller newSeller = new Seller(null, "Greg", "geg@gmail.com", new Date(), 4800.00, list.get(1).getDepartment());
        
        sellerDao.insert(newSeller);
        
        System.out.println("Adicionado! novo id: " + newSeller.getId());
        
        System.out.println("\n=== Test 5: seller update ===");
        
        seller = sellerDao.findById(1);
        
        seller.setName("Martha Waine");
        
        sellerDao.update(seller);
        
        System.out.println("Atualização completa");
        
        System.out.println("\n=== Test 6: seller delete ===");
        
        System.out.print("Informe o id a ser deletado: ");
        int id = sc.nextInt();
        
        sellerDao.deleteById(id);
        
        System.out.println("Deletado com sucesso!");
        
        DB.closeConnection();
        
        sc.close();
    }
    
}
