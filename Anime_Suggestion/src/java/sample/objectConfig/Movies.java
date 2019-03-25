/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author DELL
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movies", propOrder = {
    "movie"
})
@XmlRootElement(name = "movies")
public class Movies {
    @XmlElement
    private List<Movie> movie;

    public List<Movie> getMovie() {
        if(this.movie == null){
            this.movie = new ArrayList<>();
        }
        return movie;
    }

    public void setMovie(List<Movie> movie) {
        this.movie = movie;
    }
    
}
