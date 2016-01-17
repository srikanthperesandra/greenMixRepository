/**
 * 
 * @author srikanth
 */
$.getScript("jqwidgets/jqxcore.js");
$.getScript("jqwidgets/jqxdata.js");

$.getScript("jqwidgets/jqxbuttons.js");
$.getScript("jqwidgets/jqxscrollbar.js");
$.getScript("jqwidgets/jqxpanel.js")
$.getScript("jqwidgets/jqxtree.js");
var ManageAlertsWidget = (function(){
	
	return{
		
		widgetDom:null,
		alertsUri:"services/alerts",
		init:function(id){
			this.widgetDom = $("#"+id);	
			var thisRef = this;
			$.ajax({ url: 'templates/ManageAlertsWidget.html', dataType: 'html', 
				success: function(response) { 
							try{
								//alert("called");
								thisRef.widgetDom.html(response);
								//alert(response);
								//thisRef.instantiateWidgetComponents();
								$("#refreshButton").jqxButton({width:'100',theme:appConfig.theme});
								thisRef.getRecentAlerts();
								$("#refreshButton").on('click',function(){
									
									thisRef.getRecentAlerts();
								
								});
							}catch(e){
								alert(e);
							}
							
							//thisRef.preparePlantsGrid();
						}
						
			});
		},
		getRecentAlerts:function(){
			//alert("called");
			var thisRef = this;
			$.get(this.alertsUri).done(function(data){
				
				var treeSource = thisRef.buildTreeSource(data);
				thisRef.buildTree(treeSource);
			}).fail(function(data){
				alert(JSON.stringify(data));
			});
		},
		
		buildTreeSource:function(data){
			var source = new Array();
			$.each(data,function(index,node){
				var temp = new Object();
				temp.label = node.deviceIdTime;
				temp.items =[];
				
				temp.items.push(node.message);
				//temp.expanded = true;
				source.push(temp);
			});
			return source;
			
		},
		buildTree:function(source){
			
			
			 $('#alertsTreeGrid').jqxTree({ source: source, height: '500px', width:'100%', theme:appConfig.theme});
			 
		}
	};
	
})();
