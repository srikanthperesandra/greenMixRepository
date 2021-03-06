/**
 * @author srikanth
 */  

$.getScript("jqwidgets/jqxcore.js");
$.getScript("jqwidgets/jqxtabs.js");
$.getScript("scripts/appConfig.js");
$.ajaxSetup({async:false});
$.getScript("scripts/ManagePlantsWidget.js") .done(function( script, textStatus ) {
//alert( "response"+textStatus );
})
.fail(function( jqxhr, settings, exception ) {
//alert("response error="+exception+jqxhr);
});
$.getScript("scripts/LiveDataWidget.js");
$.getScript("scripts/HistoryDataWidget.js");
$.getScript("scripts/ManageAlertsWidget.js");
$.getScript("scripts/ManagePlantsSpecWidget.js");
$.ajaxSetup({async:true});

/*
$.getScript("../jqwidgets/jqxcore.js")
$.getScript("../jqwidgets/jqxdata.js")
$.getScript("../jqwidgets/jqxgrid.js");
$.getScript("../jqwidgets/jqxgrid.sort..js");
$.getScript("../jqwidgets/jqxgrid.pager.js");
$.getScript("../jqwidgets/jqxgrid.selection.js");
$.getScript("../jqwidgets/jqxgrid.edit.js");
*/
var TabContainer = (function(){
	
	return {
		
		tabConatiner:null,
		templateStr:null,
		init:function(id){
			var thisRef = this;
			this.tabContainer = $("#"+id);
			$.ajax({ url: 'templates/BootstrapWidget.html', dataType: 'html', 
				success: function(response) { 
							thisRef.tabContainer.html(response);
							thisRef.prepareTabcontainer();
							$("#logout").on("click",function(){
								thisRef.logout(); 
							});
							try{
								
								PlantsGrid.init("managePlants");
								LiveDataWidget.init("liveData");
								HistoryDataWidget.init("history");
								ManageAlertsWidget.init("manageAlerts");
								ManagePlantsSpec.init("managePlantSpecs",PlantsGrid);
							}catch(error){
								alert(JSON.stringify(error));
							}
							
						}
			});
			
		},
		logout:function(){
			$.get("services/users/logout").done(function(data){
				location.reload();
			}).fail(function(data){
				alert(JSON.stringify(data));
			});
		},
		prepareTabcontainer:function(){
			//alert($("#tabs"));
			$("#tabs").jqxTabs({ animationType: 'fade',theme:appConfig.theme});
		}
		
	};

})();