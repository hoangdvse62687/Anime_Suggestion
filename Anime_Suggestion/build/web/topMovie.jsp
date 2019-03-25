<%-- 
    Document   : topMovie
    Created on : Mar 23, 2019, 2:18:51 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="Content/images/short-logo.jpg" type="image/x-icon" />
        <script src="Content/js/common.js" type="text/javascript"></script>
        <script src="Content/js/topMovie.js" type="text/javascript"></script>
        <title>Top Movie Page</title>
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
                                    <c:if test="${not empty requestScope.sourceLink}">
                                        octopusTopMovie.init(0,20,'${requestScope.sourceLink}');
                                    </c:if>
                                    <c:if test="${empty requestScope.sourceLink}">
                                        octopusTopMovie.init(0,20,'resources/movie/getTopMovies?');
                                    </c:if>
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
