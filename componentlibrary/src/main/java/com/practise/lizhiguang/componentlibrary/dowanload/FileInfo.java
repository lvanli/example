package com.practise.lizhiguang.componentlibrary.dowanload;

import java.io.Serializable;

/**
 * Created by lizhiguang on 16/7/22.
 */
public class FileInfo implements Serializable {
    private String name;
    private int id;
    private int length;
    private int finish;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", length=" + length +
                ", finish=" + finish +
                ", url='" + url + '\'' +
                '}';
    }

}
