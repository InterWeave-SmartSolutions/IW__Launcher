<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="TransId">?</xsl:variable>
		<xsl:variable name="TransKey">%iw_password%</xsl:variable>
		<xsl:variable name="SFId">?</xsl:variable>
		<xsl:variable name="IP">67.192.84.146</xsl:variable>
		<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
			<SOAP-ENV:Body>
				<m:refund xmlns:m="http://acquirer.process.gatedna.aquarius" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
					<customerpaymentpagetext xsi:type="xsd:string">
						<xsl:value-of select="$TransKey"/>
					</customerpaymentpagetext>
					<orderdescription xsi:type="xsd:string">
						<xsl:value-of select="$SFId"/>
					</orderdescription>
					<referralOrderReference xsi:type="xsd:string">
						<xsl:value-of select="$TransId"/>
					</referralOrderReference>
					<comment1 xsi:type="xsd:string"/>
					<cardHolderIP xsi:type="xsd:string">
						<xsl:value-of select="$IP"/>
					</cardHolderIP>
				</m:refund>
			</SOAP-ENV:Body>
		</SOAP-ENV:Envelope>
	</xsl:template>
</xsl:stylesheet>
