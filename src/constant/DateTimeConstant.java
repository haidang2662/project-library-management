package constant;

import java.time.format.DateTimeFormatter;

public class DateTimeConstant {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

}
