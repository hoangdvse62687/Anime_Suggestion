/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class WebData {
    private String uri;
    private List<Category> category;
    private List<Movie> listMovies;
    
    public WebData(List<Category> category) {
        this.category = category;
    }

    public List<Category> getCategory() {
        if(this.category == null){
            this.category = new ArrayList<Category>();
        }
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Movie> getListMovies() {
        if(this.listMovies == null){
            this.listMovies = new ArrayList<Movie>();
        }
        return listMovies;
    }

    public void setListMovies(List<Movie> listMovies) {
        this.listMovies = listMovies;
    }
}
