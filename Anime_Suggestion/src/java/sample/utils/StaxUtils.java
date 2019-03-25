/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import sample.constaints.CommonEnum;
import sample.objectConfig.Config;
import sample.objectConfig.MangaRanking;
import sample.objectConfig.Movie;
import sample.objectConfig.MovieDetail;

/**
 *
 * @author DELL
 */
public class StaxUtils implements Serializable{
    
    private static final String shortenHrefElement = "href";
    private static final String shortenImgElement = "img";
    private static final String shortenNameElement = "name";
    private static final String shortenCommentElement = "comment";
    private static final String shortenViewedElement = "viewed";
    
    private static final String detailEpisodeElement = "episode";
    private static final String detailTypeElement = "type";
    private static final String detailYearElement = "year";
    private static final String detailDescriptionElement = "description";
    
    public static void getMovieDetail(String uri,Config config,List<Movie> listMovies,List<String> hrefs) 
            throws XMLStreamException, FileNotFoundException, IOException{
        List<String> displayOrder = config.getShortenMovieDisplayOrder();
        InputStream is = null;
        XMLStreamReader reader = null;
        is = InternetUtils.makeConnection(uri);
        is = TextUtils.fixWellform(is);
        reader = parseInputStreamToStax(is);
        if(is != null){
            is.close();
        }
        if (reader != null){
            while(reader.hasNext()){
                int currentCursor = reader.next();
                    if(currentCursor == XMLStreamConstants.START_ELEMENT){
                        try {
                            String tagName = reader.getLocalName();
                            if(tagName.equals(config.getParentElementMovie())){
                                String attrName = reader.getAttributeValue("", "class");
                                if(attrName == null){

                                }else if(attrName.equals(config.getParentClassMovie())){
                                    //start getData
                                    Movie movie = new Movie();
                                    boolean isDumpMovie = false;
                                    if(displayOrder != null){
                                        for (String order : displayOrder) {
                                            if(isDumpMovie){
                                                break;
                                            }
                                            
                                            switch(order){
                                                case shortenHrefElement:
                                                    String href = getAtttrNode(reader, "a", "", "href");
                                                    if(!hrefs.contains(TextUtils.fixedHrefInHtml(href, config.getUri()))){
                                                        movie.setHrefMovie(href,config.getUri());
                                                    }else{
                                                        isDumpMovie = true;
                                                    }
                                                    break;
                                                case shortenImgElement:
                                                    movie.setImg(getImg(reader, config));
                                                    break;
                                                case shortenNameElement:
                                                    movie.setName(getTextContent(reader, config.getNameTargetMovie(),"",config.getNameClassMovie()));
                                                    break;
                                                case shortenCommentElement:
                                                    movie.setMountOfComment(getTextContent(reader, config.getCommentTargetMovie(), "", config.getCommentClassMovie()));
                                                    break;
                                                case shortenViewedElement:
                                                    movie.setMountOfViewed(getTextContent(reader, config.getViewedTargetMovie(), "", config.getViewedClassMovie()));
                                                    break;
                                            }
                                        }
                                    }
                                    if(!isDumpMovie){
                                        listMovies.add(movie);
                                        hrefs.add(movie.getHrefMovie());
                                        System.out.println("Href movie: " + movie.getHrefMovie());
                                        System.out.println("Img movie: " + movie.getImg());
                                        System.out.println("Name movie: " + movie.getName());
                                        System.out.println("Comment movie: " + movie.getMountOfComment());
                                        System.out.println("Mount of Viewed: " + movie.getMountOfViewed());
                                    }
                                }
                            }// element
                        } catch (Exception ex) {
                            Logger.getLogger(StaxUtils.class.getName()).log(Level.SEVERE,null,ex);
                        }finally{
                            if(is != null){
                                is.close();
                            }
                        }
                    }
            }
        }
    }
    
