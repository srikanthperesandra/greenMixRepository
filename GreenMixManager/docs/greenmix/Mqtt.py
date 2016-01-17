#-*- coding: utf-8 -*-
import paho.mqtt.client as mqtt

#author Srikanth Peresandra Lakshminarayana

class MqttClient:
        def __init__(self,clientId,clean_session,protocol,username,password,host,port):
                self.mqttClient = mqtt.Client(clientId,clean_session,protocol)
                self.mqttUser = username
                self.mqttPassword = password
                self.mqttHost = host
                self.mqttPort = port

        def init(self):
                self.mqttClient.username_pw_set(self.mqttUser,self.mqttPassword)
		#self.mqttClient.connect(self.mqttHost,self.mqttPort)
                #self.mqttClient.on_message = self.onReception
                self.mqttClient.on_connect = self.onConnection
		self.mqttClient.connect(self.mqttHost,self.mqttPort)
        def setTopics(self,publishTopic,subscribeTopic):
                self.publishingTopic = publishTopic
                self.subscriptionTopic = subscribeTopic
        def onConnection(self,client, userdata, flags, rc):
   		print "connected "
                client.subscribe(self.subscriptionTopic)
        def publish(self,data):
                self.mqttClient.publish(self.publishingTopic,data)
        def onReception(self,client, userdata, msg):
                print "received message ",str(msg);
