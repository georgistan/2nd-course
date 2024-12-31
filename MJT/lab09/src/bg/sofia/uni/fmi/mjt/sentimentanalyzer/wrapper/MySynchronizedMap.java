package bg.sofia.uni.fmi.mjt.sentimentanalyzer.wrapper;

import java.util.HashMap;
import java.util.Map;

public class MySynchronizedMap {
    private Map<String, String> data;

    public MySynchronizedMap() {
        data = new HashMap<>();
    }

    public synchronized void put(String key, String value) {
        data.put(key, value);
    }

    public synchronized void remove(String str) {
        data.remove(str);
    }

    public synchronized Map.Entry<String, String> get() {
//        Random generator = new Random();
//        String[] values = new String[data.size()];
//        values = data.values().toArray(values);
//
//        String randomKey = values[generator.nextInt(values.length)];
//
//        Map.Entry<String, String> resultPair = data.entrySet().stream()
//                        .filter(pair -> pair.getKey().equals(randomKey))
//                                .collect(Collectors)
//
//        remove(key);

        Map.Entry<String, String> resultPair = data.entrySet().iterator().next();

        remove(resultPair.getKey());

        return resultPair;
    }
}
