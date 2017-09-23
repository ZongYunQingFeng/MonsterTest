package cn.zyrkj.monsterbeta10.okhttp;

//定义接口
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}