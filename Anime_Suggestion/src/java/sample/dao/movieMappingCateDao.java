/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dao;

import java.sql.CallableStatement;
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
import sample.dto.movieDto;
import sample.dto.movieMappingCateDto;
import sample.utils.DBUtils;

/**
 *
 * @author DELL
 */
public class movieMappingCateDao extends BaseDao<movieMappingCateDto, UUID>{
    public List<movieMappingCateDto> getCateNameByMovieId(String id){
        Connection con = null;
        PreparedStatement statement = null;
        List<movieMappingCateDto> results = new ArrayList<>();
        
        try {
            con = DBUtils.makeConnection();
            String sql = "Select nameCate from movieMappingCate where movieId = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                movieMappingCateDto dto = new movieMappingCateDto();
                dto.setNameCate(rs.getString("nameCate"));
                results.add(dto);
            }
        } catch (NamingException ex) {
            Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(movieDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(movieDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return results;
    }
    
    public List<movieDto> getMoviesByCateName(String cateName,int page,int pageSize){
        List<movieDto> results = new ArrayList<movieDto>();
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = DBUtils.makeConnection();
            String sql = "{call GETMOVIESBYNAMECATE (?,?,?)}";
            cs = con.prepareCall(sql);
            cs.setString(1, cateName);
            cs.setInt(2, page);
            cs.setInt(3, pageSize);
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                movieDto dto = new movieDto();
                dto.setId(UUID.fromString(rs.getString("id")));
                dto.setImg(rs.getString("img"));
                dto.setName(rs.getString("name"));
                dto.setEpisode(rs.getInt("episode"));
                dto.setRateInSite(rs.getDouble("rateInSite"));
                movieMappingCateDao mappingDao = new movieMappingCateDao();
                List<movieMappingCateDto> mappingDto = new ArrayList<>();
                mappingDto = mappingDao.getCateNameByMovieId(rs.getString("id"));
                dto.setMappings(mappingDto);
                results.add(dto);
            }
        } catch (NamingException ex) {
            Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(cs != null){
                try {
                    cs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return results;
    }
    
    public String getMovieSuggestionId(String cateName1,String cateName2){
        String result = "";
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = DBUtils.makeConnection();
            String sql = "{call GETSUGGESTIONANIME (?,?)}";
            cs = con.prepareCall(sql);
            cs.setString(1, cateName1);
            cs.setString(2, cateName2);
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                result = rs.getString("id");
            }
        } catch (NamingException ex) {
            Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(cs != null){
                try {
                    cs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(movieMappingCateDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
}
