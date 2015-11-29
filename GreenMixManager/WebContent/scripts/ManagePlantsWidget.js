/**
 * @author srikanth
 */

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
var PlantsGrid = (function(){
	
	return {
		
	
		plantsGrid: null,
		templateStr: null,
		plantsService_uri: "services/plants",
		plantsGridDiv:null,
		addPlantButton:null,
		deletePlantButton:null,
		addPlantDialog:null,
		plantSpecs:new Array(),
		jsonSpecs:null,
		init:function(id){
			var thisRef = this;
			this.plantsGridDiv = $("#"+id);
			//alert(this.plantsGridDiv);
			
			$.ajax({ url: 'templates/ManagePlantsWidget.html', dataType: 'html', 
				success: function(response) { 
							
							thisRef.plantsGridDiv.html(response);
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
			$.get(thisRef.plantsService_uri+"/specs").done(function( data ) {
				//alert(JSON.stringify(data));
				thisRef.jsonSpecs =data;
				for(var i=0;i<data.length;i++)
					thisRef.plantSpecs.push(data[i].name);
				thisRef.invokePlantsService();
				thisRef.prepareWidgets();
			}).fail(function(error){
				alert(error);
			});
		},
		prepareWidgets:function(){
			var thisRef = this;
			
			$("#addPlant").jqxButton({width:'100',theme:appConfig.theme});
			//this.deletePlantButton=$("#deletePlant").jqxButton({width:150});
			$('#addPlantWindow').jqxWindow({
                //showCollapseButton: true, 
                
                height:'250',
                width:'300',
				
                theme:appConfig.theme,
                initContent:function(){
                	thisRef.prepareAddPlantDialog();
                }

                });
			$("#addPlant").on('click',function(){
				//$("#addPlantWindow").jqxWindow('resizable',true);
				
	                
				 $('#addPlantWindow').jqxWindow('open');
				 $('#addPlantWindow').jqxWindow('focus');
			});
			
			
		},
		
		prepareAddPlantDialog:function(){
			/*
			 * 
			 * private String plantName;
	private String plantDevice;
	private String location;
	private int plantSpecId;
			 */
			//alert(this.plantSpecs);
			var thisRef = this;
			
			$("#plantName").jqxInput({placeHolder: " Enter Plant Name", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#plantDevice").jqxInput({placeHolder: " Enter Plant Device Identifier", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#location").jqxInput({placeHolder: " Provide Plant location", height: 25, width: 200, minLength: 1,theme:appConfig.theme});
			$("#plantTemplate").jqxInput({placeHolder: " Choose Plant Specification", height: 25, width: 200, minLength: 1,theme:appConfig.theme,source:this.plantSpecs});
			$("#submit").jqxButton({width:'100',theme:appConfig.theme});
			$("#submit").on('click',function(){
				
				var specId =null;
				
				
				$.each(thisRef.jsonSpecs,function(index,node){
					var  template= $("#plantTemplate").val();
					
					if(node.name == template){
						specId=node.id;
					
						//return false;
					}
				})
					var plantDetails={
							plantName:$("#plantName").val(),
							plantDevice:$("#plantDevice").val(),
							location:$("#location").val(),
							plantSpecId:specId
						
				};
				//alert(JSON.stringify(plantDetails));
				
				$.ajax( {
					url:thisRef.plantsService_uri,
					method:"POST",
					contentType:"application/x-www-form-urlencoded",
					data:"data="+JSON.stringify(plantDetails),
					success:function(response){
						alert(JSON.stringify(response));
						thisRef.invokePlantsService();
					},
					error:function(data){
						alert("error:"+JSON.stringify(data));
					}
				});
				
				
				$('#addPlantWindow').jqxWindow('close');
			});
		},
		invokePlantsService:function(){
			var thisRef = this;
			$.get( thisRef.plantsService_uri).done(function( data ) {
				//alert(data);
					
					thisRef.preparePlantsGrid(data);
					
				}).fail(function(error){
					alert(error);
				});
		},
		preparePlantsGrid:function(data){
			
			//alert(JSON.stringify(data));
			var thisRef = this;
			 var source =
	            {
	                datatype: "array",
	                datafields: [
	                    { name: 'id'},         
	                    { name: 'plantName'},
	                    { name: 'location'},
	                    { name: 'plantDeviceToken'},
	                    { name: 'plantDevice' },
	                    { name: 'plantTemplate'},
	                    { name: 'userId'}
	                    
	                ],
	                localdata:data//[{"id":1,"plantName":"testPlant","location":"Stuttgart","plantDeviceToken":"testToken","userId":0,"plantDevice":"testDevice","plantTemplate":1}]//data
	            };
			
			
			
			var dataAdapter = new $.jqx.dataAdapter(source);
			
			
			/*if(this.plantsGrid!=null){
				this.plantsGrid.attr("source",dataAdapter);
				return;
			}*/
		
			
	         
			
				$("#plantsGrid").jqxGrid({
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
		                	{ text: 'Plant Name',  datafield: 'plantName' },
		                	{ text: 'Location',  datafield: 'location' },
		                	{ text: 'Plant Device Token',  datafield: 'plantDeviceToken' },
		                	{ text: 'Plant Device',  datafield: 'plantDevice' },
		                	{ text: 'Plant Template',  datafield: 'plantTemplate' },
		                	{ text: 'Action',columntype:'button',cellsrenderer:function(){
		                		
		                		return "delete";
		                		},buttonclick:function(row){
		                			var rowData =$("#plantsGrid").jqxGrid('getRowData',row);
		                			//alert(rowData.id);
		                			$.ajax({
		                				   url: thisRef.plantsService_uri+"/"+rowData.id,
		                				   type: 'DELETE',
		                				  
		                				}).done(function(data){
		                					alert(data);
		                					thisRef.invokePlantsService();
		                				}).fail(function(error){
		                					alert("error:"+error);
		                				});
		                		}
		                	}
		                ]
		             
		                
		            });
		
				$('#plantsGrid').jqxGrid('autoresizecolumns'); 
		}
		
		
	};
	
})();