import spidev
import time
import Mqtt
import json  
import led
import MqttConfig as config
temp_sensor_channel = 0
moisture_sensor_channel = 3
light_sensor_channel = 3
#print ("\nPlease the linker_temperature is connected to A%1d\n" % temp_channel)
time.sleep(3)
 
spi = spidev.SpiDev()
spi.open(0,0)
 
def readadc(adcnum):
# read SPI data from MCP3004 chip, 4 possible adc's (0 thru 3)
    if adcnum > 3 or adcnum < 0:
        return -1
    r = spi.xfer2([1,8+adcnum <<4,0])
    adcout = ((r[1] &3) <<8)+r[2]
    return adcout


def on_message(client, userdata, msg):
	json_string = str(msg.payload)
        data = json.loads(json_string)
	if(data['Alert']=="Warning"):
		print "Plant's health is not in Good condition"
                led.start_ledbar()
	else: 
		print "Plant is in Good Health"
                led.stop_ledbar()        
mqtt_client = Mqtt.MqttClient(config.clientId,True,config.protocol,config.username,config.password,config.host,config.port)
mqtt_client.setTopics(config.publishTopic,config.subscribeTopic)
mqtt_client.init()
mqtt_client.mqttClient.on_message = on_message;
while True:
	temp_value = readadc(temp_sensor_channel)
	volts_value = readadc(light_sensor_channel) 
	moisture_value = readadc(moisture_sensor_channel)
	#print("temperature=%f"%temp_value)
	#print("moisture=%f"%moisture_value)
	mqtt_client.mqttClient.loop()
	json_readings={}
	json_readings["d"] = {}
	json_readings["d"]["temperature"]=temp_value
	json_readings["d"]["moisture"]=moisture_value
	json_readings["d"]["light"]=volts_value
	json_readings["d"]["deviceId"]= config.deviceId
	print(json.dumps(json_readings))
	mqtt_client.publish(json.dumps(json_readings))
	#print("-------------------------")
	#mqtt_client.mqttClient.loop_forever(timeout=3.0,retry_first_connection=True)
	time.sleep(5)
