/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

import sample.utils.TextUtils;

/**
 *
 * @author DELL
 */
public class Category {
    private String name;
    private String href;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        
        this.name = TextUtils.unescapeHTML(name);
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href,String uri) {
        href = TextUtils.fixedHrefInHtml(href, uri);
        this.href = href;
    }
}
