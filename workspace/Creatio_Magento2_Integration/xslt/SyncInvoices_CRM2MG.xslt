<?xml version="1.0" encoding="utf-8"?>
<!-- Transformer: Creatio Invoice → Magento 2 Invoice
     Maps Creatio OData Invoice fields to Magento REST Invoice JSON payload -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8"/>
	<xsl:template match="iwtransformationserver">
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='creatio_invoices']/data/row">
			<xsl:variable name="Number" select="col[@name='Number']"/>
			<xsl:variable name="Amount" select="col[@name='Amount']"/>
			<xsl:variable name="PaymentStatus" select="col[@name='PaymentStatus']"/>
			<xsl:variable name="CreatioId" select="col[@name='Id']"/>
			<xsl:variable name="AccountId" select="col[@name='Account']"/>
			<xsl:text>{"entity":{"increment_id":"CRM-INV-</xsl:text>
			<xsl:value-of select="$Number"/>
			<xsl:text>","grand_total":</xsl:text>
			<xsl:value-of select="$Amount"/>
			<xsl:text>,"state":</xsl:text>
			<xsl:choose>
				<xsl:when test="$PaymentStatus = 'Paid'">2</xsl:when>
				<xsl:otherwise>1</xsl:otherwise>
			</xsl:choose>
			<xsl:text>,"extension_attributes":{"creatio_invoice_id":"</xsl:text>
			<xsl:value-of select="$CreatioId"/>
			<xsl:text>"}}}</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
