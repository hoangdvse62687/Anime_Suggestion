/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

import java.util.UUID;

/**
 *
 * @author DELL
 */
public class CategoryMovieMapping {
    private UUID movieId;
    private String cateNameId;

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }
    
    public String getCateNameId() {
        return cateNameId;
    }

    public void setCateNameId(String cateNameId) {
        this.cateNameId = cateNameId;
    }
}
