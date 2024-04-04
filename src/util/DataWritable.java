package util;

public interface DataWritable {

    <T> void writeDataToFile(Object[] data, String fileName, Class<T> clazz);

}
