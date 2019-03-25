/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.objectConfig.Category;
import sample.objectConfig.Config;
import sample.objectConfig.Movie;
import sample.objectConfig.WebData;

/**
 *
 * @author DELL
 */
public class DomUtils implements Serializable{
    public static Document parseDOMFromFile(InputStream is) 
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);
        return doc;
    }
    
    public static XPath createXPath(){
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        
        return xpath;
    }
    
    public static WebData getCategories(Config config) 
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
        InputStream is = InternetUtils.makeConnection(config.getUri());
        is = TextUtils.fixWellform(is);
        WebData webData = new WebData(new ArrayList<Category>());
        List<String> listCategories = new ArrayList<String>();
        Document doc = parseDOMFromFile(is);
        if(doc != null){
            XPath xPath = createXPath();
            String query = "//a[translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"
                    + " = translate('"
                    + config.getCategoryContext() + "', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')]/parent::li"
                    + "/"
                    + config.getParentElementCategory() +"[@class='" 
                    + config.getParentClassCategory() + "']//a";
            if(config.getParentClassCategory().isEmpty()){
                query = "//a[translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' , 'abcdefghijklmnopqrstuvwxyz')"
                    + " = translate('"
                    + config.getCategoryContext() + "', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')]/parent::li"
                    +"/"
                    + config.getParentElementCategory() + "[not(@class)]//a";
            }
            NodeList listCategory = (NodeList) xPath.evaluate(query, doc,XPathConstants.NODESET);
            for(int i = 0;i < listCategory.getLength();i++){
                Node tmp = listCategory.item(i);
                query = "text()[1]";
                String name = (String)xPath.evaluate(query, tmp,XPathConstants.STRING);
                query = "@href";
                String href = (String)xPath.evaluate(query, tmp,XPathConstants.STRING);
                
                if(href.equals(config.getUri()) || !href.contains("/")){// not allow href home and not type link
                
                }else if(!listCategories.contains(href) 
                        && !config.getForbiddenLinkCategories().contains(href)){//not dumplicate the link
                    Category category = new Category();
                    category.setHref(href, config.getUri());
                    category.setName(name);
                    if(!CommonUtils.getCategory(category.getName()).isEmpty()){// check it in my category
                        listCategories.add(category.getHref());
                        webData.getCategory().add(category);
                        System.out.println("Name category: " + category.getName());
                        System.out.println("Href Category: " + category.getHref()); 
                    }
                }
            }
            
            //read top movie in week
            webData.getListMovies().addAll(getTopMovieInWeek(doc,config));
        }
        if(is != null){
            is.close();
        }
        return webData;
    }
    
    private static List<Movie> getTopMovieInWeek(Document doc,Config config) 
            throws XPathExpressionException{
        List<Movie> result = new ArrayList<Movie>();
        
        if(doc != null){
            XPath xPath = createXPath();
            String query = "//" + config.getTopWeekTargetUri() + "[@class='"
                    + config.getTopWeekClassUri() +"']/li/a";
            if(config.getTopWeekNameCLassParent() != null
                    && config.getTopWeekValueClassParent() != null
                    && config.getTopWeekElementName() != null){
                query = "//" + config.getTopWeekTargetUri() + "[@class='"
                    + config.getTopWeekClassUri() +"']//parent::"
                    + config.getTopWeekElementName() + "[@" + config.getTopWeekNameCLassParent() + "='" + config.getTopWeekValueClassParent() + "']"
                    + "/" + config.getTopWeekTargetUri() + "/li//a";
            }
            NodeList listTopMovie = (NodeList) xPath.evaluate(query, doc,XPathConstants.NODESET);
            for (int i = 0; i < listTopMovie.getLength(); i++) {
                Movie movie = new Movie();
                String hrefName = "";
                String img = "";
                String name = "";
                String amountOfView = "";
                String episode = "";
                Node tmp = listTopMovie.item(i);
                query = "@href";
                String href = (String)xPath.evaluate(query, tmp,XPathConstants.STRING);
                if(href.equals(config.getUri()) || !href.contains("/")){// not allow href home and not type link
                
                }else if(!result.contains(href)){
                    hrefName = TextUtils.fixedHrefInHtml(href, config.getUri());
                    movie.setHrefMovie(hrefName, config.getUri());
                    System.out.println("Top Movie: " + hrefName);
                }
                
                query = ".//*[@class='"+config.getTopWeekImgClass()+"']/@"+config.getTopWeekImgClassTarget();
                img = TextUtils.fixedHrefInHtml((String)xPath.evaluate(query, tmp,XPathConstants.STRING), config.getUri());
                movie.setImg(img);
                System.out.println("Top Movie Img: " + movie.getImg());
                
                query = ".//"+config.getTopWeekNameElement() + "[@class='"+ config.getTopWeekClassName()+"']/text()";
                name = (String)xPath.evaluate(query, tmp,XPathConstants.STRING);
                System.out.println("Top Movie Name: " + name);
                movie.setName(name);
                
                query = ".//"+config.getTopWeekCommentElement() + "[@class='"+config.getTopWeekCommentClass()+"']/text()";
                amountOfView = (String)xPath.evaluate(query, tmp,XPathConstants.STRING);
                movie.setMountOfViewed(amountOfView);
                movie.setIsTopMovie(true);
                System.out.println("Top Movie Viewed: "+amountOfView);
                
                result.add(movie);
            }
        }
        
        return result;
    }
}
