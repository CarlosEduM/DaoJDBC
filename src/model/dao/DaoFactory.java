/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

/**
 *
 * @author Cadu
 */
public class DaoFactory {
    
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConn());
    }
    
    public static DepartmentDao createDepartmetDao(){
        return new DepartmentDaoJDBC(DB.getConn());
    }
}
