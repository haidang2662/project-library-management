package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil<T> implements DataWritable<T>, DataReadable<T> {

    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    public void writeDataToFile(List<T> data, String fileName) {
        if (StringUtil.isNullOrEmpty(fileName) || DataUtil.isNullOrEmpty(data)) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String rs = gson.toJson(data);
            fileWriter.write(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> readDataFromFile(String fileName, Class<T[]> clazz) {
        if (StringUtil.isNullOrEmpty(fileName)) {
            return null;
        }
        try (FileReader reader = new FileReader(fileName)) {
            T[] dataArr = gson.fromJson(reader, clazz);
            return dataArr == null ? null : new ArrayList<>(Arrays.asList(dataArr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}