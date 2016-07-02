package com.kanomiya.picket.game;

public class GameInfo
{
    private final String
        name,
        description,
        version,
        author,
        url;

    public GameInfo(String name, String description, String version, String author, String url)
    {
        this.name = name;
        this.description = description;
        this.version = version;
        this.author = author;
        this.url = url;
    }


    public String name()
    {
        return name;
    }

    public String description()
    {
        return description;
    }

    public String version()
    {
        return version;
    }

    public String author()
    {
        return author;
    }

    public String url()
    {
        return url;
    }


    @Override
    public String toString()
    {
        return "GameInfo [name=" + name + ", description=" + description + ", version=" + version + ", author=" + author
                + ", url=" + url + "]";
    }

}
