/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var categoryModel = {
    categoryXML : null,
    categoryXSL : null,
};

var categoryView = {
    init: function(){
        this.render();
    },
    render: function(){
        var parser = new DOMParser();
        var xmlDom = parser.parseFromString(categoryModel.categoryXML,"text/xml");
        var xslDom = parser.parseFromString(categoryModel.categoryXSL,"text/xml");
        var proc = new XSLTProcessor();
        proc.importStylesheet(xslDom);
        proc.setParameter("","Link","Category?cateName=");
        var fragment = proc.transformToFragment(xmlDom,document);
        document.getElementById("category").appendChild(fragment);
    },
};

var octopusCategory = {
    init: function(){
        var self = this;
        self.data;
        if(localStorage.categoryXML && localStorage.categoryXSL){
            categoryModel.categoryXML = localStorage.categoryXML;
            categoryModel.categoryXSL = localStorage.categoryXSL;
        }else{
            self.loadCategory("Content/source/Category.xml",self);
            categoryModel.categoryXML = self.data;
            localStorage.categoryXML = self.data;
            self.loadCategory("Content/source/Category.xsl",self);
            categoryModel.categoryXSL = self.data;
            localStorage.categoryXSL = self.data;
        }
        
        categoryView.init();
    },
    loadCategory: function(fileName,parent){
        var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              parent.data = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", fileName, false);
        xhttp.send();
    },
    getCategoryXML: function(){
        return categoryModel.categoryXML;
    },
    getCategoryXSL: function(){
        return categoryModel.categoryXSL;
    },
};


