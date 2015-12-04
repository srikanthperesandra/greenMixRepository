/**
 * @author srikanth
 */

$.getScript("jqwidgets/jqxcore.js");
$.getScript("jqwidgets/jqxdraw.js");
$.getScript("jqwidgets/jqxchart.core.js");
$.getScript("jqwidgets/jqxdata.js");
$.getScript("scripts/MqttConfig.js");
$.getScript("scripts/mqtt.js");

var LiveDataWidget = (function(){
	
		return{
			
			widgetDom:null,
			plantsService_uri:"services/plants",
			plantsHealthService_uri:"services/plants/healthData",
			plantsData:null,
			chartDeviceId:null,
			chartSettings:{
				title: "Plants Health Data",
                description: "Variation of temperature, moisture and Light ",
                enableAnimations: true,
                showLegend: true,
               
                
                xAxis:
                {
                    dataField: 'Time',
                    gridLines: { visible: true },
                    title: { text: 'Discrete Time' }
                },
                colorScheme: 'scheme04',
                valueAxis:
                {
                    visible: true,
                    title: { text: 'Health data' }
                }
			},
			limit:10,
			init:function(id){
				this.widgetDom = $("#"+id);	
				var thisRef = this;
				$.ajax({ url: 'templates/LiveDataWidget.html', dataType: 'html', 
					success: function(response) { 
								try{
									//alert("called");
									thisRef.widgetDom.html(response);
									thisRef.getAllPlants();
									thisRef.getLiveData();
								}catch(e){
									alert(e);
								}
								
								//thisRef.preparePlantsGrid();
							}
							
				});
			},
			
			getLiveData:function(){
				var thisRef = this;
				MQttClient.setTopics({subscribe:MQttConfig.subscribeTopic,
									  publish:MQttConfig.publishTopic});
				
				MQttClient.init({"host":MQttConfig.host,
								 "port":MQttConfig.port,
								 "clientId":MQttConfig.clientId,
								 "user":MQttConfig.user,
								 "password":MQttConfig.password,
								 "messageCallBack":thisRef.handleMessage});
				
			
			},
			handleMessage:function(data){
				//alert(data.payloadString);
				var thisRef= this;
				var liveData=JSON.parse(data.payloadString);
				var ul="<div><b><i>"+liveData.d.deviceId+"</i></b></div><ul>";
				ul+="<li>Temperature:"+liveData.d.temperature+"</li>";
				ul+="<li>Moisture:"+liveData.d.moisture+"</li>";
				ul+="<li>Voltage:"+liveData.d.volts+"</li>";
				ul+="</ul>"
				var liveDiv=document.getElementById("liveDataMqtt");
				liveDiv.innerHTML=liveDiv.innerHTML+ul;
				//alert(LiveDataWidget.chartSettings);
				if(LiveDataWidget.chartDeviceId==liveData.d.deviceId){
					if(LiveDataWidget.chartSettings.seriesGroups!=null ||
							LiveDataWidget.chartSettings.seriesGroups!=undefined){
						//alert("called");
						//var len =LiveDataWidget.chartSettings.source.length;
						//LiveDataWidget.chartSettings.source.push(LiveDataWidget.chartSettings.source[len-1]-1);
						LiveDataWidget.chartSettings.seriesGroups[0].source.push(liveData.d.temperature);
						LiveDataWidget.chartSettings.seriesGroups[1].source.push(liveData.d.moisture*100);
						LiveDataWidget.chartSettings.seriesGroups[2].source.push(liveData.d.volts*100);
					}
					$('#dataGraph').jqxChart("getInstance").update();
				}
			},
			getAllPlants:function(){
				var thisRef = this;
				$.get( thisRef.plantsService_uri).done(function( data ) {
					//alert(data);
						thisRef.plantsData = data;
						thisRef.preparePlantsInput(data);
						
					}).fail(function(error){
						alert(JSON.stringify(error));
					});
			},
			preparePlantsInput:function(data){
				
				var plants= new Array();
				var thisRef = this;
				for(var i=0;i<data.length;i++)
					plants.push(data[i].plantName);
				$("#plantsInput").jqxInput({placeHolder: " Choose Plant", height: 25, width: 200, minLength: 1,theme:appConfig.theme,source:plants});
				$("#fetchLiveData").jqxButton({width:'40',theme:appConfig.theme});
				var deviceId =null;
				$("#fetchLiveData").on("click",function(){
					
					
					$.each(thisRef.plantsData,function(index,node){
						var  plant= $("#plantsInput").val();
						//alert(plant);
						if(node.plantName == plant){
							deviceId=node.plantDevice;
							thisRef.chartDeviceId = node.plantDevice
							//return false;
						}
					});
					
					
					$.get( thisRef.plantsHealthService_uri+"?limit="+thisRef.limit+"&device="+deviceId).done(function( data ) {
							var temperature = [];
							var moisture = [];
							var light = [];
							var timeValues = []
							
						$.each(data,function(index,node){
							temperature.push({"value":node.temperature});
							moisture.push({"value":node.moisture*100});
							light.push({"value":node.light*100});
							timeValues.push({"Time":node.id});
						});//end of $.each
						//alert(JSON.stringify(timeValues));
						if(thisRef.chartSettings.seriesGroups!=null ||
								thisRef.chartSettings.seriesGroups!=undefined){
							thisRef.chartSettings.source = timeValues;
							thisRef.chartSettings.seriesGroups[0].source = temperature;
							thisRef.chartSettings.seriesGroups[1].source = moisture;
							thisRef.chartSettings.seriesGroups[2].source = light;
						}else{
							
						
							thisRef.chartSettings.source = timeValues;
							thisRef.chartSettings.seriesGroups=
								[
								 {
		                            type: 'stackedline',
		                            source: temperature,
		                            series: [
		                                  { dataField: 'value', displayText: 'Temperature' }
		                            ]
								 },
								 {
		                            type: 'stackedline',
		                            source: moisture,
		                            series: [
		                                    { dataField: 'value', displayText: 'Moisture(%)' }
		                            ]
		                        },
		                        {
		                            type: 'stackedline',
		                            source: light,
		                            series: [
		                                    { dataField: 'value', displayText: 'Voltage(volts*100)' }
		                            ]
		                        }
		                    ];
						}
						
						
							if($('#dataGraph').jqxChart("getInstance")!=null)
								$('#dataGraph').jqxChart("getInstance").update();
							else
								$('#dataGraph').jqxChart(thisRef.chartSettings);
						 
						
					}).fail(function(error){
						alert(JSON.stringify(error));
					});
				
				});
			}
			
		};
	
})();