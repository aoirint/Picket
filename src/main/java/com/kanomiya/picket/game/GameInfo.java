package com.kanomiya.picket.game;

import java.util.Map;

import com.kanomiya.picket.util.IDataSerializer;

public class GameInfo
{
    public final String
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


    @Override
    public String toString()
    {
        return "GameInfo [name=" + name + ", description=" + description + ", version=" + version + ", author=" + author
                + ", url=" + url + "]";
    }


    public static class DataSerializerGameInfo implements IDataSerializer<GameInfo>
    {
        public DataSerializerGameInfo()
        {

        }

        @Override
        public Map<String, Object> serialize(GameInfo info)
        {

            return null;
        }

        @Override
        public GameInfo deserialize(Map<String, Object> map)
        {
            String name = (String) map.getOrDefault("name", "Unnamed");
            String description = (String) map.getOrDefault("description", "");
            String version = (String) map.getOrDefault("version", "");
            String author = (String) map.getOrDefault("author", "");
            String url = (String) map.getOrDefault("url", "");


            return new GameInfo(name, description, version, author, url);
        }

    }
}
