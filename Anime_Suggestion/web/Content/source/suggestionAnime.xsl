<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : suggestionAnime.xsl
    Created on : March 24, 2019, 4:35 PM
    Author     : DELL
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

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
        <div>
            <a href="Anime?id={id}">
                <img width="150px" height="195px" src="Content/images/{img}"/>
            </a>
            <div style="float: left; width: 540px">
                <a class="bigChar" href="Anime?id={id}">
                    <xsl:value-of select="name"/>
                </a>
            </div>
            <p>
                <span class="info">Thể loại:</span>
                <xsl:for-each select="mappings">
                    <a href="Category?cateName={nameCate}" class="dotUnder"><xsl:value-of select="nameCate/@value"/></a>
                        <span class="info">,</span>
                </xsl:for-each>
            </p>
            <p>
                <xsl:value-of select="description" disable-output-escaping="yes"/>
            </p>
        </div>
    </xsl:template>

</xsl:stylesheet>
