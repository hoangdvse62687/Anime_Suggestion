/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

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
@XmlType(name = "config", propOrder = {
    "uri",
    "categoryContext",
    "parentElementCategory",
    "parentClassCategory",
    "formatPagging",
    "endformatPagging",
    "parentElementMovie",
    "parentClassMovie",
    "imgElementMovie",
    "imgClassMovie",
    "imgTargetMovie",
    "nameClassMovie",
    "nameTargetMovie",
    "commentClassMovie",
    "commentTargetMovie",
    "viewedClassMovie",
    "viewedTargetMovie",
    "shortenMovieDisplayOrder",
    "episodeClassMovieDetail",
    "episodeTargetMovieDetail",
    "episodeContextMovideDetail",
    "typeClassMovieDetail",
    "typeTargetMovieDetail",
    "yearClassMovieDetail",
    "yearTargetMovieDetail",
    "yearContextMovieDetail",
    "descriptionClassMovieDetail",
    "descriptionTargetMovieDetail",
    "descriptionContextMovieDetail",
    "dumplicateClassMovieDetail",
    "dumplicateTagetMovideDetail",
    "detailMovieDisplayOrder",
    "topWeekNameCLassParent",
    "topWeekValueClassParent",
    "topWeekElementName",
    "topWeekClassUri",
    "topWeekTargetUri",
    "maximumPageGetInCategory",
    "forbiddenLinkCategories",
    "topWeekImgClass",
    "topWeekImgClassTarget",
    "topWeekClassName",
    "topWeekNameElement",
    "topWeekCommentClass",
    "topWeekCommentElement"
})
@XmlRootElement(name = "config")
public class Config {
    @XmlElement(required = true)
    protected String uri;
    @XmlElement(required = true)
    protected String categoryContext;
    @XmlElement(required = true)
    protected String parentElementCategory;
    @XmlElement
    protected String parentClassCategory;
    @XmlElement(required = true)
    protected String formatPagging;
    @XmlElement
    protected String endformatPagging;
    @XmlElement(required = true)
    protected String parentElementMovie;
    @XmlElement
    protected String parentClassMovie;
    @XmlElement(required = true)
    protected String imgElementMovie;
    @XmlElement
    protected String imgClassMovie;
    @XmlElement(required = true)
    protected String imgTargetMovie;
    @XmlElement
    protected String nameClassMovie;
    @XmlElement(required = true)
    protected String nameTargetMovie;
    @XmlElement
    protected String commentClassMovie;
    @XmlElement
    protected String commentTargetMovie;
    @XmlElement
    protected String viewedClassMovie;
    @XmlElement(required = true)
    protected String viewedTargetMovie;
    @XmlElement(required = true)
    protected List<String> shortenMovieDisplayOrder;
    @XmlElement
    protected String episodeClassMovieDetail;
    @XmlElement(required = true)
    protected String episodeTargetMovieDetail;
    @XmlElement
    protected String episodeContextMovideDetail;
    @XmlElement
    protected String typeClassMovieDetail;
    @XmlElement(required = true)
    protected String typeTargetMovieDetail;
    @XmlElement
    protected String yearClassMovieDetail;
    @XmlElement(required = true)
    protected String yearTargetMovieDetail;
    @XmlElement
    protected String yearContextMovieDetail;
    @XmlElement
    protected String descriptionClassMovieDetail;
    @XmlElement(required = true)
    protected String descriptionTargetMovieDetail;
    @XmlElement
    protected String descriptionContextMovieDetail;
    @XmlElement
    protected String dumplicateClassMovieDetail;
    @XmlElement
    protected String dumplicateTagetMovideDetail;
    @XmlElement(required = true)
    protected List<String> detailMovieDisplayOrder;
    @XmlElement
    protected String topWeekNameCLassParent;
    @XmlElement
    protected String topWeekValueClassParent;
    @XmlElement
    protected String topWeekElementName;
    @XmlElement(required = true)
    protected String topWeekClassUri;
    @XmlElement(required = true)
    protected String topWeekTargetUri;
    @XmlElement(required = true)
    protected int maximumPageGetInCategory;
    @XmlElement
    protected  List<String> forbiddenLinkCategories;
    @XmlElement(required = true)
    protected String topWeekImgClass;
    @XmlElement(required = true)
    protected String topWeekImgClassTarget;
    @XmlElement(required = true)
    protected String topWeekClassName;
    @XmlElement(required = true)
    protected String topWeekNameElement;
    @XmlElement(required = true)
    protected String topWeekCommentClass;
    @XmlElement(required = true)
    protected String topWeekCommentElement;
    
    public String getTopWeekImgClass() {
        return topWeekImgClass;
    }

    public void setTopWeekImgClass(String topWeekImgClass) {
        this.topWeekImgClass = topWeekImgClass;
    }

