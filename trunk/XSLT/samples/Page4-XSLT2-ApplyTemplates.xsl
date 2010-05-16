<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="employee">
		<b>
			<xsl:apply-templates select="firstName"/>
		</b>
		<b>
			<xsl:apply-templates select="surname"/>
		</b>	
	</xsl:template>
	<xsl:template match="surname">
		<i>
			<xsl:value-of select="."/>
		</i>
	</xsl:template>
</xsl:stylesheet>