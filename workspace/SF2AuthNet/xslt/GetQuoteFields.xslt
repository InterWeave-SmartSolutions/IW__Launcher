<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='sugarlogin']/data/row/col[@name='id']"/>
		<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:m0="http://schemas.xmlsoap.org/soap/encoding/">
			<SOAP-ENV:Body>
				<m:get_module_fields xmlns:m="http://www.sugarcrm.com/sugarcrm" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
					<session xsi:type="xsd:string">
						<xsl:value-of select="$SessionId"/>
					</session>
					<module_name xsi:type="xsd:string">Quotes</module_name>
				</m:get_module_fields>
			</SOAP-ENV:Body>
		</SOAP-ENV:Envelope>
	</xsl:template>
</xsl:stylesheet>
