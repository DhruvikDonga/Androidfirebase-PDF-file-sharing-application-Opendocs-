package com.example.storage;


public class uploadpdf {
    public String name;
    public String url;

    public uploadpdf()
    {

    }
    public uploadpdf(String name,String url)
    {
        this.name=name;
        this.url=url;
    }
    public String getName()
    {
        return name;
    }
    public String getUrl()
    {
        return url;
    }

}