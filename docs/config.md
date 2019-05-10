# Configuring Salesforce Bulk Operations

[[Initializing the connector]](#initializing-the-connector)

Add the following <snmp.init> method in your configuration:
 
## Initializing the connector 
**init**
```xml
<snmp.init>
   <host>{$ctx:host}</host>
   <port>{$ctx:port}</port>
   <snmpVersion>{$ctx:snmpVersion}</snmpVersion>
   <community>{$ctx:community}</community>
   <retries>{$ctx:retries}</retries>
   <timeout>{$ctx:timeout}</timeout>
</snmp.init>
```
**Properties** 
* host: Required - The address of the target device.
* port: Required - The port number of SNMP.
* snmpVersion: The SNMP version.
* community:Required - The community string that acts as a password. This is used to authenticate messages that are sent * between the management station and the device (the SNMP Agent).
* retries: The number of times a request should be sent when a timeout occurs.
* timeout: The time interval that a request waits for a response message from an agent.

Now that you have connected to SNMP, use the information in the following topics to perform various operations with the connector:

[Working with SNMP](snmp.md)
