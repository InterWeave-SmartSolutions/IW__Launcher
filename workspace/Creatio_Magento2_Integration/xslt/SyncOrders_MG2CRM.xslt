<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Magento 2 Order → Creatio Order
     Maps Magento REST Order fields to Creatio OData Order JSON payload -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='magento_orders']/data/row">
			<xsl:variable name="IncrementId" select="col[@name='increment_id']"/>
			<xsl:variable name="CustomerEmail" select="col[@name='customer_email']"/>
			<xsl:variable name="GrandTotal" select="col[@name='grand_total']"/>
			<xsl:variable name="Status" select="col[@name='status']"/>
			<xsl:variable name="CreatedAt" select="col[@name='created_at']"/>
			<xsl:variable name="MagentoId" select="col[@name='entity_id']"/>
			<xsl:variable name="ShippingAmount" select="col[@name='shipping_amount']"/>
			<xsl:variable name="TaxAmount" select="col[@name='tax_amount']"/>
			<xsl:text>{"Number":"</xsl:text>
			<xsl:value-of select="$IncrementId"/>
			<xsl:text>","Amount":</xsl:text>
			<xsl:value-of select="$GrandTotal"/>
			<xsl:text>,"PaymentStatus":"</xsl:text>
			<xsl:value-of select="$Status"/>
			<xsl:text>","Notes":"Magento Order #</xsl:text>
			<xsl:value-of select="$IncrementId"/>
			<xsl:text> - Shipping: </xsl:text>
			<xsl:value-of select="$ShippingAmount"/>
			<xsl:text>, Tax: </xsl:text>
			<xsl:value-of select="$TaxAmount"/>
			<xsl:text>","IWMagentoOrderId":"</xsl:text>
			<xsl:value-of select="$MagentoId"/>
			<xsl:text>"}</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
