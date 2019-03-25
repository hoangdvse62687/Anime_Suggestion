/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.constaints.CateEnum;
import sample.constaints.CommonEnum;
import sample.objectConfig.CategoryMovieMapping;
import sample.objectConfig.Movie;
import sample.objectConfig.MovieDetail;

/**
 *
 * @author DELL
 */
public class CommonUtils implements Serializable{
    private static String[] specialCategoryNames = {"SCI_FI"};//shorten characters
    private static String filePathPackage = "Content/images/";
    public static String getCategory(String categoryName){
        if(categoryName.isEmpty()){
            return "";
        }
        CateEnum[] cateEnums = CateEnum.values();
        for(CateEnum name : cateEnums){
            for (String specialCategoryName : specialCategoryNames) {
                if(name.toString().toUpperCase().equals(specialCategoryName.toUpperCase()) 
                        || name.getValue().toUpperCase().equals(specialCategoryName.toUpperCase())){
                    if(name.toString().toUpperCase().replace("_", "-").equals(categoryName.toUpperCase())
                            || name.getValue().toUpperCase().equals(categoryName.toUpperCase())){
                        return name.toString();
                    }
                }else{
                    if(name.toString().toUpperCase().replace("_", " ").equals(categoryName.toUpperCase())
                            || name.getValue().toUpperCase().equals(categoryName.toUpperCase())){
                         return name.toString();
                    }
                }
            }
        }
        return "";
    }
    
