<%-- 
    Document   : search
    Created on : Mar 24, 2019, 9:24:07 PM
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
        <title>Search Page</title>
    </head>
    <body>
        <div id="containerRoot">
            <div>
                <jsp:include page="_layoutHeader.jsp"/>  
            </div>
            <div id="container">
                <div id="leftside">
                    <div class="clear2">
                    </div>
                    <div id="submenu">
                        <div id="subcontent">
                            <div id="tab-trending">
                                <script>
                                    octopusTopMovie.init(0,20,'resources/movie/searchMoviesByName?name=${requestScope.keyword}&');
                                </script>
                            </div>
                            <div id="prev" class="inactive" style="text-align: left; font-size: 16px">
                                <a>Prev...</a>
                            </div>
                            <div id="next" style="text-align: right; font-size: 16px" onclick="octopusTopMovie.loadNext(1,20);">
                                <a>Next...</a>
                            </div>
                        </div>
                    </div>
                </div> 
            </div>
        </div>
    </body>
</html>
