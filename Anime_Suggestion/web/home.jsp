<%-- 
    Document   : home.jsp
    Created on : Mar 19, 2019, 11:54:31 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="Content/images/short-logo.jpg" type="image/x-icon" />
        <script src="Content/js/common.js" type="text/javascript"></script>
        <script src="Content/js/topMovie.js" type="text/javascript"></script>
        <script src="Content/js/suggestionAnime.js" type="text/javascript"></script>
        <title>Home Page</title> 
    </head>
    <body>
        <div id="containerRoot">
            <div>
                <jsp:include page="_layoutHeader.jsp"/>  
            </div>
            <div id="container">
                <div id="leftside">
                    <div class="banner">
                        <div id="AnimeSuggestion" class="details" style="position: relative; line-height: 18px;">
                            <script>
                                octopusSuggestionAnime.init();
                            </script>
                        </div>
                    </div>
                    <div class="clear">
                    </div>
                    
                    <div class="bigBarContainer">
                        <jsp:include page="trend.html"/>  
                    </div>
                    <div class="clear2">
                    </div>
                    <div id="submenu">
                        <div id="subcontent">
                            <div id="tab-trending">
                                <script>
                                    octopusTopMovie.init(0,10,'resources/movie/getTopMovies?');
                                </script>
                            </div>
                            <div style="text-align: right; font-size: 16px">
                                <a href="topMovie.jsp">More...</a>
                            </div>
                        </div>
                    </div>
                </div> 
            </div>
        </div>
    </body>
</html>
