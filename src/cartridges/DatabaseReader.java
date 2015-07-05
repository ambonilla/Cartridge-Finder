/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartridges;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ambonilla
 */
public class DatabaseReader {
    public Connection connect;
    public boolean connectionSuccess;
    public DatabaseReader(){
        startConnection();
    }
    
    //To Do: Add get model list
    
    public String[] getModels(String brand){
        ArrayList<String> result = new ArrayList<String>();
        if(connectionSuccess){
            try{
                Statement newStatement = connect.createStatement();

                ResultSet rSet;
                
                String queryStr = "SELECT model FROM " + brand;
                rSet = newStatement.executeQuery(queryStr);
                
                while(rSet.next()){
                    result.add(rSet.getString("model"));
                }
            }
            catch(Exception e){
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
            }            
        }
        return result.toArray(new String[result.size()]);
    }
    
    public String getCartridges(String brand, String model){
        String result = "";
        if(connectionSuccess){
            try{
                Statement newStatement = connect.createStatement();

                ResultSet rSet;
                
                String queryStr = "SELECT cartridges FROM " + brand + " where" + " model = '"+ model +"'";
                rSet = newStatement.executeQuery(queryStr);
                
                while(rSet.next()){
                    result = rSet.getString("cartridges");
                }
            }
            catch(Exception e){
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
                //System.exit(0);
            }
        }
        else{
            result = "";
        }

        return result;
    }
    
    public void startConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:cartridges.db");
            connect.setAutoCommit(false);
            connectionSuccess = true;
        }
        catch(Exception e){
            connectionSuccess = false;
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
