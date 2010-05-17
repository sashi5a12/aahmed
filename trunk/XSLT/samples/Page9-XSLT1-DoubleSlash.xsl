<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!--
	<xsl:template match="/">
		<xsl:apply-templates select="//BBB"/>
		<xsl:apply-templates select="//CCC"/>
		<xsl:apply-templates select="//DDD"/>
		<xsl:apply-templates select="//AAA"/>
	</xsl:template>
	-->
	<xsl:template match="/">
		<xsl:apply-templates select="/source/AAA//CCC"/>
		<xsl:apply-templates select="/source//AAA/BBB//*"/>
	</xsl:template>
	<xsl:template match="AAA">
		<div style="color:navy">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
	</xsl:template>
	<xsl:template match="BBB">
		<div style="color:purple">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
	</xsl:template>
	<xsl:template match="CCC">
		<div style="color:red">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
	</xsl:template>
	<xsl:template match="DDD">
		<div style="color:blue">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
	</xsl:template>
</xsl:stylesheet>