    public static List<MangaRanking> getMangaRanking(String uri) 
            throws IOException, XMLStreamException{
        List<MangaRanking> result = new ArrayList<MangaRanking>();
        
        InputStream is = null;
        XMLStreamReader reader = null;
        is = InternetUtils.makeConnection(uri);
        is = TextUtils.fixWellform(is);
        reader = parseInputStreamToStax(is); 
        if (reader != null){
            while(reader.hasNext()){
                int currentCursor = reader.next();
                    if(currentCursor == XMLStreamConstants.START_ELEMENT){
                        try{
                            MangaRanking data = new MangaRanking();
                            String name = getTextContent(reader, CommonEnum.nameElement, "", CommonEnum.classNameValue);
                            String score = getTextContent(reader, CommonEnum.scoreElement, "", CommonEnum.classScoreValue);
                            data.setName(name);
                            data.setScore(Double.parseDouble(score));
                            result.add(data);
                            System.out.println("Manga Name: " + data.getName());
                            System.out.println("Manga Score: " + data.getScore());
                        }catch(Exception ex){
                            Logger.getLogger(StaxUtils.class.getName()).log(Level.SEVERE,null,ex);
                        }
                    }
            }
        }
        return result;
    }
    
    public static void getMovieDescription(String uri,Config config,Movie movie) 
            throws IOException, XMLStreamException{
        InputStream is = null;
        XMLStreamReader reader = null;
        is = InternetUtils.makeConnection(uri);
        is = TextUtils.fixWellform(is);
        reader = parseInputStreamToStax(is);
        if(is != null){
            is.close();
        }
        if (reader != null){
            while(reader.hasNext()){
                int currentCursor = reader.next();
                    if(currentCursor == XMLStreamConstants.START_ELEMENT){
                        try {
                            List<String> displayOrder = config.getDetailMovieDisplayOrder();
                            MovieDetail detail = new MovieDetail();
                            for (String order : displayOrder) {
                                switch(order){
                                    case detailEpisodeElement:
                                        detail.setEpisode(getValuesOfElement(reader, config.getEpisodeTargetMovieDetail()
                                                , config.getEpisodeClassMovieDetail() , config.getEpisodeContextMovideDetail()));
                                        break;
                                    case detailTypeElement:
                                        detail.setCategoryName(getCategoryType(reader, config.getTypeTargetMovieDetail(),config.getTypeClassMovieDetail()));
                                        break;
                                    case detailYearElement:
                                        detail.setYear(TextUtils.unescapeHTML(getValuesOfElement(reader,config.getYearTargetMovieDetail()
                                                ,config.getYearClassMovieDetail(),config.getYearContextMovieDetail())));
                                        break;
                                    case detailDescriptionElement:
                                        detail.setDescription(getValuesOfElement(reader, config.getDescriptionTargetMovieDetail()
                                                , config.getDescriptionClassMovieDetail(),config.getDescriptionContextMovieDetail()));
                                        break;
                                    default:
                                        // this is skip duplicate in html
                                        getValuesOfElement(reader, config.getDumplicateTagetMovideDetail()
                                                , config.getDumplicateClassMovieDetail(),"");
                                }
                            }
                            movie.setDetail(detail);
                            System.out.println("Movie Name: " + movie.getName());
                            System.out.println("Movie Episode: " + detail.getEpisode());
                            System.out.println("Movie Categories: ");
                            for(String nameCategory : detail.getCategoryName()){
                                System.out.println(nameCategory);
                            }
                            System.out.println("Movie Year: " + detail.getYear());
                            System.out.println("Movie Description: " + detail.getDescription());
                            //end read of description of movie
                            break;
                            
                        } catch (Exception ex) {
                            Logger.getLogger(StaxUtils.class.getName()).log(Level.SEVERE,null,ex);
                        }
                    }
            }
        }
    }
    
    public static XMLStreamReader parseInputStreamToStax(InputStream is) 
            throws XMLStreamException{
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        
        return reader;
    }
    
    public static String getAtttrNode(XMLStreamReader reader,
            String elementName, String namespaceURI,String atrrName)
    throws XMLStreamException{
        if(reader != null){
            while(reader.hasNext()){
                int cursor = reader.getEventType();
                if(cursor == XMLStreamConstants.START_ELEMENT){
                    String tagName = reader.getLocalName();
                    if(tagName.equals(elementName)){
                        String result = reader.getAttributeValue(namespaceURI,atrrName);

                        return result;
                    }
                }
                reader.next();
            }
        }
        return null;
    }
    
