/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var TopMovie = function(id,name,episode,img,rateInSite){
    var self = this;
    self.id = id;
    self.name = name;
    self.episode = episode;
    self.imgHref = img;
    self.rateInSite = rateInSite;
    return self;
};

var topModel = {
    movies: null,
    xsl: null,
    page:0,
    pageSize: 20,
    resourcesLink: null
};

var topMovieListView = {
  init: function(){
      topMovieListView.render();
  },
  render: function(){
      var parser = new DOMParser();
      var xmlDom = parser.parseFromString(octopusTopMovie.getTopMovieXml(),"text/xml");
      var xslDom = parser.parseFromString(octopusTopMovie.getTopMovieXsl(),"text/xml");
      var cateDom = parser.parseFromString(octopusCategory.getCategoryXML(),"text/xml");
      if(cateDom){
          var cates = xmlDom.getElementsByTagName("nameCate");
          var cateNames = cateDom.getElementsByTagName("name");
          fillValueForCate(cates,cateNames);
      }
      var proc = new XSLTProcessor();
      proc.importStylesheet(xslDom);
      proc.setParameter("","page",octopusTopMovie.getPage() * octopusTopMovie.getPageSize());
      var fragment = proc.transformToFragment(xmlDom,document);
      document.getElementById("tab-trending").appendChild(fragment);
      
  },
  renderNext:function(page,pageSize){
      var eleNext = document.getElementById("next");
      var elePrev = document.getElementById("prev");
      if(eleNext != null){
          var parser = new DOMParser();
          elePrev.setAttribute("class","active");
          //test isNext
          octopusTopMovie.loadTopMovie(page+1,pageSize);
          var trackingChangeNext = new trackingChange(topModel,function(){
             var xmlDom = parser.parseFromString(octopusTopMovie.getTopMovieXml(),"text/xml");
            var element = xmlDom.getElementsByTagName("movieDto");
            if(element.length > 0){
                eleNext.setAttribute("class","active");
                eleNext.setAttribute("onclick","octopusTopMovie.loadNext(" + (page+1) + "," + pageSize + ")");
            }else{
                eleNext.setAttribute("class","inactive");
            }
            if(page > 0){
                elePrev.setAttribute("onclick","octopusTopMovie.loadPrev(" + (page-1) + "," + pageSize + ")"); 
            }else{
                elePrev.setAttribute("class","inactive");
            }
          });
      }
  },
  renderPrev: function(page,pageSize){
      var eleNext = document.getElementById("next");
      var elePrev = document.getElementById("prev");
      if(elePrev != null){
          var parser = new DOMParser();
          eleNext.setAttribute("class","active");
          
          if(page != 0){
//            octopusTopMovie.loadTopMovie(page-1,pageSize);
//            var trackingChangePrev = new trackingChange(topModel,function(){
//               var xmlDom = parser.parseFromString(octopusTopMovie.getTopMovieXml(),"text/xml");
//                var element = xmlDom.getElementsByTagName("movieDto");
//                if(element.length > 0){
//                    elePrev.setAttribute("class","active");
//                    elePrev.setAttribute("onclick","octopusTopMovie.loadPrev(" + (page-1) + "," + pageSize + ")");
//                }else{
//                    elePrev.setAttribute("class","inactive");
//                }
//            });
              elePrev.setAttribute("class","active");
              elePrev.setAttribute("onclick","octopusTopMovie.loadPrev(" + (page-1) + "," + pageSize + ")");
          }else{
              elePrev.setAttribute("class","inactive");
          }
          eleNext.setAttribute("onclick","octopusTopMovie.loadNext(" + (page+1) + "," + pageSize + ")");
      }
  },
  clearRender: function(){
      var ele = document.getElementById("tab-trending");
      if(ele){
          ele.innerHTML = '';
      }
  }
};

var octopusTopMovie = {
  init: function(page,pageSize,resourcesLink){
     var self = this;
     topModel.resourcesLink = resourcesLink;
     self.loadXslTopMovie();
     self.loadTopMovie(page,pageSize);
     var tracking = new trackingChange(topModel,function(){
         topMovieListView.init();
         //prepare load next first
         topMovieListView.renderNext(page,pageSize);
     });
  },
  loadNext: function(page,pageSize){
      scrollToTop();
      topMovieListView.clearRender();
      if(topModel.xsl == null)
        octopusTopMovie.loadXslTopMovie();
      //topModel.movies = null;
      //octopusTopMovie.loadTopMovie(page,pageSize);
      topMovieListView.init();
      topMovieListView.renderNext(page,pageSize);
  },
  loadPrev: function(page,pageSize){
      topMovieListView.clearRender();
      if(topModel.xsl == null)
        octopusTopMovie.loadXslTopMovie();
      //topModel.movies = null;
      octopusTopMovie.loadTopMovie(page,pageSize);
      var tracking = new trackingChange(topModel,function(){
          topMovieListView.init();
          topMovieListView.renderPrev(page,pageSize);
      });
  },
  loadTopMovie: function(page,pageSize){
      var self = this;
      var fileTopDoc = topModel.resourcesLink + "page=" + (page*pageSize)+"&pageSize="+pageSize;
      topModel.page = page;
      topModel.pageSize = pageSize;
      var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              topModel.movies = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", fileTopDoc, true);
        xhttp.send();
  },
  loadXslTopMovie: function(){
      var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              topModel.xsl = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", "Content/source/TopMovie.xsl", false);
        xhttp.send();
  },
  getTopMovieXml: function(){
      return topModel.movies;
  },
  getTopMovieXsl: function(){
      return topModel.xsl;
  },
  getPage: function(){
      return topModel.page;
  },
  getPageSize: function(){
      return topModel.pageSize;
  },
};


