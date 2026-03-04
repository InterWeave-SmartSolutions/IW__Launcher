<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='sugarlogin']/data/row/col[@name='id']"/>
		<xsl:variable name="TransactionSourceName">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
			</xsl:if>
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
				<xsl:variable name="R13c">
					<xsl:value-of select="$R13"/>,</xsl:variable>
				<xsl:variable name="R13a" select="substring-after($R12a, $R13c)"/>
				<xsl:variable name="R14" select="substring-before($R13a, ',')"/>
				<xsl:variable name="AppStatus">
					<xsl:choose>
						<xsl:when test="$R1=1">Approved</xsl:when>
						<xsl:when test="$R1=2">Declined</xsl:when>
						<xsl:when test="$R1=3">Error</xsl:when>
						<xsl:when test="$R1=4">Held</xsl:when>
						<xsl:otherwise>Error</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="DeclCom">
					<xsl:if test="$R1!=1">
						<xsl:value-of select="$R4"/>
					</xsl:if>
				</xsl:variable>
				<xsl:variable name="Is1stPmnt">
					<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
						<xsl:if test="row/col[@name='Id']=$R13">
							<xsl:variable name="TB" select="row/col[@name='Total_Balance__c']"/>
							<xsl:choose>
								<xsl:when test="$TB='' or number($TB)=number(0.0)">true</xsl:when>
								<xsl:otherwise>false</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
					</xsl:for-each>
				</xsl:variable>
				<xsl:variable name="DueOver">0</xsl:variable>
				<xsl:variable name="TranStat">Processed</xsl:variable>
				<xsl:variable name="PayType">
					<xsl:choose>
						<xsl:when test="$Is1stPmnt='true' or $Is1stPmnt=''">FirstPayment</xsl:when>
						<xsl:otherwise>RecurringPayment</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="TranType">
					<xsl:choose>
						<xsl:when test="$R11='CC'">CreditCard</xsl:when>
						<xsl:when test="$R11='ECHECK'">ACH</xsl:when>
						<xsl:otherwise>Other</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="RoutingBank">
					<xsl:choose>
						<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='RetrieveSFAcctDataLId']">Monterey</xsl:when>
						<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='PayAuthorizeNetQQuote']">Authorize.net</xsl:when>
						<xsl:otherwise>Authorize.net</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
					<SOAP-ENV:Body>
						<m:set_entry SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:m="http://www.sugarcrm.com/sugarcrm">
							<session xsi:type="xsd:string">
								<xsl:value-of select="$SessionId"/>
							</session>
							<module_name xsi:type="xsd:string">TRAN_Transaction</module_name>
							<name_value_list xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="m:name_value[12]">
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">name</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$R7"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">approval_status</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$AppStatus"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">amount</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$R10"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">date_ran</name>
									<value xsi:type="xsd:string">%current_date%</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">declined_comments</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$DeclCom"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">due_date_override</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$DueOver"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">transaction_status</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$TranStat"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">transaction_type</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$TranType"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">first_recurring</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$PayType"/>
									</value>
								</m:item0>
								<m:item0 xsi:type="m:name_value">
									<name xsi:type="xsd:string">routing_bank</name>
									<value xsi:type="xsd:string">
										<xsl:value-of select="$RoutingBank"/>
									</value>
								</m:item0>
							</name_value_list>
						</m:set_entry>
					</SOAP-ENV:Body>
				</SOAP-ENV:Envelope>`</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>