<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Magento 2 Customer → Creatio Account
     Maps Magento REST Customer fields to Creatio OData Account JSON payload -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='magento_customers']/data/row">
			<xsl:variable name="FirstName" select="col[@name='firstname']"/>
			<xsl:variable name="LastName" select="col[@name='lastname']"/>
			<xsl:variable name="Email" select="col[@name='email']"/>
			<xsl:variable name="MagentoId" select="col[@name='id']"/>
			<xsl:variable name="Phone" select="col[@name='telephone']"/>
			<xsl:variable name="Street" select="col[@name='street']"/>
			<xsl:variable name="City" select="col[@name='city']"/>
			<xsl:variable name="Region" select="col[@name='region']"/>
			<xsl:variable name="Postcode" select="col[@name='postcode']"/>
			<xsl:variable name="CountryId" select="col[@name='country_id']"/>
			<xsl:text>{"Name":"</xsl:text>
			<xsl:value-of select="$FirstName"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="$LastName"/>
			<xsl:text>","Email":"</xsl:text>
			<xsl:value-of select="$Email"/>
			<xsl:text>","Phone":"</xsl:text>
			<xsl:value-of select="$Phone"/>
			<xsl:text>","Address":"</xsl:text>
			<xsl:value-of select="$Street"/>
			<xsl:text>","City":"</xsl:text>
			<xsl:value-of select="$City"/>
			<xsl:text>","Region":"</xsl:text>
			<xsl:value-of select="$Region"/>
			<xsl:text>","Zip":"</xsl:text>
			<xsl:value-of select="$Postcode"/>
			<xsl:text>","Country":"</xsl:text>
			<xsl:value-of select="$CountryId"/>
			<xsl:text>","IWMagentoCustomerId":"</xsl:text>
			<xsl:value-of select="$MagentoId"/>
			<xsl:text>"}</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
