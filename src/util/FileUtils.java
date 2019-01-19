package util;

import java.io.File;
import java.nio.file.FileSystems;

public class FileUtils {

    public static String stockStorePath(){

        return storePath("stock_store.dat");

    }

    public static String offersStorePath(){

        return storePath("offers_store.dat");

    }

    public static String storePath(String fileName){

        String projectPath = System.getProperty("user.dir");
        String separator = FileSystems.getDefault().getSeparator();

        return projectPath + separator + fileName;

    }

    public static boolean fileExists(String filePath){

        File file = new File(filePath);
        return file.exists();

    }
}
