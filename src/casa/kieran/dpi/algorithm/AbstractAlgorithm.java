package casa.kieran.dpi.algorithm;

import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract public class AbstractAlgorithm implements Algorithm {

    public static final int ALPHABET_SIZE = 256;

    private int threadCount;

    public static final int DEFAULT_THREAD_COUNT = 10;

    protected void executeSearch(List<Runnable> runnableList) {

        int threadsToUse = threadCount == 0 ? DEFAULT_THREAD_COUNT : threadCount;

        ExecutorService executorService = Executors.newFixedThreadPool(threadsToUse);

        runnableList.forEach(executorService::execute);

        executorService.shutdown();

        while (!executorService.isTerminated()) {

        }
    }

    public boolean memcmp(Rule rule, Input input, int i, int j, int n) {
        for (int k = 0; k < n; k++) {
            if (!input.getByte(j + k).equals(rule.getByte(i + k))) {
                return false;
            }
        }
        return true;
    }

    public void memcpy(List<Integer> input, int from, int size) {
        List<Integer> newInput = new ArrayList<>();
        for (int i = from; i < from + size; i++) {
            if (i < input.size()) {
                newInput.add(input.get(i));
            } else {
                newInput.add(0);
            }
        }
        input = newInput;
    }

    public void memset(List<Integer> input, int from, int to, int value) {
        for (int i = from; i < to; i++) {
            input.set(i, value);
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
