/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.esb.connector;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.soap.SOAPBody;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Target;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;


import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
 * Class SNMPUtils defines the common methods to perform all SNMP Connector operations.
 *
 * @since 1.0.0
 */
public class SNMPUtils {
	private static final Log log = LogFactory.getLog(SNMPUtils.class);

	/**
	 * Create Target Address object
	 *
	 * @param messageContext the message context
	 * @return target
	 */
	public static Target getTarget(MessageContext messageContext) {
		String host = (String) messageContext.getProperty(SNMPConstants.HOST);
		String port = (String) messageContext.getProperty(SNMPConstants.PORT);
		String snmpVersion = (String) messageContext.getProperty(SNMPConstants.SNMP_VERSION);
		String community = (String) messageContext.getProperty(SNMPConstants.COMMUNITY);
		String retries = (String) messageContext.getProperty(SNMPConstants.RETRIES);
		String timeout = (String) messageContext.getProperty(SNMPConstants.TIMEOUT);
		String isTcp = (String) messageContext.getProperty(SNMPConstants.IS_TCP);

		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		if (Boolean.parseBoolean(isTcp)) {
			target.setAddress(new TcpAddress(host + SNMPConstants.COMBINER + port));
		} else {
			target.setAddress(new UdpAddress(host + SNMPConstants.COMBINER + port));
		}
		if (StringUtils.isEmpty(snmpVersion) || snmpVersion.equals(SNMPConstants.SNMP_VERSION2C)) {
			target.setVersion(SnmpConstants.version2c);
		} else if (snmpVersion.equals(SNMPConstants.SNMP_VERSION1)) {
			target.setVersion(SnmpConstants.version1);
		} else	{
			if (log.isDebugEnabled()) {
				log.info("SNMP connector doesn't support SNMP version other than 1 and 2c");
			}
		}
		if (StringUtils.isEmpty(retries)) {
			target.setRetries(Integer.parseInt(SNMPConstants.DEFAULT_RETRIES));
		} else {
			target.setRetries(Integer.parseInt(retries));
		}
		if (StringUtils.isEmpty(timeout)) {
			target.setTimeout(Integer.parseInt(SNMPConstants.DEFAULT_TIMEOUT));
		} else {
			target.setTimeout(Integer.parseInt(timeout));
		}
		return target;
	}

	/**
	 * Add the multiple OIDs into PDU
	 *
	 * @param oids set of OIDs
	 * @param pdu  SNMP protocol data unit
	 * @return pdu
	 */
	public static PDU addOids(String oids, PDU pdu) throws IOException {
		List<String> oidsList = null;
		if (StringUtils.isNotEmpty(oids)) {
			oidsList = Arrays.asList(oids.split(SNMPConstants.COMMA));
		}
		if (oidsList != null) {
			for (String oid : oidsList) {
				pdu.add(new VariableBinding(new OID(oid)));
			}
		}
		return pdu;
	}

	/**
	 * Prepare the payload
	 *
	 * @param messageContext The message context that is processed by a handler in the handle method
	 * @param element        The OMElement
	 */
	public static void preparePayload(MessageContext messageContext, OMElement element) {
		SOAPBody soapBody = messageContext.getEnvelope().getBody();
		for (Iterator itr = soapBody.getChildElements(); itr.hasNext(); ) {
			OMElement child = (OMElement) itr.next();
			child.detach();
		}
		for (Iterator itr = element.getChildElements(); itr.hasNext(); ) {
			OMElement child = (OMElement) itr.next();
			soapBody.addChild(child);
		}
	}

	/**
	 * Create a OMElement
	 *
	 * @param output The output string
	 * @return return resultElement
	 */
	public static OMElement transformMessages(String output) throws XMLStreamException {
		OMElement resultElement;
		resultElement = AXIOMUtil.stringToOM(output);
		return resultElement;
	}
}
