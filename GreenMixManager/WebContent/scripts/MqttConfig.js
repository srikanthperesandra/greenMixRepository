/**
 * @author srikanth
 */

var MQttConfig = (function(){
					return {
								clientId:"d:j3yjz2:MqttDeevice:UIDevice",
								//clientId:"d:j3yjz2:MqttDeevice:testDevice",
								user:"use-token-auth",
								password:"nfMx-RyV8zk0@DOkm3",
								//password:"testToken",
								host:"j3yjz2.messaging.internetofthings.ibmcloud.com",
								port:1883,
								publishTopic:"iot-2/evt/eid/fmt/json",
								subscribeTopic:"iot-2/cmd/cid/fmt/json"
						};
})()