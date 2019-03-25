/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author DELL
 */
public class BaseDao<T,PK> implements IGenericDao<T, PK>{
    
    public String capitalize(String x){
	char[] s = x.toCharArray();
	s[0] = Character.toUpperCase(s[0]);
	x = new String(s);
	return x;
    }
    
    public String Join(String glue, List Array){
	String res ="";
	int i=0;
	for(i=0;i<Array.size()-1;i++){
            res = res + Array.get(i) + glue;
	}
	res = res + Array.get(i);
	return res;
    }
    
    public String Repeat(String glue, String repeat, Integer count){
	String res ="";
	int i=0;
	for(i=0;i<count-1;i++){
            res = res + repeat + glue;
	}
	res = res + repeat;
	return res;
    }
    
    @Override
    public T findById(PK entityId,T entity) {
        Connection con = null;
        PreparedStatement statement = null;
        try{
            try {
                con = DBUtils.makeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<String> columns = (List<String>) entity.getClass().getDeclaredField("columns").get(entity);
            ArrayList<Class> clazz = (ArrayList<Class>)entity.getClass().getDeclaredField("columnTypes").get(entity);
            if(entityId== null){
		return null;
            }
            String sql = "select * from " + (String)entity.getClass().getDeclaredField("tablename").get(entity) + " where " + columns.get(0) + "=?";
            statement = con.prepareStatement(sql);
            statement.setString(1, parseObjectToString(entityId));
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
		for(int i=0;i<columns.size();i++){// leave the id cause already have
                    String field = columns.get(i);
                    entity.getClass().getMethod("set" + capitalize(field),clazz.get(i)).invoke(entity, parseStringToObject(clazz.get(i),rs.getString(field)));
		}
            }

        }catch(Exception ex){
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return entity;
    }

    @Override
    public void create(T entity) {
        Connection con = null;
        PreparedStatement statement = null;
        try{
            try {
                con = DBUtils.makeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<String> columns = (List<String>)entity.getClass().getDeclaredField("columns").get(entity);
            String tableName = (String) entity.getClass().getDeclaredField("tablename").get(entity).toString();
            String sql = "insert into " + tableName + "(" + Join(", ", columns) + ") values (" + Repeat(", ", "?", columns.size()) + ")";
            
            statement = con.prepareStatement(sql);
            
            for(int i = 0;i < columns.size();i++){
                statement.setString(i+1, parseObjectToString(entity.getClass().getMethod("get" + capitalize(columns.get(i))).invoke(entity)));
            }
            statement.execute();
            
        }catch(Exception ex){
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public T update(T entity) {
        Connection con = null;
        PreparedStatement statement = null;
        try{
            try {
                con = DBUtils.makeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String tableName = (String) entity.getClass().getDeclaredField("tablename").get(entity).toString();
            ArrayList<Class> clazz = (ArrayList<Class>)entity.getClass().getDeclaredField("columnTypes").get(entity);
            ArrayList<String> columns = (ArrayList<String>) entity.getClass().getDeclaredField("columns").get(entity);
            String sql = "update " + tableName + " set ";
            int i;
            for(i=1;i<columns.size()-1;i++){//ingore id with int i = 1
		sql = sql + columns.get(i) + "=?,";
            }
            sql = sql + columns.get(i) + "=? where " + columns.get(0) + "=?";
            
            statement = con.prepareStatement(sql);
            for(i=1;i<columns.size();i++){
		statement.setString(i, parseObjectToString(entity.getClass().getMethod("get" + capitalize(columns.get(i))).invoke(entity))); //ignore id with i + 1
            }
            statement.setString(i, parseObjectToString(entity.getClass().getMethod("get" + capitalize(columns.get(0))).invoke(entity)));
            statement.executeUpdate();
        }catch(Exception ex){
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return entity;
    }

    @Override
    public void deleteById(PK entityId,T entity) {
        Connection con = null;
        PreparedStatement statement = null;
        try{
            try {
                con = DBUtils.makeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String tableName = (String) entity.getClass().getDeclaredField("tablename").get(entity).toString();
            ArrayList<Class> clazz = (ArrayList<Class>)entity.getClass().getDeclaredField("columnTypes").get(entity);
            ArrayList<String> columns = (ArrayList<String>) entity.getClass().getDeclaredField("columns").get(entity);
            String sql = "delete  from " + tableName + " where " + columns.get(0) + "=?";
            statement = con.prepareStatement(sql);

            if(entityId != null){
                statement.setString(1,parseObjectToString(entityId));
		statement.executeUpdate();
            }
        }catch(Exception ex){
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private String parseObjectToString(Object o){
        if(o instanceof String){
            return o.toString();
        }else if(o instanceof Boolean){
            return o.toString();
        }else if(o instanceof Long){
            return Long.toString(Long.parseLong(o.toString()));
        }else if(o instanceof Integer){
            return Integer.toString(Integer.parseInt(o.toString()));
        }else if(o instanceof Double){
            return Double.toString(Double.parseDouble(o.toString()));
        }else if(o instanceof UUID){
            return o.toString();
        }else{
            return "0";//default
        }
    }
    
    private Object parseStringToObject(Class clazz,String src){
        if(clazz == Boolean.class || clazz == boolean.class){
            return Boolean.parseBoolean(src);
        }else if(clazz == Long.class || clazz == long.class){
            return Long.parseLong(src);
        }else if(clazz == Integer.class || clazz == int.class){
            return Integer.parseInt(src);
        }else if(clazz == Double.class || clazz == double.class){
            return Double.parseDouble(src);
        }else if(clazz == UUID.class){
            return UUID.fromString(src);
        }else{
            return src;
        }
    }
}
