package main.webview.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class WebViewResponse {

    public static String TAG = WebViewResponse.class.getName();

    private String action;

    private int code;

    private String desc = "";

    private JSONObject data = null;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setData(String result) {
        JSONObject data = null;
        try{
            data = new JSONObject(result);
        }catch(JSONException e) {
        }
        JSONObject json = new JSONObject();
        try {
            if(data != null) {
                json.put("result", data);
            } else {
                json.put("result", result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.data = json;
    }

    public void setData(Map data) {
        JSONObject json = new JSONObject();
        try {
            json.put("result", new JSONObject(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.data = json;
    }

    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("action", this.action);
            json.put("code", this.code);
            if(this.data != null) {
                json.put("data", this.data.get("result"));
            }
            json.put("desc", this.desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
