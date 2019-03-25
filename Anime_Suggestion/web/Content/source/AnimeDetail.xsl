<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : AnimeDetail.xsl
    Created on : March 23, 2019, 10:48 PM
    Author     : DELL
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="movieDto">
        <div id="leftside">
                    <div class="bigBarContainer">
                        <div class="barTitle">
                            
                        </div>
                        <div class="barContent">
                            <div class="arrow-general"></div>
                            <a class="bigChar" href="Anime?id={id}"><xsl:value-of select="name"/></a>
                <p>
                    <span class="info">Thể loại:</span>
                        <xsl:for-each select="mappings">
                            <a href="Category?cateName={nameCate}" class="dotUnder"><xsl:value-of select="nameCate/@value"/></a>
                            <span class="info">,</span>
                        </xsl:for-each>
                </p>
                <p>
                    <span class="info">Năm</span>&#160;
                    <xsl:value-of select="year"/>
                </p>
                <p>
                <span class="info">Số Tập:</span>
                    &#160;<xsl:value-of select="episode"/>
                    &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
                <span class="info">Lượt Xem:</span>
                    &#160;<xsl:value-of select="mountOfViewed"/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
                            
                    <span>Lượt Bình Luận:</span>
                    &#160;<xsl:value-of select="mountOfComment"/>
                </p>
                <p>
                    <span class="info">Giới Thiệu:</span>
                </p>
                <p>
                      <xsl:value-of select="description" disable-output-escaping="yes"/>              
                </p>
                        </div>
                    </div>
                </div> 
                <div id="rightside">
                    <div class="barTitle">
                    Cover</div>
                    <div class="barContent">
                    <div class="arrow-general">
                        &#160;</div>
                    <div style="text-align: center">
                        <img width="190px" height="250px" src="Content/images/{img}"/>
                    </div>
                </div>
                </div>
    </xsl:template>

</xsl:stylesheet>
