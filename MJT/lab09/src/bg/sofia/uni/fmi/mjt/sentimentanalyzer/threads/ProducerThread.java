package bg.sofia.uni.fmi.mjt.sentimentanalyzer.threads;

import bg.sofia.uni.fmi.mjt.sentimentanalyzer.AnalyzerInput;
import bg.sofia.uni.fmi.mjt.sentimentanalyzer.wrapper.MySynchronizedMap;

import java.io.BufferedReader;
import java.io.IOException;

public class ProducerThread extends Thread {
    private AnalyzerInput input;
    private MySynchronizedMap syncMap;

    public ProducerThread(AnalyzerInput input, MySynchronizedMap syncMap) {
        this.input = input;
        this.syncMap = syncMap;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(input.inputReader())) {
            syncMap.put(input.inputID(), br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
