<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Creatio Account → Magento 2 Customer
     Maps Creatio OData Account fields to Magento REST Customer JSON payload -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='creatio_accounts']/data/row">
			<xsl:variable name="Name" select="col[@name='Name']"/>
			<xsl:variable name="Email" select="col[@name='Email']"/>
			<xsl:variable name="Phone" select="col[@name='Phone']"/>
			<xsl:variable name="Address" select="col[@name='Address']"/>
			<xsl:variable name="City" select="col[@name='City']"/>
			<xsl:variable name="Region" select="col[@name='Region']"/>
			<xsl:variable name="Zip" select="col[@name='Zip']"/>
			<xsl:variable name="Country" select="col[@name='Country']"/>
			<xsl:variable name="CreatioId" select="col[@name='Id']"/>
			<xsl:text>{"customer":{"email":"</xsl:text>
			<xsl:value-of select="$Email"/>
			<xsl:text>","firstname":"</xsl:text>
			<xsl:value-of select="$Name"/>
			<xsl:text>","lastname":"(Creatio)","addresses":[{"street":["</xsl:text>
			<xsl:value-of select="$Address"/>
			<xsl:text>"],"city":"</xsl:text>
			<xsl:value-of select="$City"/>
			<xsl:text>","region":{"region":"</xsl:text>
			<xsl:value-of select="$Region"/>
			<xsl:text>"},"postcode":"</xsl:text>
			<xsl:value-of select="$Zip"/>
			<xsl:text>","country_id":"</xsl:text>
			<xsl:value-of select="$Country"/>
			<xsl:text>","telephone":"</xsl:text>
			<xsl:value-of select="$Phone"/>
			<xsl:text>"}],"custom_attributes":[{"attribute_code":"creatio_id","value":"</xsl:text>
			<xsl:value-of select="$CreatioId"/>
			<xsl:text>"}]}}</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
