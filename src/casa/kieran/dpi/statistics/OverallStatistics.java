package casa.kieran.dpi.statistics;

import java.util.HashMap;
import java.util.Map;

public class OverallStatistics {

    private Map<Object, Statistics> statisticsMap = new HashMap<>();

    private Statistics overAllStatistics;

    public OverallStatistics() {

    }

    public void addStatistics(Object key, Statistics value) {
        statisticsMap.put(key, value);
    }

    public Map<Object, Statistics> getStatisticsMap() {
        return statisticsMap;
    }

    public void setStatisticsMap(Map<Object, Statistics> statisticsMap) {
        this.statisticsMap = statisticsMap;
    }

    public Statistics getOverAllStatistics() {
        return overAllStatistics;
    }

    public void setOverAllStatistics(Statistics overAllStatistics) {
        this.overAllStatistics = overAllStatistics;
    }
}
