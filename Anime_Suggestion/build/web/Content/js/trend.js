/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var positionEachPage = 771.25;
var ele = document.getElementsByClassName("items");

var Movie = function(id,name,episode,imgHref){
    var self = this;
    self.id = id;
    self.name = name;
    self.episode = episode;
    self.imgHref = imgHref;
    
    return self;
};

var trendModel = {
    movies: []
};

var trendListView = {
  init: function(){
      document.getElementsByClassName("prev")[0].addEventListener("click",trendListView.prevTrend);
      document.getElementsByClassName("next")[0].addEventListener("click",trendListView.nextTrend);
      trendListView.render();
  },
  render:function(){
      var self = this;
      var trends = octopusTrend.getListTrend();
      var tmp = [];
      trends.forEach(function(item,index){
          if(tmp.length < 5){
              tmp.push(item);
          }
          
          if(tmp.length == 5){
              self.appendTrends(tmp);
              tmp = [];
          }
          
          if(index == trends.length - 1){
              self.appendTrends(tmp);
          }
      });
  },
  appendTrends: function(movies){
    var element = document.createElement("div");
    if(movies){
        movies.forEach(function(item,index){
            //a element
            var aEle = document.createElement("a");
            aEle.setAttribute("href","Anime?id=" + item.id);
            aEle.setAttribute("title","Tập " + item.episode);
            element.appendChild(aEle);
            
            //image element
            var imgEle = document.createElement("img");
            imgEle.setAttribute("width","130px");
            imgEle.setAttribute("height","150px");
            imgEle.setAttribute("src","Content/images/" + item.imgHref);
            aEle.appendChild(imgEle);
            
            //<br>
            aEle.appendChild(document.createElement("br"));
            
            //Text
            aEle.innerHTML += item.name;
            
            //<br>
            aEle.appendChild(document.createElement("br"));
            
            //span
            var spanEle = document.createElement("span");
            spanEle.innerHTML = "Tập " + item.episode;
            spanEle.setAttribute("class","textDark");
            aEle.appendChild(spanEle);
            
            //whitespace
            element.innerHTML += "&nbsp;&nbsp;&nbsp;";
        });
    }
    ele[0].appendChild(element);
  },
  prevTrend : function(){
        var count = ele[0].childElementCount;
        var leftPosition = ele[0].getAttribute("style");
        var temp = leftPosition.replace("left: ","");
        temp = temp.replace("px;","");
        var position = parseFloat(temp);

        var currentPage = position / positionEachPage;
        currentPage = Math.abs(currentPage);
        if(currentPage <= count){
            ele[0].setAttribute("style","left: " + (currentPage - 1) * (-positionEachPage) + "px;");
            document.getElementsByClassName("navi")[0].innerText = "Page " + (currentPage);
            document.getElementsByClassName("next")[0].setAttribute("style","display: block");
        }

        if(currentPage === 1){
            document.getElementsByClassName("prev")[0].setAttribute("style","display: none");
        }
    },
  nextTrend : function(){
    var count = ele[0].childElementCount;
    var leftPosition = ele[0].getAttribute("style");
    var temp = leftPosition.replace("left: ","");
    temp = temp.replace("px;","");
    var position = parseFloat(temp);
    
    var currentPage = position / positionEachPage;
    currentPage = Math.abs(currentPage);
    if(currentPage === count - 1){
        
    }else if(currentPage >= 0){
        ele[0].setAttribute("style","left: " + (currentPage + 1) * (-positionEachPage) + "px;");
        document.getElementsByClassName("navi")[0].innerText = "Page " + (currentPage + 2);
        document.getElementsByClassName("prev")[0].setAttribute("style","display: block");
    }
    
    if(currentPage === count-2){
        document.getElementsByClassName("next")[0].setAttribute("style","display: none");
    }
    
    if(ele[0].childElementCount <= 1){
        document.getElementsByClassName("next")[0].setAttribute("style","display: none");
    }
},
};

var octopusTrend = {
  init:function(){
      var self = this;
      self.loadTrend(trendModel.movies);
      var tracking = new trackingChange(trendModel,trendListView.init);
//      setTimeout(function(){
//          trendListView.init();
//      },300);
  },
  loadTrend: function(movies){
        var self = this;
        var fileTopDoc = "Content/source/topmovie.xml";
        var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              var childs = data.responseXML.getElementsByTagName("movie");
              for(var i = 0;i < childs.length; i++){
                movies.push(self.convertTrend(childs[i]));
              }
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", fileTopDoc, true);
        xhttp.send();
  },
  convertTrend: function(node){
      if(node == null){
        return;
      }
      if(node.tagName === "movie"){
        var movie = new Movie();
        movie.id = node.getElementsByTagName("id")[0].innerHTML;
        movie.name = node.getElementsByTagName("name")[0].innerHTML;
        movie.episode = node.getElementsByTagName("episode")[0].innerHTML;
        movie.imgHref = node.getElementsByTagName("img")[0].innerHTML;
        return movie;
     }
  },
  getListTrend: function(){
      return trendModel.movies;
  },
};

octopusTrend.init();

