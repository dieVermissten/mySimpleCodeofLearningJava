import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;

public class RouteEntry {
    public String requestMethod;
    public String mainRoute;
    public String getParam;
    public String postParam;
    public String param;
    public HashMap<String,String> paraMap;

    @Override
    public String toString() {
        return "RouteEntry{" +
                "requestMethod='" + requestMethod + '\'' +
                ", mainRoute='" + mainRoute + '\'' +
                ", getParam='" + getParam + '\'' +
                ", postParam='" + postParam + '\'' +
                ", param='" + param + '\'' +
                ", paraMap=" + paraMap +
                '}';
    }
}

public class Route {
    private String request;
    public String requestMethod;
    public String mainRoute;
    public String getParam;
    public String postParam;
    public String param;
    public HashMap<String,String> paraMap;

    public RouteEntry getRouteEntry(){
        RouteEntry r = new RouteEntry();
        r.requestMethod= requestMethod;
        r.mainRoute= mainRoute;
        r.getParam= getParam;
        r.postParam= postParam;
        r.param= param;
        r.paraMap=paraMap;
        return r;

    }
    public Route(String request) {
        this.request = request;
        this.requestMethod = requestMethod();
        //System.out.println("1======"+Thread.currentThread().getName()+"===requestMethod==="+this.requestMethod);
        this.mainRoute = mainRoute();
        //System.out.println("2======"+Thread.currentThread().getName()+"===mainRoute==="+this.mainRoute);
        this.getParam = getParam();
        this.postParam = postParam();
        this.param=this.getParam.equals("")?this.postParam:this.getParam;
        //System.out.println("3======"+Thread.currentThread().getName()+"===param==="+this.param);
        if(!this.param.equals(""))this.paraMap=splitparam();

    }
    private String requestMethod() {
        String res = this.request.substring(0, this.request.indexOf(" "));
        return res;
    }
    private String mainRoute() {
        String res = this.request.split("\r")[0].split(" ")[1];
        if (res.indexOf('?') == -1) {
            if (res.equals("/")||res.equals("\\"))return "/index.html";
            return res;
        } else {
            return res.substring(0, res.indexOf('?'));
        }
    }
    private String getParam() {
        String res = "";
        if (requestMethod().equals("POST")) return "";
        String a = this.request.split("\r")[0].split(" ")[1];
        if (a.indexOf('?') == -1) return "";
        try {
            res = URLDecoder.decode(a.substring(a.indexOf('?') + 1), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (res.equals("")) return "";
        return res;
    }
    private String postParam() {
        if (requestMethod().equals("GET")) return "";
        String res = null;
        try {
            res = URLDecoder.decode(this.request.substring(this.request.indexOf("\r\n\r\n") + 4),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (res.equals("")) return "";
        return res;
    }
    private HashMap<String,String> splitparam(){
        paraMap=new HashMap<>();
        for (String s: param.split("&")) {
            String[] m = s.split("=");
            paraMap.put(m[0],m[1]);
        }
        return paraMap;

    }
}
