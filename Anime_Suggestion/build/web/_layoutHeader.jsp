<%-- 
    Document   : _layoutHeader
    Created on : Mar 22, 2019, 12:15:29 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="Content/js/common.js" type="text/javascript"></script>
        <link href="Content/css/common.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="header.html"/>
        <div class="clear">
        </div>
        
        <div>
            <jsp:include page="category.html"/> 
        </div>
    </body>
</html>
