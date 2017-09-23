package cn.zyrkj.monsterbeta10.bean;

import java.util.ArrayList;

/**
 * 幻灯片
 */

public class Switchable extends ItemType{
    private ArrayList<MyImage> showImages;
    private ArrayList<MyImage> dotImages;
    private ArrayList<String> titles;

    public Switchable() {
        super("1");
    }

    public Switchable(ArrayList<MyImage> showImages, ArrayList<MyImage> dotImages, ArrayList<String> titles) {
        super("1");
        this.showImages = showImages;
        this.dotImages = dotImages;
        this.titles = titles;
    }

    public ArrayList<MyImage> getShowImages() {
        return showImages;
    }

    public void setShowImages(ArrayList<MyImage> showImages) {
        this.showImages = showImages;
    }

    public ArrayList<MyImage> getDotImages() {
        return dotImages;
    }

    public void setDotImages(ArrayList<MyImage> dotImages) {
        this.dotImages = dotImages;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }
}
