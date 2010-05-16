<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="title">
		<h1><xsl:value-of select="."/></h1>
	</xsl:template>
		<xsl:template match="author">
		<h2><xsl:value-of select="."/></h2>
	</xsl:template>
</xsl:stylesheet>