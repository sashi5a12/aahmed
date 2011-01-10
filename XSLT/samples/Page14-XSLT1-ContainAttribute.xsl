<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="car[not(@checked)]">
		<p>
			<xsl:text>Car: </xsl:text>
			<xsl:value-of select="@id"></xsl:value-of>
		</p>
	</xsl:template>
</xsl:stylesheet>
