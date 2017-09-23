package cn.zyrkj.monsterbeta10.bean;

/**
 * 图片
 */

public class MyImage {
    private String source;
    private String url;

    public MyImage() {
        super();
    }

    public MyImage(String source, String url) {
        this.source = source;
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
