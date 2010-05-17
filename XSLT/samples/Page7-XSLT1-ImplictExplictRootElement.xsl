<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="AAA">
		<div style="color:purple">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
	</xsl:template>
	<!--
	<xsl:template match="BBB">
		<div style="color:purple">
			<xsl:value-of select="name()"/>
			<xsl:text> id=</xsl:text>
			<xsl:value-of select="@id"/>
		</div>
		<xsl:apply-templates/>
	</xsl:template>
	-->
</xsl:stylesheet>
