/**
 * @author srikanth
 */

$.getScript("scripts/MQTT.js");
var MQttClient = (function(){
	
	return {
		
		pahoClient:null,
		isConnected:false,
		topics:{},
		init:function(obj){
			var thisRef = this;
			this.pahoClient = new Paho.MQTT.Client(obj.host, 
													Number(obj.port), 
													obj.clientId);
			
			this.pahoClient.onConnectionLost = function(message){
				alert(JSON.stringify(message));
				if (message.errorCode !== 0) {
				    alert("onConnectionLost:"+message.errorMessage);
				  }
				thisRef.pahoClient.disconnect();
			};
			this.pahoClient.onMessageArrived = function(data){
				   //alert(data);
				   obj.messageCallBack(data);
			   };
			
			this.pahoClient.connect({userName:obj.user,
									 password:obj.password,
									 onSuccess:function(){
										 		thisRef.isConnected = true;
										 		//thisRef.publish({name:"srikanth"});
										 		thisRef.subscribe();
										 		
										 		
										 		
										 		//alert("connected");
									 			}
								
									 		
									});
			
			
			
		},
		setTopics:function(obj){
			this.topics.subscribeTopic = obj.subscribe;
			this.topics.publishTopic = obj.publish;
		},
		publish:function(obj){
			//if(this.isConnected ){
				var mqttMessage = new Paho.MQTT.Message(JSON.stringify(obj));
				mqttMessage.destinationName = this.topics.publishTopic;
				this.pahoClient.send(mqttMessage);
			//}
		},
	   subscribe:function(){
		   if(this.isConnected){
			  
			   this.pahoClient.subscribe(this.topics.subscribeTopic,
						  
					  	{
				  		qos:0,
				  		onSuccess:function(message){
				  			//alert("subscribed");
				  			//alert("successfully subscribed "+JSON.stringify(message));
				  		},onFailure:function(message){
				  			alert(message);
				  		}
			  
				});
		   }
	   },
	   registerMessageArrivalCallBack:function(fun){
		   this.pahoClient.onMessageArrived = function(data){
			   alert(data);
			   fun(data);
		   };
	   }
	   
		
	};
	
	
	
})();