    public static String getTextContent(XMLStreamReader reader
            ,String elementName, String namespaceURI,String atrrName) 
            throws XMLStreamException{
        if (reader == null){
            return null;
        }
        
        if(elementName == null){
            return null;
        }
        
        if(elementName.trim().isEmpty()){
            return null;
        }
        
        while(reader.hasNext()){
            int currentCursor = reader.getEventType();
            if(currentCursor == XMLStreamConstants.START_ELEMENT){
                String tagName = reader.getLocalName();
                if(tagName.equals(elementName)){
                    String attr = reader.getAttributeValue(namespaceURI, "class");
                    if(attr == null){
                        attr = "";
                    }
                    
                    if(attr.equals(atrrName)){
                        String result = "";
                        reader.next();
                        
                        result = reader.getText();
                        
                        reader.nextTag();
                        return result;
                    }
                }
            }
            reader.next();
        }
        return null;
    }
    
    public static String getValuesOfElement(XMLStreamReader reader,String target,String Class,String context) 
            throws XMLStreamException{
        String result = "";
        
        if (reader != null){
            while(reader.hasNext()){
                int currentCursor = reader.next();
                if(currentCursor == XMLStreamConstants.START_ELEMENT){
                    String tagName = reader.getLocalName();
                    if(tagName.equals(target)){
                        String attr = reader.getAttributeValue("", "class");
                        if(attr == null){
                            attr = "";
                        }
                        if(attr.equals(Class)){
                            String startElement = "";
                            while(reader.hasNext()){
                                reader.next();
                                if(reader.isStartElement()){
                                    startElement = reader.getLocalName();
                                }else if(reader.isCharacters()){
                                    result += reader.getText();
                                }else if(reader.isEndElement()){
                                    String tmp = reader.getLocalName();
                                    if(tmp.equals(startElement)){
                                        startElement = "";
                                    }else if(tmp.equals(tagName)){
                                        if(!result.contains(context)){
                                            result = "";
                                        }else{
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    private static String getImg(XMLStreamReader reader,Config config) 
            throws XMLStreamException{
        String result = "";
        
        if (reader != null){
            while(reader.hasNext()){
                int currentCursor = reader.next();
                if(currentCursor == XMLStreamConstants.START_ELEMENT){
                    String tagName = reader.getLocalName();
                    if(tagName.equals(config.getImgElementMovie())){
                        String attr = getAtttrNode(reader, tagName, "", "class");
                        if(attr == null){
                            attr = "";
                        }
                        if(attr.equals(config.getImgClassMovie())){
                            result = getAtttrNode(reader,tagName,"", config.getImgTargetMovie());
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }
    private static List<String> getCategoryType(XMLStreamReader reader,String target,String Class) 
            throws XMLStreamException{
        List<String> result = new ArrayList<String>();
        
        if (reader != null){
            while(reader.hasNext()){
                int currentCursor = reader.next();
                if(currentCursor == XMLStreamConstants.START_ELEMENT){
                    String tagName = reader.getLocalName();
                    if(tagName.equals(target)){
                        String attr = reader.getAttributeValue("","class");
                        if(attr == null){
                            attr = "";
                        }
                        if(attr.equals(Class)){
                            try {
                                result = readCategory(reader,target);
                                return result;
                            } catch (Exception ex) {
                                Logger.getLogger(StaxUtils.class.getName()).log(Level.SEVERE,null,ex);
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    private static List<String> readCategory(XMLStreamReader reader,String target) 
            throws XMLStreamException{
        List<String> result = new ArrayList<>();
        
        if (reader != null){
            while(reader.hasNext()){
                int currentCursor = reader.next();
                if(currentCursor == XMLStreamConstants.START_ELEMENT){
                    String tagName = reader.getLocalName();
                    if(tagName.equals("a")){
                        String cateName = getTextContent(reader, "a", "", "");
                        result.add(cateName);
                    }
                }
                if(currentCursor == XMLStreamConstants.END_ELEMENT){//End of element category will end
                    String tagName = reader.getLocalName();
                    if(tagName.equals(target)){
                        break;
                    }
                }
            }
        }
        return result;
    }
    
}