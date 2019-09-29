package hellojava;

import com.alibaba.fastjson.*;
import java.util.*;


public class Main{
    public static void main(String[] args) {

        Message msg = new Message();
        msg.setUuid("uuid");
        msg.setMetric("metric");
        msg.setTags(new ArrayList());
        msg.setErrMsg("0");
        msg.setAlarmId("alarmId");
        msg.setResults(new ArrayList());

        String msg_str = JSON.toJSONString(msg);
        // Message m = JSON.parseObject(msg_str, Message.class);
        System.out.println(msg_str);
    }
}

class Message {
    public Message(){}  

    private String alarmId;
    private String uuid;
    private String metric;
    private ArrayList tags;
    private ArrayList results;
    private String errMsg;

    public String getAlarmId() {
        return alarmId;  
    }  
    public void setAlarmId(String alarmId) {  
        this.alarmId = alarmId;  
    }  
    public String getUuid() {
        return uuid;  
    }  
    public void setUuid(String uuid) {  
        this.uuid = uuid;  
    }  
    public String getMetric() {
        return metric;  
    }  
    public void setMetric(String metric) {  
        this.metric = metric;  
    }  
    public ArrayList getTags() {  
        return tags;  
    }  
    public void setTags(ArrayList tags) {  
        this.tags = tags;  
    }      
    public ArrayList getResults() {  
        return results;  
    }  
    public void setResults(ArrayList results) {  
        this.results = results;  
    }  
    public String getErrMsg() {
        return errMsg;  
    }  
    public void setErrMsg(String errMsg) {  
        this.errMsg = errMsg;
    }  
}  



