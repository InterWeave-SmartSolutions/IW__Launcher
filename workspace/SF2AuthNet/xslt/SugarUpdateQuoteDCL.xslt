<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
		<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='sugarlogin']/data/row/col[@name='id']"/>
		<xsl:variable name="ObjectName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ObjectName']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ON">
			<xsl:choose>
				<xsl:when test="$ObjectName=''">Quotes</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$ObjectName"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:for-each select="iwrecordset/transaction/datamap[@name='payauth']/data/row/col[@name='Result']">
			<xsl:variable name="Result" select="."/>
			<xsl:if test="not(contains($Result, 'HTML'))">
				<xsl:variable name="R1" select="substring-before($Result, ',')"/>
				<xsl:variable name="R1c">
					<xsl:value-of select="$R1"/>,</xsl:variable>
				<xsl:variable name="R1a" select="substring-after($Result, $R1c)"/>
				<xsl:variable name="R2" select="substring-before($R1a, ',')"/>
				<xsl:variable name="R2c">
					<xsl:value-of select="$R2"/>,</xsl:variable>
				<xsl:variable name="R2a" select="substring-after($R1a, $R2c)"/>
				<xsl:variable name="R3" select="substring-before($R2a, ',')"/>
				<xsl:variable name="R3c">
					<xsl:value-of select="$R3"/>,</xsl:variable>
				<xsl:variable name="R3a" select="substring-after($R2a, $R3c)"/>
				<xsl:variable name="R4" select="substring-before($R3a, ',')"/>
				<xsl:variable name="R4c">
					<xsl:value-of select="$R4"/>,</xsl:variable>
				<xsl:variable name="R4a" select="substring-after($R3a, $R4c)"/>
				<xsl:variable name="R5" select="substring-before($R4a, ',')"/>
				<xsl:variable name="R5c">
					<xsl:value-of select="$R5"/>,</xsl:variable>
				<xsl:variable name="R5a" select="substring-after($R4a, $R5c)"/>
				<xsl:variable name="R6" select="substring-before($R5a, ',')"/>
				<xsl:variable name="R6c">
					<xsl:value-of select="$R6"/>,</xsl:variable>
				<xsl:variable name="R6a" select="substring-after($R5a, $R6c)"/>
				<xsl:variable name="R7" select="substring-before($R6a, ',')"/>
				<xsl:variable name="R7c">
					<xsl:value-of select="$R7"/>,</xsl:variable>
				<xsl:variable name="R7a" select="substring-after($R6a, $R7c)"/>
				<xsl:variable name="R8" select="substring-before($R7a, ',')"/>
				<xsl:variable name="R8c">
					<xsl:value-of select="$R8"/>,</xsl:variable>
				<xsl:variable name="R8a" select="substring-after($R7a, $R8c)"/>
				<xsl:variable name="R9" select="substring-before($R8a, ',')"/>
				<xsl:variable name="R9c">
					<xsl:value-of select="$R9"/>,</xsl:variable>
				<xsl:variable name="R9a" select="substring-after($R8a, $R9c)"/>
				<xsl:variable name="R10" select="substring-before($R9a, ',')"/>
				<xsl:variable name="R10c">
					<xsl:value-of select="$R10"/>,</xsl:variable>
				<xsl:variable name="R10a" select="substring-after($R9a, $R10c)"/>
				<xsl:variable name="R11" select="substring-before($R10a, ',')"/>
				<xsl:variable name="R11c">
					<xsl:value-of select="$R11"/>,</xsl:variable>
				<xsl:variable name="R11a" select="substring-after($R10a, $R11c)"/>
				<xsl:variable name="R12" select="substring-before($R11a, ',')"/>
				<xsl:variable name="R12c">
					<xsl:value-of select="$R12"/>,</xsl:variable>
				<xsl:variable name="R12a" select="substring-after($R11a, $R12c)"/>
				<xsl:variable name="R13" select="substring-before($R12a, ',')"/>
				<xsl:if test="$R13!=''">
					<xsl:variable name="R13c">
						<xsl:value-of select="$R13"/>,</xsl:variable>
					<xsl:variable name="R13a" select="substring-after($R12a, $R13c)"/>
					<xsl:variable name="R14" select="substring-before($R13a, ',')"/>
					<xsl:variable name="R14c">
						<xsl:value-of select="$R14"/>,</xsl:variable>
					<xsl:variable name="R14a" select="substring-after($R13a, $R14c)"/>
					<xsl:variable name="R15" select="substring-before($R14a, ',')"/>
					<xsl:variable name="Decl0">
						<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='getquotes']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_entryResponse' or @name='get_entry_listResponse']/rowset[@name='return']/rowset[@name='entry_list']/rowset[@name='item']/rowset[@name='name_value_list']">
							<xsl:if test="starts-with(rowset[@name='item']/row[col[@name='name']='id']/col[@name='value'], $R13)">
								<xsl:value-of select="rowset[@name='item']/row[col[@name='name']='number_of_declines_c']/col[@name='value']"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:variable>
					<xsl:variable name="SugId">
						<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='getquotes']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='get_entryResponse' or @name='get_entry_listResponse']/rowset[@name='return']/rowset[@name='entry_list']/rowset[@name='item']/rowset[@name='name_value_list']">
							<xsl:if test="starts-with(rowset[@name='item']/row[col[@name='name']='id']/col[@name='value'], $R13)">
								<xsl:value-of select="rowset[@name='item']/row[col[@name='name']='id']/col[@name='value']"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:variable>
					<xsl:variable name="TTranId" select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='createtransaction']/data/row/col[@name='id']"/>
					<xsl:if test="$SugId!='' and $TTranId!=''">
						<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:m0="http://schemas.xmlsoap.org/soap/encoding/">
							<SOAP-ENV:Body>
								<m:set_relationship xmlns:m="http://www.sugarcrm.com/sugarcrm" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
									<session xsi:type="xsd:string">
										<xsl:value-of select="$SessionId"/>
									</session>
									<module_name xsi:type="xsd:string">
										<xsl:value-of select="$ON"/>
									</module_name>
									<module_id xsi:type="xsd:string">
										<xsl:value-of select="$SugId"/>
									</module_id>
									<link_field_name xsi:type="xsd:string">tran_transaction_<xsl:value-of select="translate($ON, $uppercase, $lowercase)"/>
									</link_field_name>
									<related_ids xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m0:string[1]">
										<m0:item0 xsi:type="xsd:string">
											<xsl:value-of select="$TTranId"/>
										</m0:item0>
									</related_ids>
									<delete xsi:type="xsd:int">0</delete>
								</m:set_relationship>
							</SOAP-ENV:Body>
						</SOAP-ENV:Envelope>`</xsl:if>
					<xsl:variable name="Decl">
						<xsl:choose>
							<xsl:when test="$Decl0!=''">
								<xsl:value-of select="$Decl0+1"/>
							</xsl:when>
							<xsl:otherwise>1</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="$R1=1">
							<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
								<SOAP-ENV:Body>
									<m:set_entry SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:m="http://www.sugarcrm.com/sugarcrm">
										<session xsi:type="xsd:string">
											<xsl:value-of select="$SessionId"/>
										</session>
										<module_name xsi:type="xsd:string">
											<xsl:value-of select="$ON"/>
										</module_name>
										<name_value_list xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m:name_value[3]">
											<m:item0 xsi:type="m:name_value">
												<name xsi:type="xsd:string">id</name>
												<value xsi:type="xsd:string">
													<xsl:value-of select="$SugId"/>
												</value>
											</m:item0>
											<m:item0 xsi:type="m:name_value">
												<name xsi:type="xsd:string">number_of_declines_c</name>
												<value xsi:type="xsd:string">0</value>
											</m:item0>
											<m:item0 xsi:type="m:name_value">
												<name xsi:type="xsd:string">last_authorized_payment_date_c</name>
												<value xsi:type="xsd:string">%current_date%</value>
											</m:item0>
										</name_value_list>
									</m:set_entry>
								</SOAP-ENV:Body>
							</SOAP-ENV:Envelope>`</xsl:when>
						<xsl:otherwise>
							<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
								<SOAP-ENV:Body>
									<m:set_entry SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:m="http://www.sugarcrm.com/sugarcrm">
										<session xsi:type="xsd:string">
											<xsl:value-of select="$SessionId"/>
										</session>
										<module_name xsi:type="xsd:string">
											<xsl:value-of select="$ON"/>
										</module_name>
										<name_value_list xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m:name_value[2]">
											<m:item0 xsi:type="m:name_value">
												<name xsi:type="xsd:string">id</name>
												<value xsi:type="xsd:string">
													<xsl:value-of select="$SugId"/>
												</value>
											</m:item0>
											<m:item0 xsi:type="m:name_value">
												<name xsi:type="xsd:string">number_of_declines_c</name>
												<value xsi:type="xsd:string">
													<xsl:value-of select="$Decl"/>
												</value>
											</m:item0>
										</name_value_list>
									</m:set_entry>
								</SOAP-ENV:Body>
							</SOAP-ENV:Envelope>`<xsl:if test="$R1!=2">
								<xsl:variable name="To">
									<xsl:choose>
										<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='UseAdmEml']='Yes'">
											<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SFIntUsr']"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CCEmail']"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								<xsl:variable name="CC">
									<xsl:if test="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='UseAdmEml']='Yes'">
										<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CCEmail']"/>
									</xsl:if>
								</xsl:variable>
								<xsl:variable name="BCC" select="//iwtransformationserver/iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='BCCEmail']"/>
								<xsl:if test="$To!='' or $CC!='' or $BCC!=''">
									<xsl:variable name="ErrorType">
										<xsl:choose>
											<xsl:when test="$R1=3">error occured</xsl:when>
											<xsl:when test="$R1=4">is held for review</xsl:when>
										</xsl:choose>
									</xsl:variable>
								</xsl:if>
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>