package casa.kieran.dpi.algorithm;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract public class AbstractParallelizableAlgorithm {

    private static final int DEFAULT_THREAD_POOL_SIZE = 10;

    protected void executeSearch(List<Runnable> runnableList) {

        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);

        runnableList.forEach(executorService::execute);

        executorService.shutdown();

        while (!executorService.isTerminated()) {

        }
    }
}
