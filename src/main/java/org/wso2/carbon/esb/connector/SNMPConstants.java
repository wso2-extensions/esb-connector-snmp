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

/*
 * Class SNMPConstants defines all constants used for SNMP Connector.
 *
 * @since 1.0.0
 */
public class SNMPConstants {
	// Constant for host.
	public static final String HOST = "host";
	// Constant for port.
	public static final String PORT = "port";
	// Constant for OIDs.
	public static final String OIDs = "oids";
	// Constant for host SNMP version 2c.
	public static final String SNMP_VERSION2C = "2c";
	// Constant for host SNMP Version 1.
	public static final String SNMP_VERSION1 = "1";
	// Constant for SNMP Version.
	public static final String SNMP_VERSION = "snmpVersion";
	// Constant for Community String.
	public static final String COMMUNITY = "community";
	// Constant for retries.
	public static final String RETRIES = "retries";
	// Constant for timeout.
	public static final String TIMEOUT = "timeout";
	// Constant for request ID.
	public static final String REQUEST_ID = "requestId";
	// Constant for Start tag.
	public static final String START_TAG = "<result><Response>";
	// Constant for End tag.
	public static final String END_TAG = "</Response></result>";
	// Constant for Comma.
	public static final String COMMA = ",";
	// Constant for Forward Slash.
	public static final String COMBINER = "/";
	// Constant for snmp.
	public static final String SNMP = "snmp";
	// Constant for OID.
	public static final String OID = "oid";
	// Constant for value.
	public static final String VALUE = "value";
	// Constant for Update OIDs.
	public static final String UPDATE_OIDS = "updateOids";
	// Constant for String.
	public static final String STRING = "string";
	// Constant for Octet string.
	public static final String OCTETSTRING = "octetstring";
	// Constant for Integer.
	public static final String INTEGER = "integer";
	// Constant for IP Address.
	public static final String IPADDRESS = "ipaddress";
	// Constant for counter32.
	public static final String COUNTER32 = "counter32";
	// Constant for counter64.
	public static final String COUNTER64 = "counter64";
        // Constant for Gauge32.
         public static final String GAUGE32 = "Gauge32";
	// Constant for default retries.
	public static final String DEFAULT_RETRIES = "5";
	// Constant for default time out.
	public static final String DEFAULT_TIMEOUT = "1000";
	// Constant for maximum repetition.
	public static final String MAX_REPETITION = "maxRepetition";
	// Constant for non repeater.
	public static final String NON_REPEATER = "nonRepeater";
	// Constant for data type.
	public static final String TYPE = "type";
	// Constant for protocol type.
	public static final String IS_TCP = "isTcp";
	// Constant for trap message oid.
	public static final String TRAP_OIDS = "trapOids";
	// Constant for host of the source sending the trap.
	public static final String SNMP_TRAP_HOST = "trapHost";
	// Constant for trap response start tag.
	public static final String TRAP_RESPONSE_START_TAG = "<result><Success>";
	// Constant for trap response end tag.
	public static final String TRAP_RESPONSE_END_TAG = "</Success></result>";
}
