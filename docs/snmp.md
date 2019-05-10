# Working with SNMP

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview
The following operations allow you to work with SNMP. Click an operation name to see details on how to use it.

For a sample proxy service that illustrates how to work with operations, see [Sample configuration](#sample-configuration.

| Operation        | Description |
| ------------- |-------------|
|[snmpGet](#retrieving-information-from-the-managed-device) |	Retrieves information from the managed device.|
|[snmpGetBulk](#retrieving-voluminous-data-from-large-mib-tables)	|   Retrieves voluminous data from large MIB tables.|
|[snmpGetNext](#retrieving-the-value-of-the-next-object-identifier)	|   Retrieves the value of the next object identifier.|
|[snmpSet](#changing-the-current-value-of-a-mib-object)	|   Changes the current value of a MIB object.|
|[snmpTrap](#send-trap-message) | Sends a trap message to SNMP manager. |

### Operation details

This section provides further details on the operations related to the SNMP operations.

### Retrieving information from the managed device
The snmpGet operation retrieves information from the managed device based on the specified object identifiers (OIDs).

**snmpGet**
```xml
<snmp.snmpGet>
    <oids>{$ctx:oids}</oids>
    <requestId>{$ctx:requestId}</requestId>
</snmp.snmpGet>
```

**Properties**

* Oids : Required - The list of object identifiers (OIDs).
* requestId : The identifier of the request.

**Sample request**
Following is a sample REST/JSON request that can be handled by the snmpGet operation.

```json
{ 
   "host":"127.0.0.1",
   "port":"161",
   "snmpVersion":"2c",
   "oids":"1.3.6.1.2.1.1.7.0,1.3.6.1.2.1.1.5.0,1.3.6.1.2.1.1.6.0",
   "community":"private",
   "retries":"2",
   "timeout":"1000"
}
```
**Sample response**

Given below is a sample response for the snmpGet operation.
```json
{
    "Response":"[1.3.6.1.2.1.1.7.0 = 76, 1.3.6.1.2.1.1.5.0 = keerthu.local, 1.3.6.1.2.1.1.6.0 = Right here, right now.]"
}
```

### Retrieving voluminous data from large MIB tables
The snmpGetBulk operation retrieves voluminous data, particularly from a large MIB table at once.

**snmpGetBulk**
```xml
<snmp.snmpGetBulk>
    <oids>{$ctx:oids}</oids>
    <maxRepetition>{$ctx:maxRepetition}</maxRepetition>
    <nonRepeater>{$ctx:nonRepeater}</nonRepeater>
</snmp.snmpGetBulk>
```

**Properties**
* Oids : Required - The list of  object identifiers (OIDs) .
* maxRepetition  : Required - The maximum number of iterations over the repeating variable bindings.
* onRepeater :  The number of non-repeating variable bindings.

**Sample request**
Following is a sample REST/JSON request that can be handled by the snmpGetBulk operation.  
```json
{ 
   "host":"127.0.0.1",
   "port":"161",
   "snmpVersion":"2c",
   "oids":"1.3.6.1.2.1.1.7.0,1.3.6.1.2.1.1.5.0,1.3.6.1.2.1.1.6.0",
   "community":"private",
   "retries":"2",
   "timeout":"1000",
   "maxRepetition":"1",
   "nonRepeater":"1"
}
```
**Sample response**

Given below is a sample response for the snmpGetBulk operation.
```json
{
    "Response":"1.3.6.1.2.1.1.8.0 = 0:00:00.00, 1.3.6.1.2.1.1.6.0 = Right here, right now., 1.3.6.1.2.1.1.7.0 = 76, 1.3.6.1.2.1.1.7.0 = 76, 1.3.6.1.2.1.1.8.0 = 0:00:00.00, 1.3.6.1.2.1.1.8.0 = 0:00:00.00, 1.3.6.1.2.1.1.9.1.2.1 = 1.3.6.1.6.3.11.2.3.1.1"
}
```

### Retrieving the value of the next object identifier
The snmpGetNext operation retrieves the value of the lexicographically next object in the MIB.

**snmpGetNext**
```xml
<snmp.snmpGetNext>
    <oids>{$ctx:oids}</oids>
    <requestId>{$ctx:requestId}</requestId>
</snmp.snmpGetNext>
```

**Properties**

* Oids: Required - The list of  object identifiers (OIDs) .
* requestId: The identifier of the request.

**Sample request**  
 Following is a sample REST/JSON request that can be handled by the snmpGetNext operation.

```json
{ 
   "host":"127.0.0.1",
   "port":"161",
   "snmpVersion":"2c",
   "oids":"1.3.6.1.2.1.1.7.0,1.3.6.1.2.1.1.5.0,1.3.6.1.2.1.1.6.0",
   "community":"private",
   "retries":"2",
   "timeout":"1000"
}
```
**Sample response**

Given below is a sample response for the snmpGetNext operation.
```json
{
    "Response":"[1.3.6.1.2.1.1.8.0 = 0:00:00.00, 1.3.6.1.2.1.1.6.0 = Right here, right now., 1.3.6.1.2.1.1.7.0 = 76]"
}
```

### Changing the current value of a MIB object
The snmpSet operation changes the current value of a managed object or creates a new row in a table. 

**snmpSet**
```xml
<snmp.snmpSet>
    <updateOids>{$ctx:updateOids}</updateOids>
</snmp.snmpSet>
```
**Properties**
* updateOids: Required - A JSON array of t he object identifiers (OIDs) that need to be updated, and the new value and data type for each OID.

**Sample request**
Following is a sample REST/JSON request that can be handled by the snmpSet operation.

```json
{ 
   "host":"127.0.0.1",
   "port":"161",
   "snmpVersion":"2c",
   "updateOids":[ 
      { 
         "oid":"1.3.6.1.2.1.1.4.0",
         "value":"vives@it-slav.net",
         "type":"String"
      },
      { 
         "oid":"1.3.6.1.2.1.1.5.0",
         "value":"TestValue",
         "type":"String"
      },
      { 
         "oid":"1.3.6.1.2.1.1.6.0",
         "value":"1",
         "type":"Integer"
      }
   ],
   "community":"private",
   "retries":"2",
   "timeout":"1000"
}
```

**Sample response**

Given below is a sample response for the snmpSet operation.
```json
{
    "Response":"OIDs successfully updated with [1.3.6.1.2.1.1.4.0 = vives@it-slav.net, 1.3.6.1.2.1.1.5.0 = TestValue, 1.3.6.1.2.1.1.6.0 = 1]"
}
```

### Send Trap message
The snmpTrap operation sends a trap message to SNMP manager.

**snmpTrap**
```xml
<snmp.snmpTrap>
   <trapOids>{$ctx:trapOids}</trapOids>
   <trapHost>{$ctx:trapHost}</trapHost>
   <isTcp>{$ctx:isTcp}</isTcp>
</snmp.snmpTrap>
```

**Properties**

* trapOids : Required - give a JsonObject which has set of trap oid with trap name.
* trapHost : Host of the source sending the trap.
* isTCP : Required - which type of the protocol used in the Transport Layaer.

**Sample request**
 Following is a sample REST/JSON request that can be handled by the snmpTrap operation.

```json
{
   "host":"127.0.0.1",
   "port":"162",
   "snmpVersion":"2c",
   "oids":".1.3.6.1.2.1.1.8",
   "community":"public",
   "retries":"2",
   "timeout":"1000",
   "trapOids":{".1.3.6.1.2.1.1.3":"majaor",".1.3.6.1.2.1.1.5":"minor"},
   "trapHost":"192.168.1.9",
   "isTcp":"false"
}
```
**Sample response**

Given below is a sample response for the snmpTrap operation.
```json
{
	"result": {
		"Success": "true"
	}
}
```
### Sample configuration
Following example illustrates how to connect to SNMP with the init operation and snmpSet operation.

1. Create a sample proxy as below :

```xml
 <proxy xmlns="http://ws.apache.org/ns/synapse"
       name="SNMPSet"
       startOnLoad="true"
       statistics="disable"
       trace="disable"
       transports="https,http,local">
   <target>
      <inSequence>
         <property expression="json-eval($.host)" name="host"/>
         <property expression="json-eval($.port)" name="port"/>
         <property expression="json-eval($.snmpVersion)" name="snmpVersion"/>
         <property expression="json-eval($.community)" name="community"/>
         <property expression="json-eval($.retries)" name="retries"/>
         <property expression="json-eval($.timeout)" name="timeout"/>
         <property expression="json-eval($.updateOids)" name="updateOids"/>
         <snmp.init>
            <host>{$ctx:host}</host>
            <port>{$ctx:port}</port>
            <snmpVersion>{$ctx:snmpVersion}</snmpVersion>
            <community>{$ctx:community}</community>
            <retries>{$ctx:retries}</retries>
            <timeout>{$ctx:timeout}</timeout>
         </snmp.init>
         <snmp.snmpSet>
            <updateOids>{$ctx:updateOids}</updateOids>
         </snmp.snmpSet>
         <respond/>
      </inSequence>
   </target>
   <description/>
</proxy>
```

2. Create an XML file named snmpSet.json and copy the configurations given below to it:

```json
{ 
   "host":"127.0.0.1",
   "port":"161",
   "snmpVersion":"2c",
   "updateOids":[ 
      { 
         "oid":"1.3.6.1.2.1.1.4.0",
         "value":"vives@it-slav.net",
         "type":"String"
      },
      { 
         "oid":"1.3.6.1.2.1.1.5.0",
         "value":"TestValue",
         "type":"String"
      },
      { 
         "oid":"1.3.6.1.2.1.1.6.0",
         "value":"1",
         "type":"Integer"
      }
   ],
   "community":"private",
   "retries":"2",
   "timeout":"1000"
}
```
3. Replace the oids with your values.

4. Execute the following curl command:

```bash
curl http://localhost:8280/services/SNMPSet -H "Content-Type: application/json" -d @snmpSet.json
```

5. SNMP returns a JSON response similar to the one shown below:

{
    "Response":"OIDs successfully updated with [1.3.6.1.2.1.1.4.0 = vives@it-slav.net, 1.3.6.1.2.1.1.5.0 = TestValue, 1.3.6.1.2.1.1.6.0 = 1]"
}