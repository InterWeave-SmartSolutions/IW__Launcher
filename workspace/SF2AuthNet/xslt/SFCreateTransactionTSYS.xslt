<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="iwtransformationserver">
		<xsl:variable name="SessionId" select="iwrecordset/transaction/datamap[@name='login']/data/row/col[@name='sessionId']"/>
		<xsl:variable name="DBLQ">"</xsl:variable>
		<xsl:variable name="CurrentFlowId">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='CurrentFlowId']"/>
				</xsl:when>
				<xsl:otherwise>SugarCO2QBBillNQQ</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="APIType00">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__APIType']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__APIType']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="APIType0">
			<xsl:choose>
				<xsl:when test="contains($APIType00, ':')">
					<xsl:value-of select="substring-before($APIType00, ':')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$APIType00"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="SchedCCType">
			<xsl:choose>
				<xsl:when test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SchedCCType']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='SchedCCType']"/>
				</xsl:when>
				<xsl:otherwise>None</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="APIType">
			<xsl:choose>
				<xsl:when test="$APIType0!=''">
					<xsl:value-of select="$APIType0"/>
				</xsl:when>
				<xsl:when test="$SchedCCType='Cust'">POST</xsl:when>
				<xsl:otherwise>WS</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="CreditMode0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CreditMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__CreditMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="CreditMode">
			<xsl:choose>
				<xsl:when test="$CreditMode0!=''">
					<xsl:value-of select="$CreditMode0"/>
				</xsl:when>
				<xsl:otherwise>CREDIT</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="DSName">
			<xsl:choose>
				<xsl:when test="contains($APIType00, ':')">payauthinfo</xsl:when>
				<xsl:otherwise>payauth</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="TrnName">
			<xsl:choose>
				<xsl:when test="$APIType='WS'">PayTSYSQOpp</xsl:when>
				<xsl:when test="$APIType='POST' and $CreditMode0!=''">PayTSYSTQCQ</xsl:when>
				<xsl:when test="$APIType='POST'">PayTSYSQOppP</xsl:when>
				<xsl:when test="starts-with($APIType, 'CARDPOINT') and $CreditMode0!=''">PayCardPQCrd</xsl:when>
				<xsl:when test="starts-with($APIType, 'CARDPOINT')">PayCardPQOpp</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="ProcessingMode0">
			<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']">
				<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__ProcessingMode']"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="ProcessingMode">
			<xsl:choose>
				<xsl:when test="starts-with($CurrentFlowId,'SFTransactions2TSYSSt') or starts-with($CurrentFlowId,'SFTransactions2CardPSt')">FROMSTORAGE</xsl:when>
				<xsl:when test="$ProcessingMode0=''">NORMAL</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$ProcessingMode0"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:if test="$ProcessingMode!='SETSTORAGE'">
			<xsl:variable name="TransactionSourceName">
				<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__TransactionSourceName']"/>
				</xsl:if>
			</xsl:variable>
			<xsl:variable name="UseIdorName">
				<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='__Param__UseIdorName']"/>
				</xsl:if>
			</xsl:variable>
			<xsl:variable name="IdorName">
				<xsl:choose>
					<xsl:when test="$UseIdorName='Name'">Name</xsl:when>
					<xsl:otherwise>Id</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="ShowCCTypes">
				<xsl:if test="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='ShowCCTypes']">
					<xsl:value-of select="iwrecordset/transaction[@name='sessionvars']/datamap[@name='sessionvars']/data/row/col[@name='ShowCCTypes']"/>
				</xsl:if>
			</xsl:variable>
			<xsl:choose>
				<xsl:when test="$APIType='WS'"/>
				<xsl:when test="$APIType='POST'">
					<xsl:for-each select="iwrecordset/transaction[@name=$TrnName]/datamap[@name=$DSName]/data/row/col[@name='Result']">
						<xsl:variable name="Pos" select="position()"/>
						<xsl:variable name="Result" select="concat(translate(., $DBLQ, ''), '&amp;')"/>
						<xsl:if test="not(contains($Result, 'HTML'))">
							<xsl:variable name="R1" select="substring-before($Result, '&amp;')"/>
							<xsl:variable name="R1c">
								<xsl:value-of select="$R1"/>&amp;</xsl:variable>
							<xsl:variable name="R1a" select="substring-after($Result, $R1c)"/>
							<xsl:variable name="R2" select="substring-before($R1a, '&amp;')"/>
							<xsl:variable name="R2c">
								<xsl:value-of select="$R2"/>&amp;</xsl:variable>
							<xsl:variable name="R2a" select="substring-after($R1a, $R2c)"/>
							<xsl:variable name="R3" select="substring-before($R2a, '&amp;')"/>
							<xsl:variable name="R3c">
								<xsl:value-of select="$R3"/>&amp;</xsl:variable>
							<xsl:variable name="R3a" select="substring-after($R2a, $R3c)"/>
							<xsl:variable name="R4" select="substring-before($R3a, '&amp;')"/>
							<xsl:variable name="R4c">
								<xsl:value-of select="$R4"/>&amp;</xsl:variable>
							<xsl:variable name="R4a" select="substring-after($R3a, $R4c)"/>
							<xsl:variable name="R5" select="substring-before($R4a, '&amp;')"/>
							<xsl:variable name="R5c">
								<xsl:value-of select="$R5"/>&amp;</xsl:variable>
							<xsl:variable name="R5a" select="substring-after($R4a, $R5c)"/>
							<xsl:variable name="R6" select="substring-before($R5a, '&amp;')"/>
							<xsl:variable name="R6c">
								<xsl:value-of select="$R6"/>&amp;</xsl:variable>
							<xsl:variable name="R6a" select="substring-after($R5a, $R6c)"/>
							<xsl:variable name="R7" select="substring-before($R6a, '&amp;')"/>
							<xsl:variable name="R7c">
								<xsl:value-of select="$R7"/>&amp;</xsl:variable>
							<xsl:variable name="R7a" select="substring-after($R6a, $R7c)"/>
							<xsl:variable name="R8" select="substring-before($R7a, '&amp;')"/>
							<xsl:variable name="R8c">
								<xsl:value-of select="$R8"/>&amp;</xsl:variable>
							<xsl:variable name="R8a" select="substring-after($R7a, $R8c)"/>
							<xsl:variable name="R9" select="substring-before($R8a, '&amp;')"/>
							<xsl:variable name="R9c">
								<xsl:value-of select="$R9"/>&amp;</xsl:variable>
							<xsl:variable name="R9a" select="substring-after($R8a, $R9c)"/>
							<xsl:variable name="R10" select="substring-before($R9a, '&amp;')"/>
							<!--<xsl:variable name="R10c">
								<xsl:value-of select="$R10"/>,</xsl:variable>
							<xsl:variable name="R10a" select="substring-after($R9a, $R10c)"/>
							<xsl:variable name="R11" select="substring-before($R10a, '&amp;')"/>
							<xsl:variable name="R11c">
								<xsl:value-of select="$R11"/>,</xsl:variable>
							<xsl:variable name="R11a" select="substring-after($R10a, $R11c)"/>
							<xsl:variable name="R12" select="substring-before($R11a, '&amp;')"/>
							<xsl:variable name="R12c">
								<xsl:value-of select="$R12"/>,</xsl:variable>
							<xsl:variable name="R12a" select="substring-after($R11a, $R12c)"/>
							<xsl:variable name="R13" select="substring-before($R12a, '&amp;')"/>
							<xsl:variable name="R13c">
								<xsl:value-of select="$R13"/>,</xsl:variable>
							<xsl:variable name="R13a" select="substring-after($R12a, $R13c)"/>
							<xsl:variable name="R14" select="substring-before($R13a, '&amp;')"/>
							<xsl:variable name="R14c">
								<xsl:value-of select="$R14"/>,</xsl:variable>
							<xsl:variable name="R14a" select="substring-after($R13a, $R14c)"/>
							<xsl:variable name="R15" select="substring-before($R14a, '&amp;')"/>
							<xsl:variable name="R15c">
								<xsl:value-of select="$R15"/>,</xsl:variable>
							<xsl:variable name="R15a" select="substring-after($R14a, $R15c)"/>
							<xsl:variable name="R16" select="substring-before($R15a, '&amp;')"/>
							<xsl:variable name="R16c">
								<xsl:value-of select="$R16"/>,</xsl:variable>
							<xsl:variable name="R16a" select="substring-after($R15a, $R16c)"/>
							<xsl:variable name="R17" select="substring-before($R16a, '&amp;')"/>
							<xsl:variable name="R17c">
								<xsl:value-of select="$R17"/>,</xsl:variable>
							<xsl:variable name="R17a" select="substring-after($R16a, $R17c)"/>
							<xsl:variable name="R18" select="substring-before($R17a, '&amp;')"/>
							<xsl:variable name="R18c">
								<xsl:value-of select="$R18"/>,</xsl:variable>
							<xsl:variable name="R18a" select="substring-after($R17a, $R18c)"/>
							<xsl:variable name="R19" select="substring-before($R18a, '&amp;')"/>
							<xsl:variable name="R19c">
								<xsl:value-of select="$R19"/>,</xsl:variable>
							<xsl:variable name="R19a" select="substring-after($R18a, $R19c)"/>
							<xsl:variable name="R20" select="substring-before($R19a, '&amp;')"/>
							<xsl:variable name="R20c">
								<xsl:value-of select="$R20"/>,</xsl:variable>
							<xsl:variable name="R20a" select="substring-after($R9a, $R20c)"/>
							<xsl:variable name="R21" select="substring-before($R20a, '&amp;')"/>
							<xsl:variable name="R21c">
								<xsl:value-of select="$R21"/>,</xsl:variable>
							<xsl:variable name="R21a" select="substring-after($R20a, $R21c)"/>
							<xsl:variable name="R22" select="substring-before($R21a, '&amp;')"/>
							<xsl:variable name="R22c">
								<xsl:value-of select="$R22"/>,</xsl:variable>
							<xsl:variable name="R22a" select="substring-after($R21a, $R22c)"/>
							<xsl:variable name="R23" select="substring-before($R22a, '&amp;')"/>
							<xsl:variable name="R23c">
								<xsl:value-of select="$R23"/>,</xsl:variable>
							<xsl:variable name="R23a" select="substring-after($R22a, $R23c)"/>
							<xsl:variable name="R24" select="substring-before($R23a, '&amp;')"/>
							<xsl:variable name="R24c">
								<xsl:value-of select="$R24"/>,</xsl:variable>
							<xsl:variable name="R24a" select="substring-after($R23a, $R24c)"/>
							<xsl:variable name="R25" select="substring-before($R24a, '&amp;')"/>
							<xsl:variable name="R25c">
								<xsl:value-of select="$R25"/>,</xsl:variable>
							<xsl:variable name="R25a" select="substring-after($R24a, $R25c)"/>
							<xsl:variable name="R26" select="substring-before($R25a, '&amp;')"/>
							<xsl:variable name="R26c">
								<xsl:value-of select="$R26"/>,</xsl:variable>
							<xsl:variable name="R26a" select="substring-after($R25a, $R26c)"/>
							<xsl:variable name="R27" select="substring-before($R26a, '&amp;')"/>
							<xsl:variable name="R27c">
								<xsl:value-of select="$R27"/>,</xsl:variable>
							<xsl:variable name="R27a" select="substring-after($R26a, $R27c)"/>
							<xsl:variable name="R28" select="substring-before($R27a, '&amp;')"/>
							<xsl:variable name="R28c">
								<xsl:value-of select="$R28"/>,</xsl:variable>
							<xsl:variable name="R28a" select="substring-after($R27a, $R28c)"/>
							<xsl:variable name="R29" select="substring-before($R28a, '&amp;')"/>
							<xsl:variable name="R29c">
								<xsl:value-of select="$R29"/>,</xsl:variable>
							<xsl:variable name="R29a" select="substring-after($R28a, $R29c)"/>
							<xsl:variable name="R30" select="substring-before($R29a, '&amp;')"/>
							<xsl:variable name="R30c">
								<xsl:value-of select="$R30"/>,</xsl:variable>
							<xsl:variable name="R30a" select="substring-after($R29a, $R30c)"/>
							<xsl:variable name="R31" select="substring-before($R30a, '&amp;')"/>
							<xsl:variable name="R31c">
								<xsl:value-of select="$R31"/>,</xsl:variable>
							<xsl:variable name="R31a" select="substring-after($R30a, $R31c)"/>
							<xsl:variable name="R32" select="substring-before($R31a, '&amp;')"/>
							<xsl:variable name="R32c">
								<xsl:value-of select="$R32"/>,</xsl:variable>
							<xsl:variable name="R32a" select="substring-after($R31a, $R32c)"/>
							<xsl:variable name="R33" select="substring-before($R32a, '&amp;')"/>
							<xsl:variable name="R33c">
								<xsl:value-of select="$R33"/>,</xsl:variable>
							<xsl:variable name="R33a" select="substring-after($R32a, $R33c)"/>
							<xsl:variable name="R34" select="substring-before($R33a, '&amp;')"/>
							<xsl:variable name="R34c">
								<xsl:value-of select="$R34"/>,</xsl:variable>
							<xsl:variable name="R34a" select="substring-after($R33a, $R34c)"/>
							<xsl:variable name="R35" select="substring-before($R34a, '&amp;')"/>
							<xsl:variable name="R35c">
								<xsl:value-of select="$R35"/>,</xsl:variable>
							<xsl:variable name="R35a" select="substring-after($R34a, $R35c)"/>
							<xsl:variable name="R36" select="substring-before($R35a, '&amp;')"/>
							<xsl:variable name="R36c">
								<xsl:value-of select="$R36"/>,</xsl:variable>
							<xsl:variable name="R36a" select="substring-after($R35a, $R36c)"/>
							<xsl:variable name="R37" select="substring-before($R36a, '&amp;')"/>
							<xsl:variable name="R37c">
								<xsl:value-of select="$R37"/>,</xsl:variable>
							<xsl:variable name="R37a" select="substring-after($R36a, $R37c)"/>
							<xsl:variable name="R38" select="substring-before($R37a, '&amp;')"/>
							<xsl:variable name="R38c">
								<xsl:value-of select="$R38"/>,</xsl:variable>
							<xsl:variable name="R38a" select="substring-after($R37a, $R38c)"/>
							<xsl:variable name="R39" select="substring-before($R38a, '&amp;')"/>
							<xsl:variable name="R39c">
								<xsl:value-of select="$R39"/>,</xsl:variable>
							<xsl:variable name="R39a" select="substring-after($R38a, $R39c)"/>
							<xsl:variable name="R40" select="substring-before($R39a, '&amp;')"/>
							<xsl:variable name="R40c">
								<xsl:value-of select="$R40"/>,</xsl:variable>
							<xsl:variable name="R40a" select="substring-after($R39a, $R40c)"/>
							<xsl:variable name="R41" select="substring-before($R40a, '&amp;')"/>
							<xsl:variable name="R41c">
								<xsl:value-of select="$R41"/>,</xsl:variable>
							<xsl:variable name="R41a" select="substring-after($R40a, $R41c)"/>
							<xsl:variable name="R42" select="substring-before($R41a, '&amp;')"/>
							<xsl:variable name="R42c">
								<xsl:value-of select="$R42"/>,</xsl:variable>
							<xsl:variable name="R42a" select="substring-after($R41a, $R42c)"/>
							<xsl:variable name="R43" select="substring-before($R42a, '&amp;')"/>
							<xsl:variable name="R43c">
								<xsl:value-of select="$R43"/>,</xsl:variable>
							<xsl:variable name="R43a" select="substring-after($R42a, $R43c)"/>
							<xsl:variable name="R44" select="substring-before($R43a, '&amp;')"/>
							<xsl:variable name="R44c">
								<xsl:value-of select="$R44"/>,</xsl:variable>
							<xsl:variable name="R44a" select="substring-after($R43a, $R44c)"/>
							<xsl:variable name="R45" select="substring-before($R44a, '&amp;')"/>
							<xsl:variable name="R45c">
								<xsl:value-of select="$R45"/>,</xsl:variable>
							<xsl:variable name="R45a" select="substring-after($R44a, $R45c)"/>
							<xsl:variable name="R46" select="substring-before($R45a, '&amp;')"/>
							<xsl:variable name="R46c">
								<xsl:value-of select="$R46"/>,</xsl:variable>
							<xsl:variable name="R46a" select="substring-after($R45a, $R46c)"/>
							<xsl:variable name="R47" select="substring-before($R46a, '&amp;')"/>
							<xsl:variable name="R47c">
								<xsl:value-of select="$R47"/>,</xsl:variable>
							<xsl:variable name="R47a" select="substring-after($R46a, $R47c)"/>
							<xsl:variable name="R48" select="substring-before($R47a, '&amp;')"/>
							<xsl:variable name="R48c">
								<xsl:value-of select="$R48"/>,</xsl:variable>
							<xsl:variable name="R48a" select="substring-after($R47a, $R48c)"/>
							<xsl:variable name="R49" select="substring-before($R48a, '&amp;')"/>
							<xsl:variable name="R49c">
								<xsl:value-of select="$R49"/>,</xsl:variable>
							<xsl:variable name="R49a" select="substring-after($R48a, $R49c)"/>
							<xsl:variable name="R50" select="substring-before($R49a, '&amp;')"/>
							<xsl:variable name="R50c">
								<xsl:value-of select="$R50"/>,</xsl:variable>
							<xsl:variable name="R50a" select="substring-after($R49a, $R50c)"/>
							<xsl:variable name="R51" select="substring-before($R50a, '&amp;')"/>
							<xsl:variable name="R51c">
								<xsl:value-of select="$R51"/>,</xsl:variable>
							<xsl:variable name="R51a" select="substring-after($R50a, $R51c)"/>
							<xsl:variable name="R52" select="substring-before($R51a, '&amp;')"/>-->
							<xsl:variable name="AppStatus">
								<xsl:choose>
									<xsl:when test="contains($APIType00, ':') and substring-before($R4, '=')='Status' and starts-with(substring-after($R4, '='), 'Approved')">Approved</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and substring-before($R1, '=')='ResponseCode' and substring-after($R1, '=')='00'">Approved</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and ((substring-before($R1, '=')='ResponseCode' and substring-after($R1, '=')='06') or substring-before($R1, '=')='ErrorCode')">Error</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and (substring-before($R1, '=')='ResponseCode' and (substring-after($R1, '=')='06' or substring-after($R1, '=')='08' or substring-after($R1, '=')='11'))">Declined</xsl:when>
									<xsl:otherwise>Error</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xsl:variable name="DeclCom">
								<xsl:choose>
									<xsl:when test="contains($APIType00, ':') and substring-before($R5, '=')='Message'">
										<xsl:value-of select="substring-after($R5, 'Message=')"/>
									</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and substring-before($R1, '=')='ErrorCode' and $R3!=''">
										<xsl:value-of select="substring-after($R3, '=')"/>
									</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and substring-before($R1, '=')='ResponseCode' and substring-after($R1, '=')!='00'">Return Code = <xsl:value-of select="substring-after($R1, '=')"/>
									</xsl:when>
								</xsl:choose>
							</xsl:variable>
							<xsl:variable name="Is1stPmnt">
								<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
									<xsl:variable name="TB" select="row/col[@name='Total_Balance__c']"/>
									<xsl:choose>
										<xsl:when test="$TB='' or number($TB)=number(0.0)">true</xsl:when>
										<xsl:otherwise>false</xsl:otherwise>
									</xsl:choose>
								</xsl:for-each>
							</xsl:variable>
							<xsl:variable name="DueOver">0</xsl:variable>
							<xsl:variable name="TranStat">Processed</xsl:variable>
							<xsl:variable name="PayType">
								<xsl:choose>
									<xsl:when test="$CreditMode0='' and $Is1stPmnt='true'">First Payment</xsl:when>
									<xsl:when test="$CreditMode0=''">Recurring Payment</xsl:when>
									<xsl:when test="$CreditMode='REFUND'">Refund</xsl:when>
									<xsl:when test="$CreditMode='VOID'">Void</xsl:when>
								</xsl:choose>
							</xsl:variable>
							<xsl:variable name="TranType">Credit Card</xsl:variable>
							<!--<xsl:choose>
									<xsl:when test="$R11='CC'">Credit Card</xsl:when>
									<xsl:when test="$R11='ECHECK'">ACH</xsl:when>
									<xsl:otherwise>Other</xsl:otherwise>
								</xsl:choose>-->
							<xsl:variable name="RoutingBank">TSYS</xsl:variable>
							<xsl:variable name="TrnId">
								<xsl:choose>
									<xsl:when test="contains($APIType00, ':') and substring-before($R1, '=')='tranNr' and starts-with(substring-after($R4, '='), 'Approved')">
										<xsl:value-of select="substring-after($R1, '=')"/>
									</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and substring-before($R1, '=')='ResponseCode'">
										<xsl:value-of select="substring-after($R2, '=')"/>
									</xsl:when>
									<xsl:otherwise>0</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xsl:variable name="ClientId0">
								<xsl:choose>
									<xsl:when test="contains($APIType00, ':') and substring-before($R2, '=')='CustRefID'">
										<xsl:value-of select="substring-after($R2, 'CustRefID=')"/>
									</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and //iwtransformationserver/iwrecordset/transaction[@name=$TrnName]/datamap[@name='payauthinfo']/data/row/col[@name='Result']">
										<xsl:variable name="ResT">
											<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction[@name=$TrnName]/datamap[@name='payauthinfo']/data/row/col[@name='Result']">
												<xsl:variable name="ResultI" select="concat(translate(., $DBLQ, ''), '&amp;')"/>
												<xsl:if test="not(contains($ResultI, 'HTML'))">
													<xsl:variable name="R1I" select="substring-before($ResultI, '&amp;')"/>
													<xsl:variable name="R1Ic">
														<xsl:value-of select="$R1I"/>&amp;</xsl:variable>
													<xsl:variable name="R1Ia" select="substring-after($ResultI, $R1Ic)"/>
													<xsl:variable name="R2I" select="substring-before($R1Ia, '&amp;')"/>
													<xsl:if test="number(substring-after($R1I, 'tranNr='))=number($TrnId)">
														<xsl:value-of select="substring-after($R2I, 'CustRefID=')"/>;</xsl:if>
												</xsl:if>
											</xsl:for-each>
										</xsl:variable>
										<xsl:value-of select="substring-before($ResT, ';')"/>
									</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and $CreditMode0!=''">
										<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Client__c']"/>
									</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and $IdorName!='Id'">
										<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row/col[@name='Id']"/>
									</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and $TransactionSourceName!=''">
										<xsl:value-of select="$TransactionSourceName"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records' and position()=$Pos]/row/col[@name='Id']"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xsl:variable name="ClientId">
								<xsl:choose>
									<xsl:when test="string-length($ClientId0)=18">
										<xsl:value-of select="$ClientId0"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="substring($ClientId0, 1, 15)"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
								<soap:Header>
									<QueryOptions xmlns="urn:partner.soap.sforce.com">
										<batchSize>2000</batchSize>
									</QueryOptions>
									<SessionHeader xmlns="urn:partner.soap.sforce.com">
										<sessionId>
											<xsl:value-of select="$SessionId"/>
										</sessionId>
									</SessionHeader>
									<CallOptions xmlns="urn:partner.soap.sforce.com">
										<client>IntegrationTechnologies/Interweave/</client>
									</CallOptions>
								</soap:Header>
								<soap:Body>
									<create xmlns="urn:partner.soap.sforce.com">
										<sObjects>
											<type xmlns="urn:sobject.partner.soap.sforce.com">Transaction__c</type>
											<Approval_Status__c>
												<xsl:value-of select="$AppStatus"/>
											</Approval_Status__c>
											<Auth_net_Transaction_ID__c>
												<xsl:value-of select="$TrnId"/>
											</Auth_net_Transaction_ID__c>
											<xsl:choose>
												<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='PayAuthorizeNetAQSO']">
													<Sales_Order__c>
														<xsl:value-of select="$ClientId"/>
													</Sales_Order__c>
												</xsl:when>
												<xsl:otherwise>
													<Client__c>
														<xsl:value-of select="$ClientId"/>
													</Client__c>
												</xsl:otherwise>
											</xsl:choose>
											<Amount__c>
												<xsl:choose>
													<xsl:when test="contains($APIType00, ':')  and $SchedCCType='Cust' and //iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records' and row/col[@name='Id']=$ClientId]/rowset[@name='Scheduled_Payments__r']/rowset[@name='records']/row/col[@name='Payment_Amount__c']">
														<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records' and row/col[@name='Id']=$ClientId]/rowset[@name='Scheduled_Payments__r']/rowset[@name='records']/row/col[@name='Payment_Amount__c']"/>
													</xsl:when>
													<xsl:when test="contains($APIType00, ':') and //iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row[col[@name='Id']=$ClientId]/col[@name='Gross_Monthly_Charge__c']">
														<xsl:value-of select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']/row[col[@name='Id']=$ClientId]/col[@name='Gross_Monthly_Charge__c']"/>
													</xsl:when>
													<xsl:when test="not(contains($APIType00, ':')) and substring-before($R1, '=')='ResponseCode' and substring-after($R4, '=')!=''">
														<xsl:value-of select="number(substring-after($R4, '=')) div 100"/>
													</xsl:when>
													<xsl:otherwise>0.00</xsl:otherwise>
												</xsl:choose>
											</Amount__c>
											<Date_Ran__c>%current_date%</Date_Ran__c>
											<Declined_Comments__c>
												<xsl:value-of select="$DeclCom"/>
											</Declined_Comments__c>
											<Due_Date_Override__c>
												<xsl:value-of select="$DueOver"/>
											</Due_Date_Override__c>
											<Transaction_Status__c>
												<xsl:value-of select="$TranStat"/>
											</Transaction_Status__c>
											<Transaction_Type__c>
												<xsl:value-of select="$TranType"/>
											</Transaction_Type__c>
											<First_Recurring__c>
												<xsl:value-of select="$PayType"/>
											</First_Recurring__c>
											<Routing_Bank__c>
												<xsl:value-of select="$RoutingBank"/>
											</Routing_Bank__c>
											<!--<xsl:if test="$ShowCCTypes='Yes'">
												<Credit_Card_Type__c>
													<xsl:value-of select="$R52"/>
												</Credit_Card_Type__c>
											</xsl:if>-->
										</sObjects>
									</create>
								</soap:Body>
							</soap:Envelope>`</xsl:if>
					</xsl:for-each>
				</xsl:when>
				<xsl:when test="starts-with($APIType, 'CARDPOINT')">
					<xsl:for-each select="iwrecordset/transaction[@name=$TrnName]/datamap[@name=$DSName]/data/rowset[@name='o']/row">
						<xsl:variable name="AppStatus">
							<xsl:choose>
								<xsl:when test="number(col[@name='respcode'])=0">Approved</xsl:when>
								<!--<xsl:when test="not(contains($APIType00, ':')) and substring-before($R1, '=')='ResponseCode' and substring-after($R1, '=')='00'">Approved</xsl:when>
								<xsl:when test="not(contains($APIType00, ':')) and ((substring-before($R1, '=')='ResponseCode' and substring-after($R1, '=')='06') or substring-before($R1, '=')='ErrorCode')">Error</xsl:when>
									<xsl:when test="not(contains($APIType00, ':')) and (substring-before($R1, '=')='ResponseCode' and (substring-after($R1, '=')='06' or substring-after($R1, '=')='08' or substring-after($R1, '=')='11'))">Declined</xsl:when>-->
								<xsl:otherwise>Declined</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						<xsl:variable name="DeclCom">
							<xsl:if test="number(col[@name='respcode'])!=0">
								<xsl:value-of select="col[@name='resptext']"/>
							</xsl:if>
						</xsl:variable>
						<xsl:variable name="Is1stPmnt">
							<xsl:for-each select="//iwtransformationserver/iwrecordset/transaction/datamap[@name='retrieveacct']/data/rowset[@name='Envelope']/rowset[@name='Body']/rowset[@name='queryResponse']/rowset[@name='result']/rowset[@name='records']">
								<xsl:variable name="TB" select="row/col[@name='Total_Balance__c']"/>
								<xsl:choose>
									<xsl:when test="$TB='' or number($TB)=number(0.0)">true</xsl:when>
									<xsl:otherwise>false</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</xsl:variable>
						<xsl:variable name="DueOver">0</xsl:variable>
						<xsl:variable name="TranStat">Processed</xsl:variable>
						<xsl:variable name="PayType">
							<xsl:choose>
								<xsl:when test="$CreditMode0='' and $Is1stPmnt='true'">First Payment</xsl:when>
								<xsl:when test="$CreditMode0=''">Recurring Payment</xsl:when>
								<xsl:when test="$CreditMode='REFUND'">Refund</xsl:when>
								<xsl:when test="$CreditMode='VOID'">Void</xsl:when>
							</xsl:choose>
						</xsl:variable>
						<xsl:variable name="TranType">Credit Card</xsl:variable>
						<!--<xsl:choose>
									<xsl:when test="$R11='CC'">Credit Card</xsl:when>
									<xsl:when test="$R11='ECHECK'">ACH</xsl:when>
									<xsl:otherwise>Other</xsl:otherwise>
								</xsl:choose>-->
						<xsl:variable name="RoutingBank">CARDPOINT</xsl:variable>
						<xsl:variable name="TrnId" select="col[@name='authcode']"/>
						<xsl:variable name="ClientId0" select="col[@name='orderId']"/>
						<xsl:variable name="ClientId">
							<xsl:choose>
								<xsl:when test="string-length($ClientId0)=18">
									<xsl:value-of select="$ClientId0"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="substring($ClientId0, 1, 15)"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
							<soap:Header>
								<QueryOptions xmlns="urn:partner.soap.sforce.com">
									<batchSize>2000</batchSize>
								</QueryOptions>
								<SessionHeader xmlns="urn:partner.soap.sforce.com">
									<sessionId>
										<xsl:value-of select="$SessionId"/>
									</sessionId>
								</SessionHeader>
								<CallOptions xmlns="urn:partner.soap.sforce.com">
									<client>IntegrationTechnologies/Interweave/</client>
								</CallOptions>
							</soap:Header>
							<soap:Body>
								<create xmlns="urn:partner.soap.sforce.com">
									<sObjects>
										<type xmlns="urn:sobject.partner.soap.sforce.com">Transaction__c</type>
										<Approval_Status__c>
											<xsl:value-of select="$AppStatus"/>
										</Approval_Status__c>
										<Auth_net_Transaction_ID__c>
											<xsl:value-of select="$TrnId"/>
										</Auth_net_Transaction_ID__c>
										<Retrieval_Reference_Number__c>
											<xsl:value-of select="col[@name='retref']"/>
										</Retrieval_Reference_Number__c>
										<xsl:choose>
											<xsl:when test="//iwtransformationserver/iwrecordset/transaction[@name='PayAuthorizeNetAQSO']">
												<Sales_Order__c>
													<xsl:value-of select="$ClientId"/>
												</Sales_Order__c>
											</xsl:when>
											<xsl:otherwise>
												<Client__c>
													<xsl:value-of select="$ClientId"/>
												</Client__c>
											</xsl:otherwise>
										</xsl:choose>
										<Amount__c>
											<xsl:value-of select="col[@name='amount']"/>
										</Amount__c>
										<Date_Ran__c>%current_date%</Date_Ran__c>
										<Declined_Comments__c>
											<xsl:value-of select="$DeclCom"/>
										</Declined_Comments__c>
										<Due_Date_Override__c>
											<xsl:value-of select="$DueOver"/>
										</Due_Date_Override__c>
										<Transaction_Status__c>
											<xsl:value-of select="$TranStat"/>
										</Transaction_Status__c>
										<Transaction_Type__c>
											<xsl:value-of select="$TranType"/>
										</Transaction_Type__c>
										<First_Recurring__c>
											<xsl:value-of select="$PayType"/>
										</First_Recurring__c>
										<Routing_Bank__c>
											<xsl:value-of select="$RoutingBank"/>
										</Routing_Bank__c>
										<!--<xsl:if test="$ShowCCTypes='Yes'">
												<Credit_Card_Type__c>
													<xsl:value-of select="$R52"/>
												</Credit_Card_Type__c>
											</xsl:if>-->
									</sObjects>
								</create>
							</soap:Body>
						</soap:Envelope>`</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
