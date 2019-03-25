<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : TopMovie.xsl
    Created on : March 22, 2019, 10:37 PM
    Author     : DELL
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>
    <xsl:param name="page"/>
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
    
    <xsl:template match="movieDtoes">
        <xsl:for-each select="movieDto">
            <li>
                <div style="position: relative">
                    <a href="Anime?id={id}">
                        <img width="80px" height="100px" src="Content/images/{img}"/>
                    </a>
                    <a href="Anime?id={id}">
                        <span class="title"><xsl:value-of select="name"/></span>
                    </a>
                    <p>
                        <span class="info">Thể loại:</span>
                        <xsl:for-each select="mappings">
                            <a href="Category?cateName={nameCate}" class="dotUnder"><xsl:value-of select="nameCate/@value"/></a>
                            <span class="info">,</span>
                        </xsl:for-each>
                    </p>
                    <p>
                        <span class="info">Số tập:</span>
                        <a href="Anime/id={id}"><xsl:value-of select="episode"/></a>
                    </p>
                    <a style="position:absolute;top:0px; left:680px; width:28px; height:28px">
                        Rank:<xsl:value-of select="$page + position()"/>
                    </a>
                </div>
            </li>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
