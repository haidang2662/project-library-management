package util;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class FileUtil implements DataWritable, DataReadable {

    private static final Gson gson = new Gson();

    @Override
    public <T> void writeDataToFile(Object[] data, String fileName, Class<T> clazz) {
        if (StringUtil.isNullOrEmpty(fileName) || DataUtil.isNullOrEmpty(data)) {
            return;
        }
        try (FileReader reader = new FileReader(fileName)) {
            gson.fromJson(reader, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object readDataFromFile(String fileName) {
        if (StringUtil.isNullOrEmpty(fileName)) {
            return null;
        }
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
//            return objectInputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}