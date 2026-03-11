<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Get Creatio Account by ID
     Query transformer that formats a single Creatio Account response -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<account>
			<xsl:for-each select="iwrecordset/transaction/datamap[@name='creatio_account']/data/row">
				<id><xsl:value-of select="col[@name='Id']"/></id>
				<name><xsl:value-of select="col[@name='Name']"/></name>
				<phone><xsl:value-of select="col[@name='Phone']"/></phone>
				<email><xsl:value-of select="col[@name='Email']"/></email>
				<address><xsl:value-of select="col[@name='Address']"/></address>
				<city><xsl:value-of select="col[@name='City']"/></city>
				<region><xsl:value-of select="col[@name='Region']"/></region>
				<zip><xsl:value-of select="col[@name='Zip']"/></zip>
				<country><xsl:value-of select="col[@name='Country']"/></country>
			</xsl:for-each>
		</account>
	</xsl:template>
</xsl:stylesheet>
