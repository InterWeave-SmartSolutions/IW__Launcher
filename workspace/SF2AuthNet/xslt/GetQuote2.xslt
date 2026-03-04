<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='sugarlogin']/data/row/col[@name='id']"/>
		<xsl:variable name="FieldNum" select="count(iwrecordset/transaction/datamap[@name='getquotefields']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_module_fieldsResponse']/rowset[@name='return']/rowset[@name='module_fields']/rowset[@name='item']/row/col[@name='name'])"/>
		<xsl:variable name="FieldNumP" select="count(iwrecordset/transaction/datamap[@name='getproductfields']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_module_fieldsResponse']/rowset[@name='return']/rowset[@name='module_fields']/rowset[@name='item']/row/col[@name='name'])"/>
		<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:m0="http://schemas.xmlsoap.org/soap/encoding/">
			<SOAP-ENV:Body>
				<m:get_entry xmlns:m="http://www.sugarcrm.com/sugarcrm" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
					<session xsi:type="xsd:string">
						<xsl:value-of select="$SessionId"/>
					</session>
					<module_name xsi:type="xsd:string">Quotes</module_name>
					<id xsi:type="xsd:string">?</id>
					<select_fields xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m0:string[{$FieldNum}]">
						<xsl:for-each select="iwrecordset/transaction/datamap[@name='getquotefields']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_module_fieldsResponse']/rowset[@name='return']/rowset[@name='module_fields']/rowset[@name='item']/row">
							<m0:item0 xsi:type="xsd:string">
								<xsl:value-of select="col[@name='name']"/>
							</m0:item0>
						</xsl:for-each>
					</select_fields>
					<link_name_to_fields_array xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m:link_name_to_fields_array[2]">
						<m:item0 xsi:type="m:link_name_to_fields_array">
							<name xsi:type="xsd:string">products</name>
							<value xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m0:string[{$FieldNumP}]">
								<xsl:for-each select="iwrecordset/transaction/datamap[@name='getproductfields']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_module_fieldsResponse']/rowset[@name='return']/rowset[@name='module_fields']/rowset[@name='item']/row">
									<m0:item0 xsi:type="xsd:string">
										<xsl:value-of select="col[@name='name']"/>
									</m0:item0>
								</xsl:for-each>
							</value>
						</m:item0>
						<m:item0 xsi:type="m:link_name_to_fields_array">
							<name xsi:type="xsd:string">opportunities</name>
							<value xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m0:string[{$FieldNumP}]">
								<xsl:for-each select="iwrecordset/transaction/datamap[@name='getoppfields']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_module_fieldsResponse']/rowset[@name='return']/rowset[@name='module_fields']/rowset[@name='item']/row">
									<m0:item0 xsi:type="xsd:string">
										<xsl:value-of select="col[@name='name']"/>
									</m0:item0>
								</xsl:for-each>
							</value>
						</m:item0>
					</link_name_to_fields_array>
				</m:get_entry>
			</SOAP-ENV:Body>
		</SOAP-ENV:Envelope>
	</xsl:template>
</xsl:stylesheet>
