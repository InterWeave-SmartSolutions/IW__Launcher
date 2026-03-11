<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Magento 2 Invoice → Creatio Invoice
     Maps Magento REST Invoice fields to Creatio OData Invoice JSON payload -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='magento_invoices']/data/row">
			<xsl:variable name="IncrementId" select="col[@name='increment_id']"/>
			<xsl:variable name="GrandTotal" select="col[@name='grand_total']"/>
			<xsl:variable name="State" select="col[@name='state']"/>
			<xsl:variable name="OrderId" select="col[@name='order_id']"/>
			<xsl:variable name="MagentoId" select="col[@name='entity_id']"/>
			<xsl:variable name="CreatedAt" select="col[@name='created_at']"/>
			<xsl:text>{"Number":"INV-</xsl:text>
			<xsl:value-of select="$IncrementId"/>
			<xsl:text>","Amount":</xsl:text>
			<xsl:value-of select="$GrandTotal"/>
			<xsl:text>,"PaymentStatus":"</xsl:text>
			<xsl:choose>
				<xsl:when test="$State = '2'">Paid</xsl:when>
				<xsl:otherwise>Pending</xsl:otherwise>
			</xsl:choose>
			<xsl:text>","Notes":"Magento Invoice #</xsl:text>
			<xsl:value-of select="$IncrementId"/>
			<xsl:text> for Order #</xsl:text>
			<xsl:value-of select="$OrderId"/>
			<xsl:text>","IWMagentoInvoiceId":"</xsl:text>
			<xsl:value-of select="$MagentoId"/>
			<xsl:text>"}</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
