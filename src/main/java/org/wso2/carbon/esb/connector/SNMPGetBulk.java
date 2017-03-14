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
import org.apache.commons.lang.StringUtils;
import org.apache.synapse.MessageContext;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.Connector;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Vector;

/*
 * Class SNMPGetBulk is to perform SNMPGetBulk operation.
 *
 * @since 1.0.0
 */
public class SNMPGetBulk extends AbstractConnector implements Connector {
	private Snmp snmp;

	/*
	 * Connect method which is initiating the GetBulk operation.
	 *
	 * @param messageContext ESB message context
	 */
	@Override
	public void connect(MessageContext messageContext) throws ConnectException {
		String oids = (String) messageContext.getProperty(SNMPConstants.OIDs);
		String maxRepetition = (String) messageContext.getProperty(SNMPConstants.MAX_REPETITION);
		String nonRepeater = (String) messageContext.getProperty(SNMPConstants.NON_REPEATER);

		try {
			if (StringUtils.isNotEmpty(maxRepetition)) {
				// Create TransportMapping
				TransportMapping transport = new DefaultUdpTransportMapping();
				// Create Snmp object for sending data to Agent
				snmp = new Snmp(transport);
				// Listens for response
				transport.listen();
				// Create the PDU object
				PDU pdu = new PDU();
				SNMPUtils.addOids(oids, pdu);
				pdu.setType(PDU.GETBULK);

				//set the max-repetitions
				pdu.setMaxRepetitions(Integer.parseInt(maxRepetition));

				//set the nonrepeaters
				if (StringUtils.isNotEmpty(nonRepeater)) {
					pdu.setNonRepeaters(Integer.parseInt(nonRepeater));
				}
				if (log.isDebugEnabled()) {
					log.debug("Sending Request to the Agent.");
				}
				//sending data to Agent
				ResponseEvent response = snmp.send(pdu, SNMPUtils.getTarget(messageContext));
				// Process Agent Response
				if (response != null) {
					if (log.isDebugEnabled()) {
						log.debug("Got Response from the Agent.");
					}
					PDU responsePDU = response.getResponse();
					if (responsePDU != null) {
						int errorStatus = responsePDU.getErrorStatus();
						int errorIndex = responsePDU.getErrorIndex();
						String errorStatusText = responsePDU.getErrorStatusText();
						if (errorStatus == PDU.noError) {
							OMElement element;
							String result = "";
							StringBuilder sb = new StringBuilder();
							Vector<? extends VariableBinding> vbs = responsePDU.getVariableBindings();
							for (VariableBinding vb : vbs) {
								result = result + vb + ", ";
								sb.append(vb).append(", ");
							}
							result = sb.toString().trim().substring(0, sb.toString().trim().length() - 1);
							element = SNMPUtils.transformMessages(SNMPConstants.START_TAG + result +
							                                      SNMPConstants.END_TAG);
							SNMPUtils.preparePayload(messageContext, element);
						} else {
							handleException("Request Failed:" + "Status = " + errorStatus + ", Index = " +
							                errorIndex + ", Status Text = " + errorStatusText, messageContext);
						}
					} else {
						handleException("Response PDU is null.", messageContext);
					}
				} else {
					handleException("Agent Timeout occurred.", messageContext);
				}
			} else {
				handleException("MaxRepetition value is null.", messageContext);
			}
		} catch (IOException e) {
			handleException("Error while processing the SNMPGetBulk: " + e.getMessage(), e, messageContext);
		} catch (XMLStreamException e) {
			handleException("Error while building the message: " + e.getMessage(), e, messageContext);
		} finally {
			try {
				snmp.close();
			} catch (IOException e) {
				handleException("Error while closing the SNMP: " + e.getMessage(), e, messageContext);
			}
		}
	}
}