    public String getTopWeekImgClassTarget() {
        return topWeekImgClassTarget;
    }

    public void setTopWeekImgClassTarget(String topWeekImgClassTarget) {
        this.topWeekImgClassTarget = topWeekImgClassTarget;
    }

    public String getTopWeekClassName() {
        return topWeekClassName;
    }

    public void setTopWeekClassName(String topWeekClassName) {
        this.topWeekClassName = topWeekClassName;
    }

    public String getTopWeekNameElement() {
        return topWeekNameElement;
    }

    public void setTopWeekNameElement(String topWeekNameElement) {
        this.topWeekNameElement = topWeekNameElement;
    }

    public String getTopWeekCommentClass() {
        return topWeekCommentClass;
    }

    public void setTopWeekCommentClass(String topWeekCommentClass) {
        this.topWeekCommentClass = topWeekCommentClass;
    }

    public String getTopWeekCommentElement() {
        return topWeekCommentElement;
    }

    public void setTopWeekCommentElement(String topWeekCommentElement) {
        this.topWeekCommentElement = topWeekCommentElement;
    }
    
    public int getMaximumPageGetInCategory() {
        return maximumPageGetInCategory;
    }

    public void setMaximumPageGetInCategory(int maximumPageGetInCategory) {
        this.maximumPageGetInCategory = maximumPageGetInCategory;
    }
    
    public String getTopWeekNameCLassParent() {
        return topWeekNameCLassParent;
    }

    public String getTopWeekElementName() {
        return topWeekElementName;
    }

    public void setTopWeekElementName(String topWeekElementName) {
        this.topWeekElementName = topWeekElementName;
    }
    
    public void setTopWeekNameCLassParent(String topWeekNameCLassParent) {
        this.topWeekNameCLassParent = topWeekNameCLassParent;
    }

    public String getTopWeekValueClassParent() {
        return topWeekValueClassParent;
    }

    public void setTopWeekValueClassParent(String topWeekValueClassParent) {
        this.topWeekValueClassParent = topWeekValueClassParent;
    }
    
    public String getTopWeekClassUri() {
        return topWeekClassUri;
    }

    public void setTopWeekClassUri(String topWeekClassUri) {
        this.topWeekClassUri = topWeekClassUri;
    }

    public String getTopWeekTargetUri() {
        return topWeekTargetUri;
    }

    public void setTopWeekTargetUri(String topWeekTargetUri) {
        this.topWeekTargetUri = topWeekTargetUri;
    }
    
    public String getDumplicateClassMovieDetail() {
        return dumplicateClassMovieDetail;
    }

    public void setDumplicateClassMovieDetail(String dumplicateClassMovieDetail) {
        this.dumplicateClassMovieDetail = dumplicateClassMovieDetail;
    }

    public String getDumplicateTagetMovideDetail() {
        return dumplicateTagetMovideDetail;
    }

    public void setDumplicateTagetMovideDetail(String dumplicateTagetMovideDetail) {
        this.dumplicateTagetMovideDetail = dumplicateTagetMovideDetail;
    }

    
    public String getEpisodeContextMovideDetail() {
        return episodeContextMovideDetail;
    }

    public void setEpisodeContextMovideDetail(String episodeContextMovideDetail) {
        this.episodeContextMovideDetail = episodeContextMovideDetail;
    }

    public String getYearContextMovieDetail() {
        return yearContextMovieDetail;
    }

    public void setYearContextMovieDetail(String yearContextMovieDetail) {
        this.yearContextMovieDetail = yearContextMovieDetail;
    }

    public String getDescriptionContextMovieDetail() {
        return descriptionContextMovieDetail;
    }

    public void setDescriptionContextMovieDetail(String descriptionContextMovieDetail) {
        this.descriptionContextMovieDetail = descriptionContextMovieDetail;
    }

    
    public String getEpisodeClassMovieDetail() {
        return episodeClassMovieDetail;
    }

    public void setEpisodeClassMovieDetail(String episodeClassMovieDetail) {
        this.episodeClassMovieDetail = episodeClassMovieDetail;
    }

    public String getEpisodeTargetMovieDetail() {
        return episodeTargetMovieDetail;
    }

    public void setEpisodeTargetMovieDetail(String episodeTargetMovieDetail) {
        this.episodeTargetMovieDetail = episodeTargetMovieDetail;
    }

    public String getTypeClassMovieDetail() {
        return typeClassMovieDetail;
    }

    public void setTypeClassMovieDetail(String typeClassMovieDetail) {
        this.typeClassMovieDetail = typeClassMovieDetail;
    }

    public String getTypeTargetMovieDetail() {
        return typeTargetMovieDetail;
    }

    public void setTypeTargetMovieDetail(String typeTargetMovieDetail) {
        this.typeTargetMovieDetail = typeTargetMovieDetail;
    }

    public String getYearClassMovieDetail() {
        return yearClassMovieDetail;
    }

