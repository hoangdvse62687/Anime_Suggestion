<%-- 
    Document   : AnimeDetail
    Created on : Mar 23, 2019, 10:30:02 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="Content/images/short-logo.jpg" type="image/x-icon" />
        <script src="Content/js/common.js" type="text/javascript"></script>
        <script src="Content/js/animeDetail.js" type="text/javascript"></script>
        <title>Anime Detail</title>
    </head>
    <body>
        <div id="containerRoot">
            <div>
                <jsp:include page="_layoutHeader.jsp"/>  
            </div>
            <div id="container">
            </div>
        </div>
        <script>
            octopusAnimeDetail.init('${requestScope.id}');
        </script>
    </body>
</html>
