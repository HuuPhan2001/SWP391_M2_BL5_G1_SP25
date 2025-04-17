/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.dao;

import vn.edu.fpt.config.DbContext;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author MTTTT
 */
public class Main {
    public static void main(String[] args) {
        DbContext dbContext = new DbContext();
        try(Connection connection = dbContext.getConnection()){
            System.out.println(connection);
            System.out.println("connect success");
        } catch(SQLException ex){
            System.out.println("Error: Failed to close the database connection.");
            ex.printStackTrace();
        }
    }
}
