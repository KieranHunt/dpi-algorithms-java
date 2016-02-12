package casa.kieran.dpi.algorithm.knuthmorrispratt;

import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class KnuthMorrisPrattTest {

    private KnuthMorrisPratt knuthMorrisPratt;

    private Rules rules;

    @Before
    public void setUp() throws Exception {

        Rule rule = new Rule("GCAGAGAG");

        rules = new Rules();
        rules.addRule(rule);

        knuthMorrisPratt = KnuthMorrisPratt.getInstance(rules);
    }

    @Test
    public void testPrecompute() throws Exception {
        Map<Rule, int[]> ruleMap = KnuthMorrisPratt.precompute(rules);

        Rule rule = rules.iterator().next();

        int[] kmpNext = ruleMap.get(rule);

        assertThat(ruleMap.size(), is(1));

        int[] shouldBe = {-1, 0, 0, -1, 1, -1, 1, -1, 1};
        assertArrayEquals(shouldBe, kmpNext);
    }
}