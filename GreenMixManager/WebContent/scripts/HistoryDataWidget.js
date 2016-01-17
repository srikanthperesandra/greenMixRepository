/**
 * @author srikanth
 */

$.getScript("jqwidgets/jqxcore.js");
$.getScript("jqwidgets/jqxdraw.js");
$.getScript("jqwidgets/jqxchart.core.js");
$.getScript("jqwidgets/jqxdata.js");
$.getScript("jqwidgets/jqxdatetimeinput.js");
$.getScript("jqwidgets/jqxcalendar.js");
$.getScript("jqwidgets/jqxtooltip.js");
$.getScript("jqwidgets/globalization/globalize.js");

var HistoryDataWidget = (function(){
	
	return{
		widgetDom:null,
		plantsService_uri:"services/plants",
		plantsHealthService_uri:"services/plants/healthData/history",
		commonChartSettings:{
			title: "Plants Health Data",
            
            enableAnimations: true,
            showLegend: true,
           
            
            xAxis:
            {
                dataField: 'Time',
                gridLines: { visible: true },
                title: { text: 'Discrete Time' }
            },
            colorScheme: 'scheme04',
		},
		init:function(id){
			this.widgetDom = $("#"+id);	
			var thisRef = this;
			$.ajax({ url: 'templates/HistoryDataWidget.html', dataType: 'html', 
				success: function(response) { 
							try{
								//alert("called");
								thisRef.widgetDom.html(response);
								//alert(response);
								thisRef.instantiateWidgetComponents();
								//thisRef.getHistoryData();
							}catch(e){
								alert(e);
							}
							
							//thisRef.preparePlantsGrid();
						}
						
			});
		},
		
		instantiateWidgetComponents:function(){
			  var thisRef = this;
			  $("#fromDate").jqxDateTimeInput({ width: '150px', height: '25px' });
			  $("#toDate").jqxDateTimeInput({ width: '150px', height: '25px' });
			  $("#fetchHistoryData").jqxButton({width:'40',theme:appConfig.theme});
			  $("#fetchHistoryData").on("click",function(){
				  try{
				  	thisRef.getHistoryData();
				  }catch(error) {
					  alert(JSON.stringify(error));
				  }
			  });
		},
		getHistoryData:function(){
			var thisRef = this;
			var fromDate = $("#fromDate").val().split("/");//jqxDateTimeInput("getText");
			var toDate = $("#toDate").val().split("/");//jqxDateTimeInput("getText");
			//fromDate = fromDate.replace(/\//g,"-");
			var fromDateStr = fromDate[2]+"-"+fromDate[1]+"-"+fromDate[0];
			var toDateStr = toDate[2]+"-"+toDate[1]+"-"+toDate[0];
			var temperatureData2 = new Array();
			var moistureData = new Array();
			var lightData = new Array();
			var chartSeriesArea = new Object();
			var series = new Array();
			var xDataSeries = new Array();
			$.get( thisRef.plantsHealthService_uri+"?time1="+fromDateStr+"&time2="+toDateStr).done(function( data ) {
			
				$.each(data,function(index,node){
					if(!chartSeriesArea.hasOwnProperty(node.device)){
						chartSeriesArea[node.device]={}
						chartSeriesArea[node.device].dataField = node.device;
						chartSeriesArea[node.device].displayText = node.device;
						chartSeriesArea[node.device].opacity = 0.7;
						series.push(chartSeriesArea[node.device]);
					}
					xDataSeries.push({"Time":node.id}); 
					var temp1 = new Object();
					temp1[node.device] = node.temperature;
					temperatureData2.push(temp1)
					
					var temp2 = new Object();
					temp2[node.device] = node.moisture;
					moistureData.push(temp2)
					
					var temp3 = new Object();
					temp3[node.device] = node.light;
					lightData.push(temp3)
					
					
					
				});
				
				//alert(JSON.stringify(temperatureData2));
				try{
					thisRef.plotTemperatureChart(xDataSeries,series,temperatureData2);
					thisRef.plotMoistureChart(xDataSeries,series,moistureData);
					thisRef.plotLightChart(xDataSeries,series,lightData);
				}catch(error){
					alert(error);
				}
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},
		
		plotTemperatureChart:function(xAxis,chartSeries,data){
			var thisRef = this;
			var temperatureChartSettings = $('#temperatureChart').jqxChart("getInstance");
			if(temperatureChartSettings!=undefined &&( temperatureChartSettings.seriesGroups!=null ||
					temperatureChartSettings.seriesGroups!=undefined)){
				temperatureChartSettings.source = xAxis;
				temperatureChartSettings.seriesGroups[0].source = data;
				temperatureChartSettings.seriesGroups[0].series = chartSeries;
				$('#temperatureChart').jqxChart("getInstance").update();
				return;
			}
			
			
			
			var temperatureChartSettings = this.commonChartSettings;
			temperatureChartSettings.description= "Temperature Variation ",
			temperatureChartSettings.valueAxis={
                 visible: true,
                 title: { text: 'Temperature of plants' }
             }
			
			temperatureChartSettings.source = xAxis;
			temperatureChartSettings.seriesGroups=
				[{
                    type: 'area',
                    source: data,
                    series: chartSeries
				 }];
			
			if($('#temperatureChart').jqxChart("getInstance")!=null)
				$('#temperatureChart').jqxChart("getInstance").update();
			else
				$('#temperatureChart').jqxChart(temperatureChartSettings);
		},
		plotMoistureChart:function(xAxis,chartSeries,data){
			var thisRef = this;
			var temperatureChartSettings = $('#moistureChart').jqxChart("getInstance");
			
			if(temperatureChartSettings!=undefined &&(temperatureChartSettings.seriesGroups!=null ||
					temperatureChartSettings.seriesGroups!=undefined)){
				temperatureChartSettings.source = xAxis;
				temperatureChartSettings.seriesGroups[0].source = data;
				temperatureChartSettings.seriesGroups[0].series = chartSeries;
				$('#moistureChart').jqxChart("getInstance").update();
				return;
			}
			var temperatureChartSettings = this.commonChartSettings;
			temperatureChartSettings.description= "Moisture Variation ",
			temperatureChartSettings.valueAxis={
                 visible: true,
                 title: { text: 'Moisture of plants' }
             }
			
			temperatureChartSettings.source = xAxis;
			temperatureChartSettings.seriesGroups=
				[{
                    type: 'area',
                    source: data,
                    series: chartSeries
				 }];
			
			if($('#moistureChart').jqxChart("getInstance")!=null)
				$('#moistureChart').jqxChart("getInstance").update();
			else
				$('#moistureChart').jqxChart(temperatureChartSettings);
		},
		plotLightChart:function(xAxis,chartSeries,data){
			var thisRef = this;
			var temperatureChartSettings =  $('#lightChart').jqxChart("getInstance");
			
			if(temperatureChartSettings!=undefined &&(temperatureChartSettings.seriesGroups!=null ||
					temperatureChartSettings.seriesGroups!=undefined)){
				temperatureChartSettings.source = xAxis;
				temperatureChartSettings.seriesGroups[0].source = data;
				temperatureChartSettings.seriesGroups[0].series = chartSeries;
				$('#lightChart').jqxChart("getInstance").update();
				return;
			}
			
			var temperatureChartSettings = this.commonChartSettings;
			temperatureChartSettings.description= "Light Variation ",
			temperatureChartSettings.valueAxis={
                 visible: true,
                 title: { text: 'Light Readings' }
             }
			
			temperatureChartSettings.source = xAxis;
			temperatureChartSettings.seriesGroups=
				[{
                    type: 'area',
                    source: data,
                    series: chartSeries
				 }];
			
			if($('#lightChart').jqxChart("getInstance")!=null)
				$('#lightChart').jqxChart("getInstance").update();
			else
				$('#lightChart').jqxChart(temperatureChartSettings);
		}
		
	}
})();