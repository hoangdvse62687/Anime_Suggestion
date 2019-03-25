/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dao;

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
public class movieDao extends BaseDao<movieDto, UUID>{
    public List<movieDto> getTopMovie(int page,int pageSize){
        Connection con = null;
        PreparedStatement statement = null;
        List<movieDto> results = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            String sql = "Select id,img,name,episode,rateInSite from Movie "
                    + "Order by rateInSite desc "
                    + "offset ? rows fetch first ? row only";
            statement = con.prepareStatement(sql);
            statement.setInt(1, page);
            statement.setInt(2, pageSize);
            ResultSet rs = statement.executeQuery();
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
            Logger.getLogger(movieDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(movieDao.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<movieDto> searchMoviesByName(String name,int page,int pageSize){
        Connection con = null;
        PreparedStatement statement = null;
        List<movieDto> results = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            String sql = "Select id,img,name,episode,rateInSite from Movie where name like ? "
                    + "Order by rateInSite desc "
                    + "offset ? rows fetch first ? row only";
            statement = con.prepareStatement(sql);
            statement.setString(1,"%" + name + "%");
            statement.setInt(2, page);
            statement.setInt(3, pageSize);
            ResultSet rs = statement.executeQuery();
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
            Logger.getLogger(movieDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(movieDao.class.getName()).log(Level.SEVERE, null, ex);
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
}
