import json
import base64

x = json.dumps({
  "alarmId": "xx",  
  "status": 0,  
  "uuid": "fk", 
  "metrics": [
      {
          "metric" : "disk",
          "tags": [
              {"tagName" : "key", "tagValue" : "sda1"},
              {"tagName" : "pid", "tagValue" : "1" }
          ] 
      },
      {
          "metric" : "cpu",
          "tags": [
              {"tagName" : "key", "tagValue" : "ok"},
              {"tagName" : "pid", "tagValue" : "2" }
          ] 
      },
  ],     
  "aggFunc": ["mean", "max"],  
  "window": 5  
})
x = base64.b64encode(x)
print(x)

