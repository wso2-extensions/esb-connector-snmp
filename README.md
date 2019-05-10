
#SNMP EI Connector

The SNMP [Connector](https://docs.wso2.com/display/EI650/Working+with+Connectors) allows you to monitor and configure network components such as servers, routers, switches or printers via WSO2 ESB. This connector supports Simple Network Management Protocol(SNMP) Version 2, and uses the snmp4j library, which is an enterprise class free open source SNMP implementation for Java™ SE.

SNMP is a popular protocol used for managing devices on IP networks.

## Compatibility

| Connector version | Supported SNMP4J version | Supported WSO2 ESB/EI version |
| ------------- | ------------- | ------------- |
| [1.0.2](https://github.com/wso2-extensions/esb-connector-snmp/tree/org.wso2.carbon.esb.connector.SNMP-1.0.2) | 2.5.5 | EI 6.5.0    |
| [1.0.1](https://github.com/wso2-extensions/esb-connector-snmp/tree/org.wso2.carbon.esb.connector.SNMP-1.0.1) | 2.5.5 | EI 6.1.1, EI 6.3.0, EI 6.4.0    |
| [1.0.0](https://github.com/wso2-extensions/esb-connector-snmp/tree/org.wso2.carbon.esb.connector.SNMP-1.0.0) | 2.5.5 | ESB 4.9.0, ESB 5.0.0 |

## Getting started

#### Download and install the connector

1. Download the connector from the [WSO2 Store](https://store.wso2.com/store/assets/esbconnector/details/95dd3803-9abb-47c0-a0e9-c3393485b0e3) by clicking the Download Connector button.
2. Then you can follow this [Documentation](https://docs.wso2.com/display/EI650/Working+with+Connectors+via+the+Management+Console) to add and enable the connector via the Management Console in your EI instance.
3. For more information on using connectors and their operations in your EI configurations, see [Using a Connector](https://docs.wso2.com/display/EI650/Using+a+Connector).
4. If you want to work with connectors via EI tooling, see [Working with Connectors via Tooling](https://docs.wso2.com/display/EI650/Working+with+Connectors+via+Tooling).

#### Configuring the connector operations

To get started with SNMP connector and their operations, see [Configuring SNMP Operations](docs/config.md).


## Building From the Source

Follow the steps given below to build the SNMP connector from the source code:

1. Get a clone or download the source from [Github](https://github.com/wso2-extensions/esb-connector-snmp).
2. Run the following Maven command from the `esb-connector-snmp` directory: `mvn clean install`.
3. The SNMP connector zip file is created in the `esb-connector-snmp/target` directory

## How You Can Contribute

As an open source project, WSO2 extensions welcome contributions from the community.
Check the [issue tracker](https://github.com/wso2-extensions/esb-connector-snmp/issues) for open issues that interest you. We look forward to receiving your contributions.
