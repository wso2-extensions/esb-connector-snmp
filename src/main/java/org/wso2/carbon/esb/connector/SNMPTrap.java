/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.Connector;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import javax.xml.stream.XMLStreamException;

/**
 * Creates an SnmpTrap object using passed parameters.
 */
public class SNMPTrap extends AbstractConnector implements Connector {
    private static final Log logger = LogFactory.getLog(SNMPTrap.class);
    private Snmp snmp;
    private PDU pdu;
    /*
     * Connect method which is initiating the SnmpTrap creates operation.
     *
     * @param messageContext ESB message context
     */
    @Override
    public void connect(MessageContext messageContext) {

        TransportMapping transport;
        String snmpVersion = (String) messageContext.getProperty(SNMPConstants.SNMP_VERSION);
        String trapHost = (String) messageContext.getProperty(SNMPConstants.SNMP_TRAP_HOST);
        String host = (String) messageContext.getProperty(SNMPConstants.HOST);
        String trapOids = (String) messageContext.getProperty(SNMPConstants.TRAP_OIDS);
        try {
            // Create Transport Mapping
            if (Boolean.parseBoolean(SNMPConstants.IS_TCP)) {
                transport = new DefaultTcpTransportMapping();
            } else {
                transport = new DefaultUdpTransportMapping();
            }
            transport.listen();
            if (StringUtils.isEmpty(snmpVersion) || snmpVersion.equals(SNMPConstants.SNMP_VERSION2C)) {
                pdu = createV2Pdu(trapOids, trapHost, messageContext);
            } else if (snmpVersion.equals(SNMPConstants.SNMP_VERSION1)) {
                pdu = createV1Pdu(trapOids, trapHost, messageContext);
            } else {
                generateOutput(messageContext, false);
                handleException("SNMP connector doesn't support SNMP version other than 1 and 2c" , messageContext);
            }
            snmp = new Snmp(transport);
            snmp.send(pdu, SNMPUtils.getTarget(messageContext));
            if (log.isDebugEnabled()) {
                log.debug(String.format("Trap message send successfully to %s ", host));
            }
            generateOutput(messageContext, true);
        } catch (IOException e) {
            generateOutput(messageContext, false);
            handleException(String.format("Error in Sending the trap message to %s", host) , (MessageContext) e);
        } finally {
            try {
                if (snmp != null) {
                    snmp.close();
                }
            } catch (IOException e) {
                handleException(String.format("Error in closing the snmp : %s", e.getMessage()), e, messageContext);
            }
        }
    }

    /**
     * Create the Pdu for snmp v2.
     * @param  trapOids  set of OIDs and value pairs.
     * @param  trapHost  host of the source sending the trap.
     * @return pdu contains the body of an SNMP message.
     */
    private PDU createV2Pdu(String trapOids, String trapHost, MessageContext messageContext) {

        pdu = new PDU(); // Create PDU
        pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new OctetString(new Date().toString())));
        pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress, new IpAddress(trapHost)));
        addPDU(trapOids, pdu, messageContext);
        pdu.setType(PDU.NOTIFICATION);
        return pdu;
    }

    /**
     * Create the Pdu for snmp v1.
     * @param trapOids	set of OIDs and value pairs.
     * @param trapHost	host of the source sending the trap.
     * @return pdu      contains the body of an SNMP message.
     */
    private PDU createV1Pdu(String trapOids, String trapHost, MessageContext messageContext) {

        PDUv1 pdu = new PDUv1();
        pdu.setType(PDU.V1TRAP);
        pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
        pdu.setSpecificTrap(1);
        pdu.setAgentAddress(new IpAddress(trapHost));
        pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new OctetString(new Date().toString())));
        pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress, new IpAddress(trapHost)));
        addPDU(trapOids, pdu, messageContext);
        return pdu;
    }

    /**
     * This method is capable of handling multiple OIDs.
     *
     * @param trapOids  OIDs and value pairs
     * @param pdu       SNMP protocol data unit.
     */
    private void addPDU(String trapOids, PDU pdu, MessageContext messageContext) {

        try {
            JSONObject jsonObjectTrapOid = new JSONObject(trapOids);
            if (jsonObjectTrapOid.length() == 0) {
                if (logger.isDebugEnabled()) {
                    logger.info("Unable to get the trap OID");
                }
                generateOutput(messageContext, false);
                handleException("Unable to get the trap OID", messageContext);
            }
            Iterator<?> keys = jsonObjectTrapOid.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                pdu.add(new VariableBinding(new OID(key), new OctetString(jsonObjectTrapOid.getString(key))));
            }
        } catch (JSONException e) {
            generateOutput(messageContext, false);
            handleException(String.format("Error occured while casting String to JsonObject: %s ", trapOids),
                    (MessageContext) e);
        }
    }

    /**
     * Generate the output payload.
     *
     * @param messageContext The message context that is processed by a handler in the handle method
     * @param resultStatus   Result of the status (true/false)
     */
    private void generateOutput(MessageContext messageContext, boolean resultStatus) {

        String response = SNMPConstants.TRAP_RESPONSE_START_TAG + resultStatus + SNMPConstants.TRAP_RESPONSE_END_TAG;
        OMElement element;
        try {
            element = SNMPUtils.transformMessages(response);
            SNMPUtils.preparePayload(messageContext, element);
        } catch (XMLStreamException e) {
            handleException(String.format("Error occurred while transforming response [%s] to XML. ", response),
                    (MessageContext) e);
        }
    }
}
