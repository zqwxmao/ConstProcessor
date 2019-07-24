package com.oeasy.libspoon;

public class ConstAnnotationBean<T> {
    private String key;
    private T val;
    private String comment;

    public ConstAnnotationBean() {
    }

    public ConstAnnotationBean(String key, T val) {
        this.key = key;
        this.val = val;
    }

    public ConstAnnotationBean(String key, T val, String comment) {
        this.key = key;
        this.val = val;
        this.comment = comment == null ? "" : comment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ConstAnnotationBean) {
            ConstAnnotationBean constAnnotationBean = (ConstAnnotationBean) o;
            return this.key.equals(constAnnotationBean.getKey());
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }
}
