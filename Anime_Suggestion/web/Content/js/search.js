/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var searchMovie = function(id,name){
    var self = this;
    self.id = id;
    self.name = name;
    return self;
};
var searchModel = {
    movies:[]
};

var searchView = {
    init:function(){
        document.getElementById("keyword").addEventListener("keydown",function(e){
            var value = document.getElementById("keyword").value + String.fromCharCode(e.keyCode);
            if(e.keyCode == 13){
                if(value.length > 2)
                    window.location.href = "Search?name="+value+"&page=0&pageSize=20";
            }
            
            if(value.length > 2){
                var resultHTML = document.getElementById("result_box");
                var checkLoaderExist = document.getElementById("loader");
                if(!checkLoaderExist){
                    resultHTML.innerHTML = "";
                    resultHTML.innerHTML += "<span id='loader' class='active'></span>";
                }
                octopusSearch.setXML([]);
                octopusSearch.loadXML(value,0,10);
                var tracking = new trackingChange(octopusSearch.getSearchModel(),function(){
                    searchView.renderSearch();
                });
            }else{
                document.getElementById("result_box").innerHTML = "";
            }
        });
        document.getElementById("imgSearch").addEventListener("click",function(e){
            var value = document.getElementById("keyword").value;
            if(value.length > 2){
                window.location.href = "Search?name="+value+"&page=0&pageSize=20";
            }
        });
    },
    renderSearch: function(){
        var parser = new DOMParser();
        var xmlDom = parser.parseFromString(octopusSearch.getXML(),"text/xml");
        var data = xmlDom.getElementsByTagName("movieDto");
        var results = "";
        for(var i = 0; i < data.length;i++){
            var result = "";
            result += "<span>";
            result += "<a href='Anime?id="+data[i].getElementsByTagName("id")[0].textContent+"'>";
            result += data[i].getElementsByTagName("name")[0].textContent;
            result += "</a>";
            result += "</span>";
            result += "<br/>";
            results += result;
        }
        if(xmlDom.length > 10)
            results += "<a href='Search?name="+document.getElementById("keyword").value+"&page=0&pageSize=20'> Xem Thêm </a>";
        document.getElementById("result_box").innerHTML = results;
    },
};

var octopusSearch = {
    init:function(){
        searchView.init();
    },
    loadXML:function(name,page,pageSize){
        var query = "resources/movie/searchMoviesByName?name="+name
        +"&page=" + page + "&pageSize=" + pageSize;
        var xhttp = GetXmlHttpObject();
        handlerXMLRequest(xhttp,
          function callSuccess(data){
              searchModel.movies = data.responseText;
          },
          function callError(msg){
              
          },
          function finalCall(){
          });
        xhttp.open("GET", query, true);
        xhttp.send();
    },
    setXML:function(value){
        searchModel.movies = value;
    },
    getXML:function(){
        return searchModel.movies;
    },
    getSearchModel: function(){
        return searchModel;
    }
};



