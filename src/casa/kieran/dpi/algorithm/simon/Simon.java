package casa.kieran.dpi.algorithm.simon;

import casa.kieran.dpi.algorithm.AbstractParallelizableAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Simon string search algorithm
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node9.html
 */
public class Simon extends AbstractParallelizableAlgorithm implements Algorithm {

    private static Simon instance;

    private Rules rules;

    private Map<Rule, List<Cell>> table;

    private Map<Rule, Integer> state;

    public static Simon getInstance(Rules rules) {
        if (instance == null) {
            instance = new Simon();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;

        table = new HashMap<>();
        state = new HashMap<>();

        rules.getRules().forEach(rule -> {

            int m = rule.getLength();

            int i;
            int k;
            int ell;

            Cell cell;
            List<Cell> L = new LinkedList<>(Collections.nCopies(m - 2 + 1, null));

            ell = -1;
            for (i = 1; i < m; ++i) {
                k = ell;
                cell = (ell == -1) ? null : L.get(k);
                ell = -1;
                if (rule.getByte(i).equals(rule.getByte(k + 1))) {
                    ell = k + 1;
                } else {
                    setTransition(i - 1, k + 1, L);
                }
                while (cell != null) {
                    k = cell.element;
                    if (rule.getByte(i).equals(rule.getByte(k))) {
                        ell = k;
                    } else {
                        setTransition(i - 1, k, L);
                    }
                    cell = cell.next;
                }
            }

            table.put(rule, L);
            state.put(rule, ell);
        });
    }

    private void setTransition(int p, int q, List<Cell> L) {
        Cell cell = new Cell();

        cell.element = q;
        cell.next = L.get(p);
        L.set(p, cell);
    }

    private int getTransition(Rule rule, int p, List<Cell> L, byte c) {
        int m = rule.getLength();

        Cell cell;

        if (p < m - 1 && rule.getByte(p + 1) == c) {
            return p + 1;
        } else if (p > -1) {
            cell = L.get(p);
            while (cell != null) {
                if (rule.getByte(cell.element) == c) {
                    return cell.element;
                } else {
                    cell = cell.next;
                }
            }
            return -1;
        } else {
            return -1;
        }
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        for (Rule rule : rules) {
            Runnable simon = new SimonRunnable(input, result, n, rule);
            runnables.add(simon);
        }

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    private class SimonRunnable implements Runnable {

        Input input;
        Result result;
        int n;
        Rule rule;

        public SimonRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        @Override
        public void run() {
            int j;
            int ell;
            int currState;

            int m = rule.getLength();

            ell = state.get(rule);

            for (currState = -1, j = 0; j < n; ++j) {
                currState = getTransition(rule, currState, table.get(rule), input.getByte(j));
                if (currState >= m - 1) {
                    result.addLocation(j - m + 1);
                    currState = ell;
                }
            }
        }
    }

    private class Cell {
        public int element;
        private Cell next;
    }

    @Override
    public String toString() {
        return "Simon";
    }
}
