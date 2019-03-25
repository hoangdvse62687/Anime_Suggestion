/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import sample.constaints.CommonEnum;
import sample.utils.TextUtils;

/**
 *
 * @author DELL
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Movie", propOrder = {
    "id",
    "img",
    "name",
    "mountOfComment",
    "mountOfViewed",
    "detail",
    "rateManga",
    "isTopMovie"
})
@XmlRootElement(name = "Movie")
public class Movie {
    @XmlTransient
    private final String[] formatImg = {".png",".jpg"};
    @XmlElement
    private UUID id;
    @XmlTransient
    private String hrefMovie;
    @XmlElement
    private String img;
    @XmlElement
    private String name;
    @XmlElement
    private long mountOfComment;
    @XmlElement
    private long mountOfViewed;
    @XmlElement
    private MovieDetail detail;
    @XmlElement
    private double rateManga;
    @XmlElement
    private boolean isTopMovie;
    @XmlTransient
    private double rateInSite;
    public Movie() {
        this.isTopMovie = false;//default
        id = UUID.randomUUID();
    }

    public String getHrefMovie() {
        return hrefMovie;
    }

    public UUID getId() {
        return id;
    }
    
    public void setHrefMovie(String hrefMovie,String uri) {
        hrefMovie = TextUtils.fixedHrefInHtml(hrefMovie, uri);
        this.hrefMovie = hrefMovie;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        for (String formatImg : formatImg) {
            if(img.contains(formatImg)){
                int beginIndex = img.lastIndexOf("http");
                if(beginIndex < 0){
                    return;
                }
                int lastIndex = img.indexOf(formatImg) + formatImg.length();
                img = img.substring(beginIndex, lastIndex);
                this.img = img;
            }
        }
    }
    public void setImgVoid(String img){
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

    public void setMountOfComment(String mountOfComment) {
        if(mountOfComment == null){
            mountOfComment = "0";
        }
        long result = (long)TextUtils.getNumberInString(mountOfComment, mountOfComment.length());
        this.mountOfComment = result;
    }
    public void setMountOfComment(long mountOfComment) {
        this.mountOfComment = mountOfComment;
    }
    public long getMountOfViewed() {
        return mountOfViewed;
    }

    public void setMountOfViewed(String mountOfViewed) {
        if(mountOfViewed == null){
            mountOfViewed = "0";
        }
        long result = (long)TextUtils.getNumberInString(mountOfViewed, mountOfViewed.length());
        this.mountOfViewed = result;
    }
    public void setMountOfViewed(long mountOfViewed) {
        this.mountOfViewed = mountOfViewed;
    }
    public MovieDetail getDetail() {
        if(this.detail == null){
            this.detail = new MovieDetail();
        }
        return detail;
    }

    public void setDetail(MovieDetail detail) {
        this.detail = detail;
    }

    public double getRateManga() {
        return rateManga;
    }

    public void setRateManga(double rateManga) {
        this.rateManga = rateManga;
    }

    public boolean isIsTopMovie() {
        return isTopMovie;
    }

    public void setIsTopMovie(boolean isTopMovie) {
        this.isTopMovie = isTopMovie;
    }

    public double getRateInSite() {
        return rateInSite;
    }

    public void setRateInSite(double rateInSite) {
        this.rateInSite = rateInSite;
    }
    
    public void calculateRateInSite(){
        double result = 0;
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        double yearResult = detail.getYear() / currentYear;
        double commentResult = (mountOfComment / CommonEnum.mountOfViewAndComment) * 2;
        double viewedResult = 0;
        if(detail.getEpisode() > 0){
            viewedResult = ((mountOfViewed / detail.getEpisode())/CommonEnum.mountOfViewAndComment) * 2;
        }
        
        result = rateManga + 
                yearResult +
                commentResult +
                viewedResult;
        this.rateInSite = result;
    }
}
