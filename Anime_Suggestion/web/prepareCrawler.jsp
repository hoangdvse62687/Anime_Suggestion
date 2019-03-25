<%-- 
    Document   : prepareCrawler
    Created on : Mar 16, 2019, 8:19:10 PM
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
        <title>Crawler Page</title>
    </head>
    <body>
        <div id="containerRoot">
            <jsp:include page="_layoutHeader.jsp"/>  
            <div id="container">
                <div id="leftside">
                    <div class="bigBarContainer">
                        <div class="barTitle">
                        Crawler Machine
                        </div>
                        <div class="barContent">
                        <div class="arrow-general">
                            &nbsp;</div>
                        <div>
                            <div style="padding-bottom: 10px">

                            </div>
                            <form action="CrawlData" method="post" enctype="multipart/form-data">
                                <c:set var="data" value="${requestScope.data}"/>
                                <c:if test="${not empty data.message}">
                                    <label class="error">
                                        ${data.message}
                                    </label>
                                    <br/>
                                </c:if>
                                <label class="label">
                                    Input file configs here:
                                </label>
                                <input type="file" name="file" accept="text/xml" required="true"/>
                                <div style="padding-top: 10px;">
                                    <input id="btnSubmit" type="image" src="Content/images/okbtn.png" class="button" style="cursor: pointer">
                                </div>
                                <c:url var="downloadTemplateLink" value="Download">
                                    <c:param name="name" value="configs.xml"/>
                                </c:url>
                                <a class="label" href="${downloadTemplateLink}">
                                    Download File template here
                                </a>
                                
                            </form>
                        </div>
                    </div>
                    </div>  
                </div> 
            </div>
        </div>
        <script>
            includeHTML();
        </script>
    </body>
</html>
