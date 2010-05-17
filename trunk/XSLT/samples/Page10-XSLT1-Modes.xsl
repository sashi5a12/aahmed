<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<xsl:apply-templates select="//CCC" mode="red"/>
		<xsl:apply-templates select="//CCC" mode="blue"/>
		<xsl:apply-templates select="//CCC" mode="purple"/>		
	</xsl:template>
	<xsl:template match="CCC" mode="red">
		<div style="color:red">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="CCC" mode="blue">
		<div style="color:blue">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="CCC" mode="purple">
		<div style="color:purple">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
		<xsl:apply-templates/>
	</xsl:template>	
</xsl:stylesheet>