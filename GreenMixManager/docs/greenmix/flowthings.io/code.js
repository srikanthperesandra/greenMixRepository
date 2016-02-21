/**
 * 
 */
//Rule­Based Analysis 
 
var deviceId = drop.elems.deviceId.value; 
var responseData = 
Flow.Http.get("http://greenmix6.mybluemix.net/specs?deviceId="+deviceId,{}); 
//var responseData = request; 
var spec = JSON.parse(responseData.content); 
var temperature = drop.elems.temperature.value; 
var light = drop.elems.volts.value; 
var moisture = drop.elems.moisture.value; 
var date = new Date(); 
//var currentDate = date.getHours() + ":" + date.getMinute() + ":" + date.getSeconds() + "," + 
date.getDate() + "/" + date.getMonth() + "/" + date.getFullYear(); 
var result = { 
"messages":[], 
  "deviceId": drop.elems.deviceId.value, 
  "timeStamp": date 
}; 
if(temperature < spec.MIN_TEMP || temperature >= spec.MAX_TEMP){ 
  result.messages.push("Temperature:" + temperature + " is out of range! (" + spec.MIN_TEMP 
+"­"+spec.MAX_TEMP + ") Celcius"); 
} 
if(moisture < spec.MIN_MOISTURE || moisture >= spec.MAX_MOISTURE){ 
  result.messages.push("Moisture:"+moisture+" is out of range! " + spec.MIN_MOISTURE + "­" + 
spec.MAX_MOISTURE); 
} 
if(light < spec.MIN_LUMS || light >= spec.MAX_LUMS){ 
  result.messages.push("Light:"+light+" is out of range! " + spec.MIN_LUMS + "­" + 
spec.MAX_LUMS); 
} 
if(result.messages.length > 0) 
Flow.Http.post("http://greenmix6.mybluemix.net/FlowThingsAlert", 
{"content­type":"application/json"}, JSON.stringify(result)); 
return {"elems":{"analysedResult":JSON.stringify(result)}} 
 
 
 
 
 
 
 
 
 //Streaming­based Analysis 
 
Pvalues = {60:[0.15,0.20,0.26,0.32,0.38,0.41,0.40,0.34,0.28,0.22,0.17,0.13], 
55:[0.17,0.21,0.26,0.32,0.36,0.39,0.38,0.33,0.28,0.23,0.18,0.16], 
50:[0.19,0.23,0.27,0.31,0.34,0.36,0.35,0.32,0.28,0.24,0.20,0.18], 
45:[0.20,0.23,0.27,0.30,0.34,0.35,0.34,0.32,0.28,0.24,0.21,0.20], 
40:[0.22,0.24,0.27,0.30,0.32,0.34,0.33,0.31,0.28,0.25,0.22,0.21], 
35:[0.23,0.25,0.27,0.29,0.31,0.32,0.32,0.30,0.28,0.25,0.23,0.22], 
30:[0.24,0.25,0.27,0.29,0.31,0.32,0.31,0.30,0.28,0.26,0.24,0.23], 
25:[0.24,0.26,0.27,0.29,0.30,0.31,0.31,0.29,0.28,0.26,0.25,0.24], 
20:[0.25,0.26,0.27,0.28,0.29,0.30,0.30,0.29,0.28,0.26,0.25,0.25], 
15:[0.26,0.26,0.27,0.28,0.29,0.29,0.29,0.28,0.28,0.27,0.26,0.25], 
10:[0.26,0.27,0.27,0.28,0.28,0.29,0.29,0.28,0.28,0.27,0.26,0.26], 
5:[0.27,0.27,0.27,0.28,0.28,0.28,0.28,0.28,0.28,0.27,0.27,0.27], 
0:[0.27,0.27,0.27,0.27,0.27,0.27,0.27,0.27,0.27,0.27,0.27,0.27]} 
 
var temperature = parseInt(drop.elems.temperature.value); 
while(temperature<=60){ 
    if(temperature%5===0) 
        break; 
    temperature++; 
} 
var p = Pvalues[temperature]; 
var pFactor= p[new Date().getMonth()]; 
var eto = pFactor*((0.46*drop.elems.temperature.value)+8) 
var water = (drop.elems.moisture.value*eto) + "mm/day"; 
var result = { 
  "elems":{ 
    "water": water, 
    "temperature": drop.elems.temperature.value, 
    "moisture": drop.elems.moisture.value 
  } 
} 
 
 
return result; 


//Rule­Based Analysis 

var deviceId = drop.elems.deviceId.value; 
var responseData = 
Flow.Http.get("http://greenmix6.mybluemix.net/specs?deviceId="+deviceId,{}); 
//var responseData = request; 
var spec = JSON.parse(responseData.content); 
var temperature = drop.elems.temperature.value; 
var light = drop.elems.volts.value; 
var moisture = drop.elems.moisture.value; 
var date = new Date(); 
//var currentDate = date.getHours() + ":" + date.getMinute() + ":" + date.getSeconds() + "," + 
date.getDate() + "/" + date.getMonth() + "/" + date.getFullYear(); 
var result = { 
"messages":[], 
  "deviceId": drop.elems.deviceId.value, 
  "timeStamp": date 
}; 
if(temperature < spec.MIN_TEMP || temperature >= spec.MAX_TEMP){ 
  result.messages.push("Temperature:" + temperature + " is out of range! (" + spec.MIN_TEMP 
+"­"+spec.MAX_TEMP + ") Celcius"); 
} 
if(moisture < spec.MIN_MOISTURE || moisture >= spec.MAX_MOISTURE){ 
  result.messages.push("Moisture:"+moisture+" is out of range! " + spec.MIN_MOISTURE + "­" + 
spec.MAX_MOISTURE); 
} 
if(light < spec.MIN_LUMS || light >= spec.MAX_LUMS){ 
  result.messages.push("Light:"+light+" is out of range! " + spec.MIN_LUMS + "­" + 
spec.MAX_LUMS); 
} 
if(result.messages.length > 0) 
Flow.Http.post("http://greenmix6.mybluemix.net/FlowThingsAlert", 
{"content­type":"application/json"}, JSON.stringify(result)); 
return {"elems":{"analysedResult":JSON.stringify(result)}} 