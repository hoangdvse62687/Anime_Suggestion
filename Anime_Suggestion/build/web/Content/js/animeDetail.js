/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var animeDetailModel = {
    xml: null,
    xsl: null
};

var animeDetailView = {
   init: function(){
       animeDetailView.render();
   },
   render: function(){
       var parser = new DOMParser();
       var xmlDom = parser.parseFromString(octopusAnimeDetail.getAnimeDetailXML(),"text/xml");
       var xslDom = parser.parseFromString(octopusAnimeDetail.getAnimeDetailXsl(),"text/xml");
       var cateDom = parser.parseFromString(octopusCategory.getCategoryXML(),"text/xml");
       if(cateDom){
          var cates = xmlDom.getElementsByTagName("nameCate");
          var cateNames = cateDom.getElementsByTagName("name");
          fillValueForCate(cates,cateNames);
          //add cateName
          var animeCache = new AnimeSuggestionStorage();
          for(var i = 0; i < cates.length; i++){
              if(animeCache.find(cates[i].textContent) == 1){//found
                  animeCache.increseCount(cates[i].textContent);
              }else{
                  animeCache.add(cates[i].textContent);
              }
          }
          animeCache.save();
      }
      var proc = new XSLTProcessor();
      proc.importStylesheet(xslDom);
      var fragment = proc.transformToFragment(xmlDom,document);
      document.getElementById("container").appendChild(fragment);
   }
};

var octopusAnimeDetail = {
    init: function(id){
        octopusAnimeDetail.loadDetailXSL("Content/source/AnimeDetail.xsl");
        octopusAnimeDetail.loadDetailXML("resources/movie/getMovieById?id="+id);
        animeDetailView.init();
    },
    loadDetailXML: function(sourceLink){
        var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              animeDetailModel.xml = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", sourceLink, false);
        xhttp.send();
    },
    loadDetailXSL: function(sourceLink){
        var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              animeDetailModel.xsl = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", sourceLink, false);
        xhttp.send();
    },
    getAnimeDetailXML: function(){
        return animeDetailModel.xml;
    },
    getAnimeDetailXsl: function(){
        return animeDetailModel.xsl;
    }
};