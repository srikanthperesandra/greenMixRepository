$.getScript("jqwidgets/jqxcore.js");
$.getScript("jqwidgets/jqxdata.js");
$.getScript("jqwidgets/jqxbuttons.js")
$.getScript("jqwidgets/jqxscrollbar.js")
$.getScript("jqwidgets/jqxmenu.js")
$.getScript("jqwidgets/jqxcheckbox.js")
$.getScript("jqwidgets/jqxlistbox.js")
$.getScript("jqwidgets/jqxdropdownlist.js")
$.getScript("jqwidgets/jqxgrid.js");
$.getScript("jqwidgets/jqxgrid.sort.js"); 
$.getScript("jqwidgets/jqxgrid.pager.js"); 
$.getScript("jqwidgets/jqxgrid.selection.js"); 
$.getScript("jqwidgets/jqxgrid.edit.js");
$.getScript("jqwidgets/jqxbuttons.js");
$.getScript("jqwidgets/jqxinput.js");
$.getScript("jqwidgets/jqxwindow.js");
$.getScript("jqwidgets/jqxpanel.js");
var ManagePlantsSpec = (function(){
	
	return {
		
	
		plantsGrid: null,
		templateStr: null,
		plantsSpecService_uri: "services/plants/specs",
		plantsSpecGridDiv:null,
		addPlantSpecButton:null,
		deletePlantSpecButton:null,
		addPlantSpecDialog:null,
		plantSpecs:new Array(),
		jsonSpecs:null,
		init:function(id){
			var thisRef = this;
			this.plantsSpecGridDiv = $("#"+id);
			//alert(this.plantsGridDiv);
			
			$.ajax({ url: 'templates/ManagePlantSpecsWidget.html', dataType: 'html', 
				success: function(response) { 
							
							thisRef.plantsSpecGridDiv.html(response);
							try{
								//thisRef.prepareWidgets();
								thisRef.getPlantSpecs();
							}catch(e){
								alert(e);
							}
							
							//thisRef.preparePlantsGrid();
						}
						
			});
			
		},
		
		getPlantSpecs:function(){
			var thisRef = this;
			$.get(thisRef.plantsSpecService_uri).done(function( data ) {
				//alert(JSON.stringify(data));
				thisRef.jsonSpecs =data;
				for(var i=0;i<data.length;i++)
					thisRef.plantSpecs.push(data[i].name);
				thisRef.invokePlantsSpecService();
				thisRef.prepareWidgets();
			}).fail(function(error){
				alert(error);
			});
		},
		prepareWidgets:function(){
			var thisRef = this;
			
			$("#addPlantSpec").jqxButton({width:'100',theme:appConfig.theme});
			//this.deletePlantButton=$("#deletePlant").jqxButton({width:150});
			$('#addPlantSpecWindow').jqxWindow({
                //showCollapseButton: true, 
                
                height:'250',
                width:'300',
				
                theme:appConfig.theme,
                initContent:function(){
                	thisRef.prepareAddPlantSpecDialog();
                }

                });
			$("#addPlantSpec").on('click',function(){
				//$("#addPlantWindow").jqxWindow('resizable',true);
				
	                
				 $('#addPlantSpecWindow').jqxWindow('open');
				 $('#addPlantSpecWindow').jqxWindow('focus');
			});
			
			
		},
		
		prepareAddPlantSpecDialog:function(){
			/*
			 * 
			 * private String plantName;
	private String plantDevice;
	private String location;
	private int plantSpecId;
			 */
			//alert(this.plantSpecs);
			var thisRef = this;
			
			$("#plantSpecName").jqxInput({placeHolder: " Enter Plant Name", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#maxTemp").jqxInput({placeHolder: " Max Temperature in °C", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#minTemp").jqxInput({placeHolder: " Min Temperature in °C", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#maxMoisture").jqxInput({placeHolder: " Max Moisture between [0-1]", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#minMoisture").jqxInput({placeHolder: " Min Moisture between [0-1]", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#maxVolts").jqxInput({placeHolder: " Max Volts between [0-1]", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#minVolts").jqxInput({placeHolder: " Min Volts between [0-1]", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#submitPlantSpec").jqxButton({width:'100',theme:appConfig.theme});
			$("#submitPlantSpec").on('click',function(){
				
				var specId =null;
				
				
				
					var plantDetails={
							name:$("#plantSpecName").val(),
							maxTemp:$("#maxTemp").val(),
							minTemp:$("#minTemp").val(),
							maxMoisture:$("#maxMoisture").val(),
							minMoisture:$("#minMoisture").val(),
							maxVolts:$("#maxVolts").val(),
							minVolts:$("#minVolts").val(),
				};
				//alert(JSON.stringify(plantDetails));
				
				$.ajax( {
					url:thisRef.plantsSpecService_uri,
					method:"POST",
					contentType:"application/x-www-form-urlencoded",
					data:"data="+JSON.stringify(plantDetails),
					success:function(response){
						alert(JSON.stringify(response));
						thisRef.invokePlantsSpecService();
					},
					error:function(data){
						alert("error:"+JSON.stringify(data));
					}
				});
				
				$('#addPlantSpecWindow').jqxWindow('close');
			});
		},
		invokePlantsSpecService:function(){
			var thisRef = this;
			$.get( thisRef.plantsSpecService_uri).done(function( data ) {
				//alert(data);
					
					thisRef.preparePlantsSpecGrid(data);
					
				}).fail(function(error){
					alert(error);
				});
		},
		preparePlantsSpecGrid:function(data){
			
			//alert(JSON.stringify(data));
			var thisRef = this;
			 var source =
	            {
	                datatype: "array",
	                datafields: [
	                    { name: 'id'}, 
	                    { name: 'name'},
	                    { name: 'maxTemp'},
	                    { name: 'minTemp'},
	                    { name: 'maxMoisture'},
	                    { name: 'minMoisture' },
	                    { name: 'maxLums'},
	                    { name: 'minLums'}
	                    
	                ],
	                localdata:data//[{"id":1,"plantName":"testPlant","location":"Stuttgart","plantDeviceToken":"testToken","userId":0,"plantDevice":"testDevice","plantTemplate":1}]//data
	            };
			
			
			
			var dataAdapter = new $.jqx.dataAdapter(source);
			
			
			/*if(this.plantsGrid!=null){
				this.plantsGrid.attr("source",dataAdapter);
				return;
			}*/
		
			
	         
			
				$("#plantsSpecGrid").jqxGrid({
		                autowidth:true,
		                
		                source: dataAdapter,                
		                //pageable: true,
		                //height: 500,
		                autoheight:true,
		                theme:appConfig.theme,
		                sortable: true,
		                //altrows: true,
		                 enabletooltips: true,
		                //editable: true,
		                selectionmode: 'multiplerowsadvanced',
		                columns:[
		                    {text:"Id",datafield:'id'} ,    
		                	{ text: 'Specification',  datafield: 'name' },
		                	{ text: 'Max Temperature',  datafield: 'maxTemp' },
		                	{ text: 'Min Temperature',  datafield: 'minTemp' },
		                	{ text: 'Max Moisture',  datafield: 'maxMoisture' },
		                	{ text: 'Min Moisture',  datafield: 'minMoisture' },
		                	{ text: 'Max Volts',  datafield: 'maxLums' },
		                	{ text: 'Min Volts',  datafield: 'minLums' },
		                	{ text: 'Action',columntype:'button',cellsrenderer:function(){
		                		
		                		return "delete";
		                		},buttonclick:function(row){
		                			var rowData =$("#plantsSpecGrid").jqxGrid('getRowData',row);
		                			//alert(rowData.id);
		                			$.ajax({
		                				   url: thisRef.plantsSpecService_uri+"/"+rowData.id,
		                				   type: 'DELETE',
		                				  
		                				}).done(function(data){
		                					alert(data);
		                					thisRef.invokePlantsSpecService();
		                				}).fail(function(error){
		                					alert("error:"+error);
		                				});
		                		}
		                	}
		                ]
		             
		                
		            });
		
				$('#plantsSpecGrid').jqxGrid('autoresizecolumns'); 
		}
		
		
	};
	
})();