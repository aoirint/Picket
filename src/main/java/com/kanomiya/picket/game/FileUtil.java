package com.kanomiya.picket.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtil
{

    public static boolean exists(File file)
    {
        return file.exists();
    }

    public static String name(Path path)
    {
        return path.getFileName().toString();
    }

    public static String splitExtension(String fileName)
    {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    public static File file(Path path)
    {
        return path.toFile();
    }

    public static File file(String parent, String child)
    {
        return new File(parent, child);
    }

    public static File file(File parent, String child)
    {
        return new File(parent, child);
    }

    public static Reader reader(File file) throws FileNotFoundException
    {
       return new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
    }

    public static Path path(File file)
    {
        return file.toPath();
    }

    public static Stream<Path> walk(Path path) throws IOException
    {
        return Files.walk(path);
    }

    public static Stream<Path> walk(Path path, int maxDepth) throws IOException
    {
        return Files.walk(path, maxDepth);
    }

}
