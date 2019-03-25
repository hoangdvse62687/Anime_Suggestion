/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.objectConfig.Config;

/**
 *
 * @author DELL
 */
public class InternetUtils implements Serializable{
    
    public static void getListPagingUri(List<String> listUri,Config config,int pageSize,int pageStart,int maximumPage)
    throws MalformedURLException,IOException{
        if(listUri.isEmpty()){
            return;
        }
        if(pageSize == 0){
            pageSize = 1;//default
        }
        for(int i = 0;i < listUri.size(); i++){
            String uri = listUri.get(i);
            //Specific Name of file output in folder
            String origin = config.getUri();
            String cateUri = "";
            int page = pageStart;
            if(uri.contains(config.getFormatPagging())){
                int beginIndex = uri.lastIndexOf(origin) + origin.length();
                int lastIndex = uri.lastIndexOf(config.getFormatPagging());
                cateUri = uri.substring(beginIndex,lastIndex);
                String getPageNumber = uri.substring(lastIndex);
                page = Integer.parseInt(getPageNumber.replace(config.getEndformatPagging(), "")
                        .replace(config.getFormatPagging(), ""));
            }else{
                int beginIndex = uri.lastIndexOf(origin) + origin.length();
                cateUri = uri.substring(beginIndex);
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                Logger.getLogger(InternetUtils.class.getName()).log(Level.SEVERE,null,ex);
            }
            //End Specific Name of file output in folder
            InputStream is = null;
            try{
                is = makeConnection(uri);
            }catch(java.io.IOException ex){
                Logger.getLogger(InternetUtils.class.getName()).log(Level.SEVERE,null,ex);
                //web cannot handle it so stop getting data
                break;
            }
            BufferedReader in =
                new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            page += pageSize;
            if(page > maximumPage){
                break;
            }
            String testNextPageLink = cateUri + config.getFormatPagging() + page + config.getEndformatPagging();
            String shortenTestNextLink = config.getFormatPagging() + page + config.getEndformatPagging();
            String encodingTestNextLink = "";
            int testIndex = config.getFormatPagging().lastIndexOf("&");
            if(testIndex > -1){
                encodingTestNextLink = "&amp;" + config.getFormatPagging().replace("&", "") + page + config.getEndformatPagging();
            }
            while((inputLine = in.readLine()) != null){
                if(inputLine.contains(testNextPageLink)
                        || inputLine.contains(shortenTestNextLink)
                        || (!encodingTestNextLink.isEmpty() && inputLine.contains(encodingTestNextLink))){
                    String newUri = origin + cateUri + config.getFormatPagging() + page + config.getEndformatPagging();
                    String tmp = inputLine;
                    listUri.add(newUri);
                    System.out.println(newUri);
                    
                    //end after found the next page link
                    break;
                }
            }
        }
    }
    
    public static InputStream makeConnection(String uri) 
            throws MalformedURLException, IOException{
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        URL url = new URL(uri);
        URLConnection con = url.openConnection();
        con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
        InputStream is = con.getInputStream();
        
        return is;
    }
   
}
