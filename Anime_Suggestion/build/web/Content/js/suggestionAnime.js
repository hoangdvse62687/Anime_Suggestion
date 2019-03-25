/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var suggestionAnimeModel = {
    movies: null,
    xsl: null
};

var suggestionAnimeView = {
    init:function(){
        suggestionAnimeView.render();
    },
    render: function(){
        if(!suggestionAnimeModel.movies){
            return;
        }
        var parser = new DOMParser();
        var xmlDom = parser.parseFromString(octopusSuggestionAnime.getXML(),"text/xml");
        var xslDom = parser.parseFromString(octopusSuggestionAnime.getXSL(),"text/xml");
        var cateDom = parser.parseFromString(octopusCategory.getCategoryXML(),"text/xml");
        if(cateDom){
          var cates = xmlDom.getElementsByTagName("nameCate");
          var cateNames = cateDom.getElementsByTagName("name");
          fillValueForCate(cates,cateNames);
        }
        var proc = new XSLTProcessor();
        proc.importStylesheet(xslDom);
        var fragment = proc.transformToFragment(xmlDom,document);
        document.getElementById("AnimeSuggestion").appendChild(fragment);
    }
};

var octopusSuggestionAnime ={
    init:function(){
        octopusSuggestionAnime.loadXSL();
        octopusSuggestionAnime.loadXML();
        var tracking = new trackingChange(suggestionAnimeModel,suggestionAnimeView.init);
    },
    loadXML: function(cateName1,cateName2){
        var cateName1,cateName2;
        var animeCache = new AnimeSuggestionStorage();
        if(animeCache.count() > 2){
            var array = animeCache.getAll();
            cateName1 = array[animeCache.count() - 2].cateName;
            cateName2 = array[animeCache.count() - 1].cateName;
        }else{
            return;
        }
        var url = "resources/mappingMovie/getMovieSuggestion?cateName1=" + cateName1 + "&cateName2=" + cateName2;
        var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              suggestionAnimeModel.movies = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", url, true);
        xhttp.send();
    },
    loadXSL: function(){
        var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              suggestionAnimeModel.xsl = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", "Content/source/suggestionAnime.xsl", false);
        xhttp.send();
    },
    getXML: function(){
        return suggestionAnimeModel.movies;
    },
    getXSL: function(){
        return suggestionAnimeModel.xsl;
    }
};
