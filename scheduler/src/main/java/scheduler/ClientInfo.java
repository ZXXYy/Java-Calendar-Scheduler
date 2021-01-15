package scheduler;

import java.io.Serializable;

public class ClientInfo implements Serializable{
    private String sign;
    private String name;
    private String password;
    //重写构造函数
    public ClientInfo(String sign,String name,String password){
        this.sign=sign;
        this.name=name;
        this.password=password;
    }
    
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password_OR_path) {
        this.password = password_OR_path;
    }
    @Override
    public String toString() {
        return "Client Info: [sign=" + sign + ", name=" + name + ", password=" +password + "]";
    }

}