<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Creatio Product → Magento 2 Product
     Maps Creatio OData Product fields to Magento REST Product JSON payload -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='creatio_products']/data/row">
			<xsl:variable name="Name" select="col[@name='Name']"/>
			<xsl:variable name="Code" select="col[@name='Code']"/>
			<xsl:variable name="Price" select="col[@name='Price']"/>
			<xsl:variable name="Unit" select="col[@name='Unit']"/>
			<xsl:variable name="IsArchive" select="col[@name='IsArchive']"/>
			<xsl:variable name="CreatioId" select="col[@name='Id']"/>
			<xsl:variable name="Status">
				<xsl:choose>
					<xsl:when test="$IsArchive = 'true'">2</xsl:when>
					<xsl:otherwise>1</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:text>{"product":{"sku":"</xsl:text>
			<xsl:value-of select="$Code"/>
			<xsl:text>","name":"</xsl:text>
			<xsl:value-of select="$Name"/>
			<xsl:text>","price":</xsl:text>
			<xsl:value-of select="$Price"/>
			<xsl:text>,"status":</xsl:text>
			<xsl:value-of select="$Status"/>
			<xsl:text>,"type_id":"simple","attribute_set_id":4,"weight":1,"custom_attributes":[{"attribute_code":"creatio_id","value":"</xsl:text>
			<xsl:value-of select="$CreatioId"/>
			<xsl:text>"}]}}</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
