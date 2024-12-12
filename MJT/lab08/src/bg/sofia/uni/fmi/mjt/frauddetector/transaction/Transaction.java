package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(
    String transactionID,
    String accountID,
    double transactionAmount,
    LocalDateTime transactionDate,
    String location,
    Channel channel
) {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String TRANSACTION_PARAMETER_SEPARATOR = ",";
    private static final int CLASS_FIELD_COUNT = 6;
    private static final int TRANSACTION_DATE_FIELD_INDEX = 3;
    private static final int TRANSACTION_LOCATION_FIELD_INDEX = 4;
    private static final int TRANSACTION_CHANNEL_FIELD_INDEX = 5;

    public static Transaction of(String line) {
        final String[] parameters = line.split(TRANSACTION_PARAMETER_SEPARATOR);

        if (parameters.length != CLASS_FIELD_COUNT) {
            throw new IllegalArgumentException(
                "Invalid field count:" + parameters.length + ", should be " + CLASS_FIELD_COUNT
            );
        }

        return new Transaction(
            parameters[0],
            parameters[1],
            Double.parseDouble(parameters[2]),
            LocalDateTime.parse(parameters[TRANSACTION_DATE_FIELD_INDEX], DATE_TIME_FORMAT),
            parameters[TRANSACTION_LOCATION_FIELD_INDEX],
            Channel.valueOf(parameters[TRANSACTION_CHANNEL_FIELD_INDEX].toUpperCase())
        );
    }
}