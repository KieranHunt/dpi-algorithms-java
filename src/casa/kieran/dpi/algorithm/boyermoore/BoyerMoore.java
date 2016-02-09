package casa.kieran.dpi.algorithm.boyermoore;

import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.HashMap;
import java.util.Map;

public class BoyerMoore implements Algorithm {

    private static final int ALPHABET_SIZE = 256;

    private static BoyerMoore instance;

    private Rules rules;

    private Map<Rule, int[]> characterTableMap;

    private Map<Rule, int[]> offsetTableMap;

    public static BoyerMoore getInstance(Rules rules) {
        if (instance == null) {
            instance = new BoyerMoore();
        }
        instance.rules = rules;
        instance.makeCharacterTable();
        instance.makeOffsetTable();
        return instance;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        for (Rule rule :
                rules) {
            if (rule.getLength() == 0) {
                result.addLocation(0);
            }
            for (int i = rule.getLength() - 1, j; i < input.getLength(); ) {
                for (j = rule.getLength() - 1; rule.getByte(j) == input.getByte(i); --i, --j) {
                    if (j == 0) {
                        result.addLocation(i);
                        break;
                    }
                }
                // i += needle.length - j; // For naive method
                i += Math.max(
                        offsetTableMap.get(rule)[rule.getLength() - 1 - j],
                        characterTableMap.get(rule)[input.getByte(i) & 0xFF]);
            }
        }

        result.end();
        results.addResult(result);
    }

    private void makeCharacterTable() {
        characterTableMap = new HashMap<>();

        for (Rule rule :
                rules) {
            int[] table = new int[ALPHABET_SIZE];
            for (int i = 0; i < table.length; ++i) {
                table[i] = rule.getLength();
            }
            for (int i = 0; i < rule.getLength() - 1; ++i) {
                table[rule.getByte(i)] = rule.getLength() - 1 - i;
            }
            characterTableMap.put(rule, table);
        }
    }

    private void makeOffsetTable() {
        offsetTableMap = new HashMap<>();
        for (Rule rule :
                rules) {
            int[] table = new int[rule.getLength()];
            int lastPrefixPosition = rule.getLength();
            for (int i = rule.getLength() - 1; i >= 0; --i) {
                if (isPrefix(rule, i + 1)) {
                    lastPrefixPosition = i + 1;
                }
                table[rule.getLength() - 1 - i] = lastPrefixPosition - i + rule.getLength() - 1;
            }
            for (int i = 0; i < rule.getLength() - 1; ++i) {
                int slen = suffixLength(rule, i);
                table[slen] = rule.getLength() - 1 - i + slen;
            }
            offsetTableMap.put(rule, table);
        }
    }

    private boolean isPrefix(Rule rule, int p) {
        for (int i = p, j = 0; i < rule.getLength(); ++i, ++j) {
            if (rule.getByte(i) != rule.getByte(j)) {
                return false;
            }
        }
        return true;
    }

    private int suffixLength(Rule rule, int p) {
        int len = 0;
        for (int i = p, j = rule.getLength() - 1;
             i >= 0 && rule.getByte(i) == rule.getByte(j); --i, --j) {
            len += 1;
        }
        return len;
    }

    @Override
    public String toString() {
        return "Boyer-Moore";
    }
}
