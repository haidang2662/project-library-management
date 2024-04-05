package util;

import java.util.List;

public interface DataWritable<T> {

    void writeDataToFile(List<T> data, String fileName);

}
