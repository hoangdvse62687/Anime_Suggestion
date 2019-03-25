/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author DELL
 */
public class TextUtils implements Serializable{
    private static HashMap<String,String> htmlEntities;
  static {
    htmlEntities = new HashMap<String,String>();
    htmlEntities.put("&aacute;","á"); htmlEntities.put("&Aacute;","Á");
    htmlEntities.put("&agrave;","à"); htmlEntities.put("&Agrave;","À");
    htmlEntities.put("&acirc;","â") ; htmlEntities.put("&auml;","ä");
    htmlEntities.put("&Auml;","Ä")  ; htmlEntities.put("&Acirc;","Â");
    htmlEntities.put("&aring;","å") ; htmlEntities.put("&Aring;","Å");
    htmlEntities.put("&ccedil;","ç"); htmlEntities.put("&Ccedil;","Ç");
    htmlEntities.put("&eacute;","é"); htmlEntities.put("&Eacute;","É" );
    htmlEntities.put("&egrave;","è"); htmlEntities.put("&Egrave;","È");
    htmlEntities.put("&ecirc;","ê") ; htmlEntities.put("&Ecirc;","Ê");
    htmlEntities.put("&euml;","ë")  ; htmlEntities.put("&Euml;","Ë");
    htmlEntities.put("&iuml;","ï")  ; htmlEntities.put("&Iuml;","Ï");
    htmlEntities.put("&ocirc;","ô") ; htmlEntities.put("&Ocirc;","Ô");
    htmlEntities.put("&ouml;","ö")  ; htmlEntities.put("&Ouml;","Ö");
    htmlEntities.put("&Ugrave;","Ù"); htmlEntities.put("&ucirc;","û");
    htmlEntities.put("&Ucirc;","Û") ; htmlEntities.put("&uuml;","ü");
    htmlEntities.put("&iacute;","í"); htmlEntities.put("&Iacute;","Í");
    htmlEntities.put("&igrave;","ì"); htmlEntities.put("&Igrave;","Ì");
    htmlEntities.put("&yacute;","ý"); htmlEntities.put("&Yacute;","Ý");
    htmlEntities.put("&uacute;","ú"); htmlEntities.put("&Uacute;","Ú");
    htmlEntities.put("&otilde;","õ"); htmlEntities.put("&Otilde;","Õ");
    htmlEntities.put("&Uuml;","Ü")  ;
  }
  
    public static String refineHtml(String src){
        src = getBody(src);
        src = removeMiscellaneousTags(src);
        
        XmlSyntaxChecker xmlSyntaxChecker = new XmlSyntaxChecker();
        src = xmlSyntaxChecker.check(src);
        src = getBody(src);
        return src;
    }
    
    private static String getBody(String src){
        String result = src;
        
        String expression = "<body.*?</body>";
        Pattern pattern = Pattern.compile(expression);
        
        Matcher matcher = pattern.matcher(result);
        
        if(matcher.find()){
            result = matcher.group(0);
        }
        return result;
    }
    
    private static String removeMiscellaneousTags(String src){
        String result = src;
        
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");
        
        return result;
    }
    
    private static String getString(InputStream is){
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        
        try(BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(is, "UTF-8"))){
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        }catch(IOException ex){
            Logger.getLogger(TextUtils.class.getName()).log(Level.SEVERE,null,ex);
        }
        return stringBuilder.toString();
    }
    
    public static InputStream fixWellform(InputStream is) 
            throws UnsupportedEncodingException, FileNotFoundException, IOException{
        
        String src = getString(is);
        String outputStr = refineHtml(src);
        return new ByteArrayInputStream(outputStr.getBytes(StandardCharsets.UTF_8));
    }
    
    public static final String unescapeHTML(String source) {
      if(source.isEmpty()){
          return "";
      }
      int i, j;

      boolean continueLoop;
      int skip = 0;
      do {
         continueLoop = false;
         i = source.indexOf("&", skip);
         if (i > -1) {
           j = source.indexOf(";", i);
           if (j > i) {
             String entityToLookFor = source.substring(i, j + 1);
             String value = (String) htmlEntities.get(entityToLookFor);
             if (value != null) {
               source = source.substring(0, i)
                        + value + source.substring(j + 1);
               continueLoop = true;
             }
             else if (value == null){
                skip = i+1;
                continueLoop = true;
             }
           }
         }
      } while (continueLoop);
      return source;
  }
    
    public static final String fixedHrefInHtml(String href,String uri){
        
        if(!href.contains(uri)){
            href = uri + href.replace("../", "/").replace("./", "/");
        }
        
        return href;
    }
    
    public static final long getNumberInString(String src,int lengthGet){
        String tmp = getNumberInString(src).trim();
        long result = 0;
        String str = "";
        if(tmp != null){
            int count = 0;
            for(int i = 0; i < tmp.length();i++){
                if(Character.isDigit(tmp.charAt(i))){
                    str += tmp.charAt(i);
                    count++;
                    if(count == lengthGet){
                        break;
                    }
                }else{
                    count = 0;
                    str = "";
                }
            }
        }
        try{
            result = Long.parseLong(str);
        }catch(NumberFormatException ex){
            
        }
        return result;
    }
    
    public static final String getNumberInString(String src){
        String result = "";
        src = src.replace(",", "");// remove colon for milion
        src = src.replace(".", "");
        if(src != null){
            for(int i = 0; i < src.length();i++){
                if(Character.isDigit(src.charAt(i))){
                    result += src.charAt(i);
                }else{
                    result += " ";// clear 
                }
            }
        }
        
        return result;
    }
}

