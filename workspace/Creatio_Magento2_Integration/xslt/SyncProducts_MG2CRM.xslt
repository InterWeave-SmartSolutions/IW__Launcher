<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Magento 2 Product → Creatio Product
     Maps Magento REST Product fields to Creatio OData Product JSON payload -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='magento_products']/data/row">
			<xsl:variable name="Sku" select="col[@name='sku']"/>
			<xsl:variable name="Name" select="col[@name='name']"/>
			<xsl:variable name="Price" select="col[@name='price']"/>
			<xsl:variable name="Status" select="col[@name='status']"/>
			<xsl:variable name="MagentoId" select="col[@name='id']"/>
			<xsl:variable name="IsArchive">
				<xsl:choose>
					<xsl:when test="$Status = '2'">true</xsl:when>
					<xsl:otherwise>false</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:text>{"Name":"</xsl:text>
			<xsl:value-of select="$Name"/>
			<xsl:text>","Code":"</xsl:text>
			<xsl:value-of select="$Sku"/>
			<xsl:text>","Price":</xsl:text>
			<xsl:value-of select="$Price"/>
			<xsl:text>,"IsArchive":</xsl:text>
			<xsl:value-of select="$IsArchive"/>
			<xsl:text>,"IWMagentoProductId":"</xsl:text>
			<xsl:value-of select="$MagentoId"/>
			<xsl:text>"}</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
