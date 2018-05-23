/*
* Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.connector.integration.test.snmp;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Integration test class for SNMP connector
 */
public class SnmpConnectorIntegrationTest extends ConnectorIntegrationTestBase {

	private final Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

	/**
	 * Set up the environment.
	 */
	@BeforeClass(alwaysRun = true)
	public void setEnvironment() throws Exception {
		String connectorName = System.getProperty("connector_name") + "-connector-" +
				System.getProperty("connector_version") + ".zip";
		init(connectorName);
		esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
		esbRequestHeadersMap.put("Content-Type", "application/json");
		esbRequestHeadersMap.put("Accept", "application/json");
	}

	/**
	 * Positive test case for snmpSet method with mandatory parameters.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpSet} integration test with mandatory parameters.")
	public void testSnmpSetWithMandatoryParameters() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpSet");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpSet_mandatory.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
		Assert.assertEquals(true, esbRestResponse.getBody().toString().contains("Response"));
	}

	/**
	 * Negative test case for snmpSet method.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpSet} integration test negative case.")
	public void testSnmpSetWithNegativeCase() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpSet");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpSet_negative.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 202);
	}

	/**
	 * Positive test case for snmpGet method with mandatory parameters.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGet} integration test with mandatory parameters.")
	public void testSnmpGetWithMandatoryParameters() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGet");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGet_mandatory.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
		Assert.assertEquals(true, esbRestResponse.getBody().toString().contains("Response"));
	}

	/**
	 * Positive test case for snmpGet method with optional parameters.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGet} integration test with optional parameters.")
	public void testSnmpGetWithOptionalParameters() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGet");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGet_optional.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
		Assert.assertEquals(true, esbRestResponse.getBody().toString().contains("Response"));
	}

	/**
	 * Negative test case for snmpGet method.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGet} integration test negative case.")
	public void testSnmpGetWithNegativeCase() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGet");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGet_negative.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 202);
	}

	/**
	 * Positive test case for snmpGetNext method with mandatory parameters.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGetNext} integration test with mandatory" + " parameters.")
	public void testSnmpGetNextWithMandatoryParameters() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGetNext");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGetNext_mandatory.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
		Assert.assertEquals(true, esbRestResponse.getBody().toString().contains("Response"));
	}

	/**
	 * Positive test case for snmpGetNext method with optional parameters.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGetNext} integration test with optional" + " parameters.")
	public void testSnmpGetNextWithOptionalParameters() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGetNext");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGetNext_optional.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
		Assert.assertEquals(true, esbRestResponse.getBody().toString().contains("Response"));
	}

	/**
	 * Negative test case for snmpGetNext method.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGetNext} integration test negative case.")
	public void testSnmpGetNextWithNegativeCase() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGetNext");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGetNext_negative.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 202);
	}

	/**
	 * Positive test case for snmpGetBulk method with mandatory parameters.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGetBulk} integration test with mandatory" + " parameters.")
	public void testSnmpGetBulkWithMandatoryParameters() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGetBulk");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGetBulk_mandatory.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
		Assert.assertEquals(true, esbRestResponse.getBody().toString().contains("Response"));
	}

	/**
	 * Negative test case for snmpGetBulk method.
	 *
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test(groups = { "wso2.esb" }, description = "snmp {snmpGetBulk} integration test negative case.")
	public void testSnmpGetBulkWithNegativeCase() throws IOException, JSONException {
		esbRequestHeadersMap.put("Action", "urn:snmpGetBulk");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "snmpGetBulk_negative.json");
		Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 202);
	}
}