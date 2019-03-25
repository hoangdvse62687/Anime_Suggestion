/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@XmlRootElement(name = "movieDto")
public class movieDto extends BaseDto{
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
    private double rateInSite;
    private List<movieMappingCateDto> mappings;
    public final static String tablename = "Movie";
    public final static ArrayList<String> columns = new ArrayList<String>(Arrays.asList("Id","Img","Name","MountOfComment","MountOfViewed",
            "RateManga","IsTopMovie","Year","Episode","Description","rateInSite"));
    public final static ArrayList<Class> columnTypes = new ArrayList<Class>(Arrays.asList(UUID.class,String.class,String.class
    ,long.class,long.class,double.class,boolean.class,int.class,int.class,String.class,double.class));

    public movieDto() {
    }

    public movieDto(UUID id, String img, String name, long mountOfComment, long mountOfViewed, double rateManga, boolean isTopMovie, int year, int episode, String description, double rateInSite, List<movieMappingCateDto> mappings) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.mountOfComment = mountOfComment;
        this.mountOfViewed = mountOfViewed;
        this.rateManga = rateManga;
        this.isTopMovie = isTopMovie;
        this.year = year;
        this.episode = episode;
        this.description = description;
        this.rateInSite = rateInSite;
        this.mappings = mappings;
    }
    
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

    public double getRateInSite() {
        return rateInSite;
    }

    public void setRateInSite(double rateInSite) {
        this.rateInSite = rateInSite;
    }

    public List<movieMappingCateDto> getMappings() {
        return mappings;
    }

    public void setMappings(List<movieMappingCateDto> mappings) {
        this.mappings = mappings;
    }
    
    
}
