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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import sample.utils.TextUtils;

/**
 *
 * @author DELL
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MovieDetail", propOrder = {
    "year",
    "episode"
})
@XmlRootElement(name = "MovieDetail")
public class MovieDetail {
    @XmlTransient
    private List<String> categoryName;
    @XmlElement
    private int year;
    @XmlElement
    private int episode;
    @XmlTransient
    private String description;
    
    public List<String> getCategoryName() {
        if(this.categoryName == null){
            this.categoryName = new ArrayList<String>();
        }
        return categoryName;
    }

    public void setCategoryName(List<String> categoryName) {
        this.categoryName = categoryName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = (int)TextUtils.getNumberInString(year,4);// year is four number
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public int getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        if(episode.toLowerCase().contains("giờ") || episode.toLowerCase().contains("phút")){// this is not episode, it is duration
            this.episode = 1;
        }else
            this.episode = (int)TextUtils.getNumberInString(episode, 3);// maximum episode in anime is 3
    }
    
    public void setEpisode(int episode) {
        
        this.episode = episode;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
