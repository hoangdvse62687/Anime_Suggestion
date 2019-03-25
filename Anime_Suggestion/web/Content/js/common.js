/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var uri = window.location.origin + "/Anime_Suggestion/";
function GetXmlHttpObject(){
    var xmlHttp = null;
    try{
        xmlHttp = new XMLHttpRequest();
    }catch(e){
        try{
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }catch (e){
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}
function handlerXMLRequest(xhttp,callSuccess,callError,finalCall){
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
          if (this.status == 200) {callSuccess(this);}
          if (this.status == 404) {callError(this.responseText);}
          finalCall();
        }
      } 
}
function includeHTML() {
  var z, i, elmnt, file, xhttp;
  z = document.getElementsByTagName("*");
  for (i = 0; i < z.length; i++) {
    elmnt = z[i];
    file = elmnt.getAttribute("include");
    if (file) {
      xhttp = GetXmlHttpObject();
      handlerXMLRequest(xhttp,
      function callSuccess(data){
          elmnt.innerHTML = data.responseText;
      },
      function callError(msg){
          
      },
      function finalCall(){
          elmnt.removeAttribute("include");
          includeHTML();
      });
      
      xhttp.open("GET", file, true);
      xhttp.send();
      
      return;
    }
  }
}
function clone(obj) {
    // Handle the 3 simple types, and null or undefined
    if (null == obj || "object" != typeof obj) return obj;

    // Handle Date
    if (obj instanceof Date) {
        var copy = new Date();
        copy.setTime(obj.getTime());
        return copy;
    }

    // Handle Array
    if (obj instanceof Array) {
        var copy = [];
        for (var i = 0, len = obj.length; i < len; i++) {
            copy[i] = clone(obj[i]);
        }
        return copy;
    }

    // Handle Object
    if (obj instanceof Object) {
        var copy = {};
        for (var attr in obj) {
            if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
        }
        return copy;
    }
}
var trackingChange = function(parent,fn){
    var self = this;
    self.oldValue = clone(parent.movies);
    
    var detect = function(){
      if(self.oldValue != parent.movies){
          fn();
          self.oldValue = clone(parent.movies);
          clearInterval(self.tracking);
      }  
    };
    self.tracking = setInterval(function(){ detect(); }, 100);
}

function scrollToTop(){
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

function fillValueForCate(cates,cateNames){
    if(cates && cateNames){
        for(var i = 0; i < cates.length;i++){
              for(var j = 0; j < cateNames.length;j++){
                  if(cates[i].textContent === cateNames[j].textContent){
                    cates[i].setAttribute("value",cateNames[j].getAttribute("value"));
                  }
              }
          }
    }
}
var Cate = function(cateName,count){
    var self = this;
    self.cateName = cateName;
    self.count = count;
    return self;
};
var AnimeSuggestionStorage = function(){
    var self = this;
    var STORAGE_KEY = "DetailStorage";
    var STORAGE_TYPE = localStorage;
    var parser = new DOMParser();
    
    self.getAll = function(){
        
        var results = [];
        if(!STORAGE_TYPE.getItem(STORAGE_KEY)){
            return results;
        }
        var xmlDom = parser.parseFromString(STORAGE_TYPE.getItem(STORAGE_KEY),"text/xml");
        var cateEle = xmlDom.getElementsByTagName("cate");
        for(var i = 0; i < cateEle.length ; i++){
            var cate = new Cate(cateEle[i].getElementsByTagName("cateName")[0].textContent,cateEle[i].getElementsByTagName("count")[0].textContent);
            results.push(cate);
        }
        return results;
    };
    
    var handles = self.getAll() || [];
    
    self.count = function(){
        return handles.length;
    };
    
    self.find = function(cateName){
        for(var i = 0;i < handles.length;i++){
            if(handles[i].cateName == cateName){
                return 1;
            }
        }
        return -1;
    };
    
    self.add = function(cateName){
        var offset = self.find(cateName);
        if(offset == -1){
            var handle = new Cate(cateName,1);
            handles.push(handle);
        }
        return null;
    };
    self.increseCount = function(cateName){
        var offset = self.find(cateName);
        if(offset == 1){
            for(var i = 0; i < handles.length;i++){
                if(handles[i].cateName ==  cateName){
                    handles[i].count = parseInt(handles[i].count) + 1;
                }
            }
        }
    };
    
    self.save = function() {
        if(handles.length > 0){
            handles.sort(function(a,b){
                return parseInt(a.count) - parseInt(b.count);
            });
        }
        var save = "<cates>";
        for(var i = 0; i < handles.length;i++){
            save += "<cate>";
            save += "<cateName>";
            save += handles[i].cateName;
            save += "</cateName>";
            save += "<count>";
            save += handles[i].count;
            save += "</count>";
            save += "</cate>";
        }
        save += "</cates>";
        STORAGE_TYPE.setItem(STORAGE_KEY, save);
    };
};