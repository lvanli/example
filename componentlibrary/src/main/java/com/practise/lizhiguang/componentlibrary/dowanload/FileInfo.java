package com.practise.lizhiguang.componentlibrary.dowanload;

import java.io.Serializable;

/**
 * Created by lizhiguang on 16/7/22.
 */
public class FileInfo implements Serializable {
    String mName;
    int mId;
    int length;
    int finish;
    String url;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
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
                "mName='" + mName + '\'' +
                ", mId=" + mId +
                ", length=" + length +
                ", finish=" + finish +
                ", url='" + url + '\'' +
                '}';
    }

}
