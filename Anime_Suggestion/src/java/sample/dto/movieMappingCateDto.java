/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@XmlRootElement(name = "movieMappingCateDto")
public class movieMappingCateDto extends BaseDto{
    private UUID movieId;
    private String nameCate;
    
    public final static String tablename = "movieMappingCate";
    public final static ArrayList<String> columns = new ArrayList<String>(Arrays.asList("movieId","nameCate"));
    public final static ArrayList<Class> columnTypes = new ArrayList<Class>(Arrays.asList(UUID.class,String.class));

    public movieMappingCateDto() {
    }

    public movieMappingCateDto(UUID movieId, String nameCate) {
        this.movieId = movieId;
        this.nameCate = nameCate;
    }
    
    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public String getNameCate() {
        return nameCate;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }
    
    
}
