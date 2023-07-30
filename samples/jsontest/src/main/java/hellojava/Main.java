package hellojava;

import com.alibaba.fastjson.*;
import java.util.*;


public class Main{
    public static void main(String[] args) throws Exception {                
        // get args        
        if (args.length != 1) {            
            System.out.println("error => need json str as 1 arg !");
            System.out.println(args);
			return;
        }        

		/* parse json:
		{
		  "alarmId": "xxxxxx",  
		  "status": 0,  
		  "uuid": "xxxxx", 
		  "metrics": [
		      {
		          "metric" : "disk",
		          "tags": [
		              {"tagName" : "key", "tagValue" : "sda1"},
		              {"tagName" : "pid", "tagValue" : "1" }
		          ] 
		      },
		  ],     
		  "aggFunc": ["mean", "max"],  
		  "window": 5  
		}
		*/
        System.out.println("start\n");
        // decode to json object from base64 str
        JSONObject object = JSONObject.parseObject(new String(Base64.getDecoder().decode(args[0]), "utf-8"));

        int time = object.getIntValue("window");
        String alarmId = object.getString("alarmId");
        String uuid = object.getString("uuid");
        String status = object.getString("status");

		ArrayList<String> aggFuncs = new ArrayList<String>(); 	
        List l = JSON.parseArray(object.getJSONArray("aggFunc").toJSONString());
	    for (Object s : l) {  
	        aggFuncs.add(JSONObject.toJSONString(s));
	    }

		ArrayList<HashSet> metrics = new ArrayList<HashSet>();		
        JSONArray metricsArr = object.getJSONArray("metrics");	  	
	    for (int i = 0; i < metricsArr.size(); i++) {
	    	HashSet<String> set = new HashSet<String>();
	        JSONObject obj = metricsArr.getJSONObject(i);
	        set.add("metric=" + obj.getString("metric"));

			ArrayList tags = new ArrayList();
	        JSONArray tagsArr = obj.getJSONArray("tags");	  	
		    for (int j = 0; j < tagsArr.size(); j++) {	        
		        JSONObject objk = tagsArr.getJSONObject(j);
				set.add(objk.getString("tagName") + "=" + objk.getString("tagValue"));
		    }
		    metrics.add(set);
	    }

        System.out.println(time);
        System.out.println(alarmId);
        System.out.println(uuid);
        System.out.println(status);
        System.out.println(aggFuncs);
        System.out.println(metrics);

		System.out.println("\nend");


		// java set operator:
		HashSet<String> s1 = new HashSet<String>();
		HashSet<String> s2 = new HashSet<String>();
		s1.add("a");s1.add("b");s2.add("a");		
		HashSet<String> s3 = new HashSet<String>();
		for (String s: s2) {s3.add(s);}
		s3.removeAll(s1);
		System.out.println(s3.isEmpty());		
		System.out.println(s2.isEmpty());		
		System.out.println(s3);		
		System.out.println(s2);		
    }
}

/*
example2:
public void flatMap(String sentence, Collector<Word> out) throws Exception {    

            // parse json: {"value": [[1,2,3,4,5], [11,22,33,44,55]] }

            JSONObject object = JSONObject.parseObject(sentence);
            List l = JSON.parseArray(object.getJSONArray("value").toJSONString());
            for (Object s : l) {                
                String jsonStr = JSONObject.toJSONString(s);
                List<Integer> list = JSONObject.parseArray(jsonStr,  Integer.class);
                // System.out.println(list);
                for(int i = 0; i < list.size(); i++) {
                    Word w = new Word( map.get(i), list.get(i) );
                    out.collect( w );
                }                            
            }
}
*/



