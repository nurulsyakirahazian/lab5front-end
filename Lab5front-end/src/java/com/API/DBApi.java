/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.API;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author acer
 */
public class DBApi {
    
    static Connection con;
    static ResultSet rs;
    
    public static JSONObject authentication(String email,String password){
        JSONObject jo=new JSONObject();
        int ada=0;
        try{
            con=ConMan.getConnection();
            String sql="select * from register where email=? and password=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2,password);
            System.out.println("SQL:"+ps.toString());
            rs=ps.executeQuery();
            while(rs.next()){
                ada=1;
            }
            if(ada==1){
                jo.put("status",1);
            }else{
                jo.put("status",0);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return jo;
    }
    public static JSONObject newRegistration(String email,String password){
        int ada=0;
        JSONObject jo=new JSONObject();
        try{
            con=ConMan.getConnection();
            String sql="select * from register where email=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, email);
            rs=ps.executeQuery();
            while(rs.next()){
                ada=1;
            }
            if(ada==0){
                try{
                    sql="insert into register(email,password)values(?,?)";
                    PreparedStatement ps2=con.prepareStatement(sql);
                    
                    ps2.setString(1,email);
                    ps2.setString(2, password);
                    ps2.executeUpdate();
                    jo.put("mesej","Registration Success!");
                    jo.put("status",1);
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                jo.put("mesej","user already exist!");
                jo.put("status",0);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return jo;
    }
}
