package util;

import java.util.List;

public interface DataReadable<T> {

    List<T> readDataFromFile(String fileName, Class<T[]> clazz);

}