    public void setYearClassMovieDetail(String yearClassMovieDetail) {
        this.yearClassMovieDetail = yearClassMovieDetail;
    }

    public String getYearTargetMovieDetail() {
        return yearTargetMovieDetail;
    }

    public void setYearTargetMovieDetail(String yearTargetMovieDetail) {
        this.yearTargetMovieDetail = yearTargetMovieDetail;
    }

    public String getDescriptionClassMovieDetail() {
        return descriptionClassMovieDetail;
    }

    public void setDescriptionClassMovieDetail(String descriptionClassMovieDetail) {
        this.descriptionClassMovieDetail = descriptionClassMovieDetail;
    }

    public String getDescriptionTargetMovieDetail() {
        return descriptionTargetMovieDetail;
    }

    public void setDescriptionTargetMovieDetail(String descriptionTargetMovieDetail) {
        this.descriptionTargetMovieDetail = descriptionTargetMovieDetail;
    }

    public List<String> getDetailMovieDisplayOrder() {
        return detailMovieDisplayOrder;
    }

    public void setDetailMovieDisplayOrder(List<String> detailMovieDisplayOrder) {
        this.detailMovieDisplayOrder = detailMovieDisplayOrder;
    }
    
    
    
    public List<String> getShortenMovieDisplayOrder() {
        return shortenMovieDisplayOrder;
    }

    public void setShortenMovieDisplayOrder(List<String> shortenMovieDisplayOrder) {
        this.shortenMovieDisplayOrder = shortenMovieDisplayOrder;
    }
    
    public String getViewedClassMovie() {
        return viewedClassMovie;
    }

    public void setViewedClassMovie(String viewedClassMovie) {
        this.viewedClassMovie = viewedClassMovie;
    }

    public String getViewedTargetMovie() {
        return viewedTargetMovie;
    }

    public void setViewedTargetMovie(String viewedTargetMovie) {
        this.viewedTargetMovie = viewedTargetMovie;
    }
    
    public String getCommentClassMovie() {
        return commentClassMovie;
    }

    public void setCommentClassMovie(String commentClassMovie) {
        this.commentClassMovie = commentClassMovie;
    }

    public String getCommentTargetMovie() {
        return commentTargetMovie;
    }

    public void setCommentTargetMovie(String commentTargetMovie) {
        this.commentTargetMovie = commentTargetMovie;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCategoryContext() {
        return categoryContext;
    }

    public void setCategoryContext(String categoryContext) {
        this.categoryContext = categoryContext;
    }

    public String getParentElementCategory() {
        return parentElementCategory;
    }

    public void setParentElementCategory(String parentElementCategory) {
        this.parentElementCategory = parentElementCategory;
    }

    public String getParentClassCategory() {
        return parentClassCategory;
    }

    public void setParentClassCategory(String parentClassCategory) {
        this.parentClassCategory = parentClassCategory;
    }

    public String getFormatPagging() {
        return formatPagging;
    }

    public void setFormatPagging(String formatPagging) {
        this.formatPagging = formatPagging;
    }

    public String getEndformatPagging() {
        return endformatPagging;
    }

    public void setEndformatPagging(String endformatPagging) {
        this.endformatPagging = endformatPagging;
    }

    public String getParentElementMovie() {
        return parentElementMovie;
    }

    public void setParentElementMovie(String parentElementMovie) {
        this.parentElementMovie = parentElementMovie;
    }

    public String getParentClassMovie() {
        return parentClassMovie;
    }

    public void setParentClassMovie(String parentClassMovie) {
        this.parentClassMovie = parentClassMovie;
    }

    public String getImgElementMovie() {
        return imgElementMovie;
    }

    public void setImgElementMovie(String imgElementMovie) {
        this.imgElementMovie = imgElementMovie;
    }

    public String getImgClassMovie() {
        return imgClassMovie;
    }

    public void setImgClassMovie(String imgClassMovie) {
        this.imgClassMovie = imgClassMovie;
    }

    public String getImgTargetMovie() {
        return imgTargetMovie;
    }

    public void setImgTargetMovie(String imgTargetMovie) {
        this.imgTargetMovie = imgTargetMovie;
    }

    public String getNameClassMovie() {
        return nameClassMovie;
    }

    public void setNameClassMovie(String nameClassMovie) {
        this.nameClassMovie = nameClassMovie;
    }

    public String getNameTargetMovie() {
        return nameTargetMovie;
    }

    public void setNameTargetMovie(String nameTargetMovie) {
        this.nameTargetMovie = nameTargetMovie;
    }

    public List<String> getForbiddenLinkCategories() {
        return forbiddenLinkCategories;
    }

    public void setForbiddenLinkCategories(List<String> forbiddenLinkCategories) {
        this.forbiddenLinkCategories = forbiddenLinkCategories;
    }
}
