/**
 * @author srikanth
 */
alert("reached here");
$.getScript("jqwidgets/jqxcore.js");
$.getScript("jqwidgets/jqxdata.js");
$.getScript("jqwidgets/jqxgrid.js");
$.getScript("jqwidgets/jqxgrid.sort.js"); 
$.getScript("jqwidgets/jqxgrid.pager.js"); 
$.getScript("jqwidgets/jqxgrid.selection.js"); 
$.getScript("jqwidgets/jqxgrid.edit.js");

var PlantsGrid = (function(){
	
	return {
		plantsGrid: null,
		templateStr: null,
		plantsService_uri: "services/plants",
		init:function(id){
			var thisRef = this;
			this.plantsGridDiv = $("#"+id);
			alert(this.plantsGridDiv);
			$.ajax({ url: 'templates/ManagePlantsWidget.html', dataType: 'html', 
				success: function(response) { 
							alert(response);
							thisRef.plantsGridDiv.html(response);
							thisRef.invokeService();
							//thisRef.preparePlantsGrid();
						}
						
			});
			
		},
		
		invokeService:function(){
			var thisRef = this;
			$.get( thisRef.plantsService_uri).done(function( data ) {
					thisRef.preparePlantsGrid(data);
				});
		},
		preparePlantsGrid:function(data){
			
			var dataAdapter = new $.jqx.dataAdapter(source /*{
                downloadComplete: function (data, status, xhr) { },
                loadComplete: function (data) { },
                loadError: function (xhr, status, error) { }
            }*/);
			//alert($("#tabs"));
			/*
			 * 
			 * [{"id":1,"plantName":"testPlant","location":"Stuttgart","plantDeviceToken":"testToken","userId":0,"plantDevice":"testDevice","plantTemplate":1}]
			 */
			
			if(this.plantsGrid!=null){
				this.plantsGrid.attr("source",dataAdapter);
				return;
			}
			
			this.plantsGrid=$("#plantsGrid").jqxGrid(
		            {
		                autowidth: 200,
		                source: dataAdapter,                
		                //pageable: true,
		                autoheight: true,
		                theme:"ui-le-frog",
		                sortable: true,
		                //altrows: true,
		                //enabletooltips: true,
		                //editable: true,
		                //selectionmode: 'multiplecellsadvanced',
		                columns: [
		                  { text: 'Plant Name',  datafield: 'plantName', width: 25% },
		                  { text: 'Location', datafield: 'location', cellsalign: 'right', align: 'right', width: 25% },
		                  { text: 'Plant Device Token',  datafield: 'plantDeviceToken', align: 'right', cellsalign: 'right', width: 25% },
		                  { text: 'Plant Device', datafield: 'plantDevice', cellsalign: 'right', width: 25% },
		                  { text: 'Plant Template', datafield: 'plantTemplate', cellsalign: 'right',  width: 25% },
		                ],
		                
		            });
		}
		
		
	};
	
})();