/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import sample.constaints.SyntaxContaint;

/**
 *
 * @author DELL
 */
public class XmlSyntaxChecker implements Serializable{
    private Character quote;
    
    private String convert(Map<String,String> attributes){
        if(attributes.isEmpty()){
            return "";
        }
        
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,String> entry : attributes.entrySet()){
            String value = entry.getValue()
                    .replace("&", "&amp;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&apos;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;");
            builder.append(entry.getKey())
                    .append("=")
                    .append("\"")
                    .append(value)
                    .append("\"")
                    .append(" ");
        }
        String result = builder.toString().trim();
        if(!result.equals("")){
            result = " " + result;
        }
        
        return result;
    }
    
    public String check(String src){
        src = src + " ";
        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder();
        
        StringBuilder openTag = new StringBuilder();
        boolean isEmptyTag = false, isOpenTag = false, isCloseTag = false;
        StringBuilder closeTag = new StringBuilder();
        StringBuilder attrName = new StringBuilder();
        StringBuilder attrValue = new StringBuilder();
        Map<String,String> attributes = new HashMap<>();
        
        StringBuilder content = new StringBuilder();
        
        Stack<String> stack = new Stack<>();
        String state = SyntaxContaint.CONTENT;
        
        for(int i = 0; i < reader.length; i++){
            char c = reader[i];
            
            switch(state){
                case SyntaxContaint.CONTENT:
                    if(c == SyntaxContaint.LT){
                        state = SyntaxContaint.OPEN_BRACKET;
                        writer.append(content.toString()
                        .trim()
                        .replace("&", "&amp;"));
                    }else{
                        content.append(c);
                    }
                    break;
                case SyntaxContaint.OPEN_BRACKET:
                    if(SyntaxContaint.isStartTagChars(c)){
                        state = SyntaxContaint.OPEN_TAG_NAME;
                        
                        isOpenTag = true;
                        isCloseTag = false;
                        isEmptyTag = false;
                        
                        openTag.setLength(0);
                        openTag.append(c);
                    }else if(c == SyntaxContaint.SLASH){
                        state = SyntaxContaint.CLOSE_TAG_SLASH;
                        
                        isOpenTag = false;
                        isCloseTag = true;
                        isEmptyTag = false;
                    }
                    break;
                case SyntaxContaint.OPEN_TAG_NAME:
                    if(SyntaxContaint.isTagChars(c)){
                        openTag.append(c);
                    }else if(SyntaxContaint.isSpace(c)){
                        state = SyntaxContaint.TAG_INNER;
                        
                        attributes.clear();
                    }else if(c == SyntaxContaint.GT){
                        state = SyntaxContaint.CLOSE_BRACKET;
                    }else if(c == SyntaxContaint.SLASH){
                        state = SyntaxContaint.EMPTY_SLASH;
                    }
                    break;
                case SyntaxContaint.TAG_INNER:
                    if(SyntaxContaint.isSpace(c)){
                        
                    }else if(SyntaxContaint.isStartAttrChars(c)){
                        state = SyntaxContaint.ATTR_NAME;
                        
                        attrName.setLength(0);
                        attrName.append(c);
                    }else if(c == SyntaxContaint.GT){
                        state = SyntaxContaint.CLOSE_BRACKET;
                    }else if(c == SyntaxContaint.SLASH){
                        state = SyntaxContaint.EMPTY_SLASH;
                    }
                    break;
                case SyntaxContaint.ATTR_NAME:
                    if(SyntaxContaint.isAttrChars(c)){
                        attrName.append(c);
                    }else if(c == SyntaxContaint.EQ){
                        state = SyntaxContaint.EQUAL;
                    }else if(SyntaxContaint.isSpace(c)){
                        state = SyntaxContaint.EQUAL_WAIT;
                    }else {
                        if(c == SyntaxContaint.SLASH){
                            attributes.put(attrName.toString(), "true");
                            state = SyntaxContaint.EMPTY_SLASH;
                        }else if(c == SyntaxContaint.GT){
                            attributes.put(attrName.toString(), "true");
                            state = SyntaxContaint.CLOSE_BRACKET;
                        }
                    }
                    break;
                case SyntaxContaint.EQUAL_WAIT:
                    if(SyntaxContaint.isSpace(c)){
                        
                    }else if(c == SyntaxContaint.EQ){
                        state = SyntaxContaint.EQUAL;
                    }else{
                        if(SyntaxContaint.isStartAttrChars(c)){
                            attributes.put(attrName.toString(), "true");
                            state = SyntaxContaint.ATTR_NAME;
                            
                            attrName.setLength(0);
                            attrName.append(c);
                        }
                    }
                    break;
                case SyntaxContaint.EQUAL:
                    if(SyntaxContaint.isSpace(c)){
                        
                    }else if(c == SyntaxContaint.D_QUOT || c == SyntaxContaint.S_QUOT){
                        quote = c;
                        state = SyntaxContaint.ATTR_VALUE_Q;
                        
                        attrValue.setLength(0);
                    }else if(!SyntaxContaint.isSpace(c) && c != SyntaxContaint.GT){
                        state = SyntaxContaint.ATTR_VALUE_NQ;
                        
                        attrValue.setLength(0);
                        attrValue.append(c);
                    }
                    break;
                case SyntaxContaint.ATTR_VALUE_Q:
                    if(c != quote){
                        attrValue.append(c);
                    }else if(c == quote){
                        state = SyntaxContaint.TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case SyntaxContaint.ATTR_VALUE_NQ:
                    if(!SyntaxContaint.isSpace(c) && c != SyntaxContaint.GT){
                        attrValue.append(c);
                    }else if(SyntaxContaint.isSpace(c)){
                        state = SyntaxContaint.TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }else if(c == SyntaxContaint.GT){
                        state = SyntaxContaint.CLOSE_BRACKET;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case SyntaxContaint.EMPTY_SLASH:
                    if(c == SyntaxContaint.GT){
                        state = SyntaxContaint.CLOSE_BRACKET;
                        isEmptyTag = true;
                    }
                    break;
                case SyntaxContaint.CLOSE_TAG_SLASH:
                    if(SyntaxContaint.isStartTagChars(c)){
                        state = SyntaxContaint.CLOSE_TAG_NAME;
                        
                        closeTag.setLength(0);
                        closeTag.append(c);
                    }
                    break;
                case SyntaxContaint.CLOSE_TAG_NAME:
                    if(SyntaxContaint.isTagChars(c)){
                        closeTag.append(c);
                    }else if(SyntaxContaint.isSpace(c)){
                        state = SyntaxContaint.WAIT_END_TAG_CLOSE;
                    }else if(c == SyntaxContaint.GT){
                        state = SyntaxContaint.CLOSE_BRACKET;
                    }
                    break;
                case SyntaxContaint.WAIT_END_TAG_CLOSE:
                    if(SyntaxContaint.isSpace(c)){
                        
                    }else if(c == SyntaxContaint.GT){
                        state = SyntaxContaint.CLOSE_BRACKET;
                    }
                    break;
                case SyntaxContaint.CLOSE_BRACKET:
                    if(isOpenTag){
                        String openTagName = openTag.toString().toLowerCase();
                        
                        if(SyntaxContaint.INLINE_TAGS.contains(openTagName)){
                            isEmptyTag = true;
                        }
                        writer.append(SyntaxContaint.LT)
                                .append(openTagName)
                                .append(convert(attributes))
                                .append((isEmptyTag ? "/" : ""))
                                .append(SyntaxContaint.GT);
                        
                        attributes.clear();
                        
                        if(!isEmptyTag){
                            stack.push(openTagName);
                        }
                    }else if(isCloseTag){
                        String closeTagName = closeTag.toString().toLowerCase();
                        
                        if(!stack.isEmpty() && stack.contains(closeTagName)){
                            while(!stack.isEmpty() && !stack.peek().equals(closeTagName)){
                                writer.append(SyntaxContaint.LT)
                                        .append(SyntaxContaint.SLASH)
                                        .append(stack.pop())
                                        .append(SyntaxContaint.GT);
                            }
                            if(!stack.isEmpty() && stack.peek().equals(closeTagName)){
                                writer.append(SyntaxContaint.LT)
                                        .append(SyntaxContaint.SLASH)
                                        .append(stack.pop())
                                        .append(SyntaxContaint.GT);
                            }
                        }
                    }
                    
                        if(c == SyntaxContaint.LT){
                            state = SyntaxContaint.OPEN_BRACKET;
                        }else {
                            state = SyntaxContaint.CONTENT;
                            
                            content.setLength(0);
                            content.append(c);
                        }
                        break;
            }
        }
        if(SyntaxContaint.CONTENT.equals(state)){
                writer.append(content.toString().trim().replace("&", "&amp;"));
            }
            
            while(!stack.isEmpty()){
                writer.append(SyntaxContaint.LT)
                        .append(SyntaxContaint.SLASH)
                        .append(stack.pop())
                        .append(SyntaxContaint.GT);
            }
            
            return writer.toString();
    }
}