    public static void saveImage(String url,String pathFile) 
            throws IOException{
        InputStream is = null;
        try {
            is = InternetUtils.makeConnection(url);
            File f = new File(pathFile);
            Files.copy(is, Paths.get(f.getAbsolutePath()),StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            Logger.getLogger(CommonUtils.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            if(is != null){
                is.close();
            }
        }
    }
    
    public static List<Movie> mergeMovies(List<Movie> webA, List<Movie> webB,List<CategoryMovieMapping> mappings
            ,List<String> nameMovieMerged,String realPath,List<Movie> listTopMovie) 
            throws IOException{
        List<Movie> results = new ArrayList<Movie>();
        List<Movie> tmpA = null;
        List<Movie> tmpB = null;
        if(mappings == null || nameMovieMerged == null){
          return results;  
        }
        if(webA != null && webB != null){
            if(webA.size() > webB.size()){
                tmpA = webA;
                tmpB = webB;
            }else{
                tmpA = webB;
                tmpB = webA;
            }
            
            for(int i = 0; i < tmpA.size();i++){
                if(tmpA.get(i).isIsTopMovie() && !nameMovieMerged.contains(tmpA.get(i).getName())){
                    parseTopMovie(tmpA.get(i), results, mappings,nameMovieMerged,realPath,listTopMovie);
                }
                for(int j = 0; j < tmpB.size();j++){
                    if(tmpB.get(j).isIsTopMovie() && !nameMovieMerged.contains(tmpB.get(j).getName())){
                        parseTopMovie(tmpB.get(j), results, mappings,nameMovieMerged,realPath,listTopMovie);
                    }
                    if(compareNameMovie(tmpA.get(i).getName(),tmpB.get(j).getName())){
                        if(nameMovieMerged.contains(tmpA.get(i).getName()) ||
                            nameMovieMerged.contains(tmpB.get(j).getName())){
                            
                        }else{
                            analyseMovie(tmpA, tmpB, i, j, results, mappings,nameMovieMerged,realPath);
                        }
                    }
                }
            }
        }
        
        return results;
    }
    public static boolean compareNamePercent(String movieA,String movieB){
        boolean result = false;
        String tmpA = "";
        String tmpB = "";
        if(movieA == null || movieB == null){
            return false;
        }
        if(movieA.length() > movieB.length()){
            tmpA = movieA;
            tmpB = movieB;
        }else{
            tmpA = movieB;
            tmpB = movieA;
        }
        int matchPoint = computeMatchingDensity(tmpB, tmpA);
        if(matchPoint * 100 > CommonEnum.matchingPercent * Math.min(tmpB.length(), tmpA.length())){
            result = true;
        }
        return result;
    }
    private static int computeMatchingDensity(String a, String b){
        int n = a.length();
        
        int m = b.length();
        int dp[][] = new int[n + 1][m + 1];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(a.charAt(i) == b.charAt(j)){
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                }else{
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[n][m];
    }
    public static boolean compareNameMovie(String movieA, String movieB){
        boolean result = false;
        
        if(movieA != null && movieB != null){
            if(movieA.equals(movieB)){
                return true;
            }
            
            if(movieA.length() > movieB.length()){
                if(movieA.contains(movieB)){
                    return true;
                }
            }else{
                if(movieB.contains(movieA)){
                    return true;
                }
            }
        }
        return result;
    }
    
    public static void analyseMovie(List<Movie> webA, List<Movie> webB,int i,int j,List<Movie> results
            ,List<CategoryMovieMapping> mappings,List<String> nameMovieMerged,String realPath) 
            throws IOException{
        Movie result = new Movie();
        result.setName(webA.get(i).getName());
        nameMovieMerged.add(result.getName());
                        int index = results.size() + 1;
                        if(webA.get(i).getMountOfComment() > webB.get(j).getMountOfComment()){
                            result.setMountOfComment(webA.get(i).getMountOfComment());
                        }else{
                            result.setMountOfComment(webB.get(j).getMountOfComment());
                        }
                        
                        String nameFileImage = realPath + filePathPackage + index+ CommonEnum.formatImageOutput;
                        if(webA.get(i).getMountOfViewed() > webB.get(j).getMountOfViewed()){
                            result.setMountOfViewed(webA.get(i).getMountOfViewed());
                            saveImage(webA.get(i).getImg(),nameFileImage);
                        }else{
                            result.setMountOfViewed(webB.get(j).getMountOfViewed());
                            saveImage(webB.get(j).getImg(),nameFileImage);
                        }
                        result.setImgVoid(index + CommonEnum.formatImageOutput);
                        
                        MovieDetail detail = new MovieDetail();
                        for(String cateA : webA.get(i).getDetail().getCategoryName()){
                            String outputCate = getCategory(cateA);
                            if(!outputCate.isEmpty()){
                                if(!detail.getCategoryName().contains(outputCate)){
                                    detail.getCategoryName().add(outputCate);
                                }
                            }
                        }
                        
                        for(String cateB : webB.get(j).getDetail().getCategoryName()){
                            String outputCate = getCategory(cateB);
                            if(!outputCate.isEmpty()){
                                if(!detail.getCategoryName().contains(outputCate)){
                                    detail.getCategoryName().add(outputCate);
                                }
                            }
                        }
                        
                        //add cateName to mapping class
                        for(String cate : detail.getCategoryName()){
                            CategoryMovieMapping mapping = new CategoryMovieMapping();
                            mapping.setCateNameId(cate);
                            mapping.setMovieId(result.getId());
                            mappings.add(mapping);
                        }
                        
                        if(webA.get(i).getDetail().getEpisode() > webB.get(j).getDetail().getEpisode()){
                            detail.setEpisode(webA.get(i).getDetail().getEpisode());
                        }else{
                            detail.setEpisode(webB.get(j).getDetail().getEpisode());
                        }
                        
                        if(webA.get(i).getDetail().getYear()> webB.get(j).getDetail().getYear()){
                            detail.setYear(webA.get(i).getDetail().getYear());
                        }else{
                            detail.setYear(webB.get(j).getDetail().getYear());
                        }
                        long descriptionA;
                        long descriptionB;
                        
                        if(webA.get(i).getDetail().getDescription() != null){
                            descriptionA = webA.get(i).getDetail().getDescription().length();
                        }else{
                            descriptionA = 0;
                        }
                        
                        if(webB.get(j).getDetail().getDescription() != null){
                            descriptionB = webB.get(j).getDetail().getDescription().length();
                        }else{
                            descriptionB = 0;
                        }
                        if(descriptionA == 0 && descriptionB == 0){
                            
                        }
                        else if(descriptionA > descriptionB){
                            detail.setDescription(webA.get(i).getDetail().getDescription());
                        }else{
                            detail.setDescription(webB.get(j).getDetail().getDescription());
                        }
                        result.setDetail(detail);
                        results.add(result);
                        
    }
    private static void parseTopMovie(Movie movie,List<Movie> results
            ,List<CategoryMovieMapping> mappings,List<String> nameMovieMerged,String realPath,List<Movie> listTopMovie) 
            throws IOException{
        Movie result = new Movie();
        if(movie == null){
            return;
        }else{
            int index = results.size() + 1;
            result.setName(movie.getName());
            nameMovieMerged.add(result.getName());
            result.setIsTopMovie(true);
            result.setMountOfComment(movie.getMountOfComment());
            String nameFileImage = realPath + filePathPackage + index + CommonEnum.formatImageOutput;
            result.setMountOfViewed(movie.getMountOfViewed());
            saveImage(movie.getImg(),nameFileImage);
            result.setImgVoid(index + CommonEnum.formatImageOutput);
            MovieDetail detail = new MovieDetail();
            for(String str : movie.getDetail().getCategoryName()){
                String outputCate = getCategory(str);
                if(!outputCate.isEmpty()){
                    if(!detail.getCategoryName().contains(outputCate)){
                        detail.getCategoryName().add(outputCate);
                    }
                }
            }
            detail.setEpisode(movie.getDetail().getEpisode());
            
            //add cateName to mapping class
            for(String cate : detail.getCategoryName()){
                CategoryMovieMapping mapping = new CategoryMovieMapping();
                mapping.setCateNameId(cate);
                mapping.setMovieId(result.getId());
                mappings.add(mapping);
            }
            detail.setYear(movie.getDetail().getYear());
            detail.setDescription(movie.getDetail().getDescription());
            result.setDetail(detail);
            listTopMovie.add(result);
            results.add(result);
        }
    }
}
