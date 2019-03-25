/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author DELL
 */
public class rawDataDto extends BaseDto{
    private UUID id;
    private String img;
    private String name;
    private long mountOfComment;
    private long mountOfViewed;
    private double rateManga;
    private boolean isTopMovie;
    private int year;
    private int episode;
    private String description;
    
    public final static String tablename = "RawData";
    public final static ArrayList<String> columns = new ArrayList<String>(Arrays.asList("Id","Img","Name","MountOfComment","MountOfViewed",
            "RateManga","IsTopMovie","Year","Episode","Description"));
    public final static ArrayList<Class> columnTypes = new ArrayList<Class>(Arrays.asList(UUID.class,String.class,String.class
    ,long.class,long.class,double.class,boolean.class,int.class,int.class,String.class));

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMountOfComment() {
        return mountOfComment;
    }

    public void setMountOfComment(long mountOfComment) {
        this.mountOfComment = mountOfComment;
    }

    public long getMountOfViewed() {
        return mountOfViewed;
    }

    public void setMountOfViewed(long mountOfViewed) {
        this.mountOfViewed = mountOfViewed;
    }

    public double getRateManga() {
        return rateManga;
    }

    public void setRateManga(double rateManga) {
        this.rateManga = rateManga;
    }

    public boolean getIsTopMovie() {
        return isTopMovie;
    }

    public void setIsTopMovie(boolean isTopMovie) {
        this.isTopMovie = isTopMovie;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEpisode() {
        return episode;
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
