<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : category.xsl
    Created on : March 19, 2019, 9:40 PM
    Author     : DELL
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>
    <xsl:param name="Link"/>
    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="categories">
        <xsl:for-each select="category">
            <li>
                <a href="{$Link}{name}">
                    <xsl:value-of select="name/@value"/>
                </a>
            </li>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
