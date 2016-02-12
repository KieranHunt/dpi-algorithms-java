package casa.kieran.dpi.algorithm.boyermoore;

import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BoyerMooreTest {

    Rules rules;

    @Before
    public void setup() {
        Rule rule = new Rule("GCAGAGAG");

        rules = new Rules();
        rules.addRule(rule);
    }

    @Test
    public void testPreBmBc() throws Exception {
        Map<Rule, List<Integer>> prebmbc = BoyerMoore.preBmBc(rules);

        Rule rule = rules.iterator().next();

        List<Integer> bmbc = prebmbc.get(rule);

        assertThat(bmbc.get((int) 'A'), is(1));
        assertThat(bmbc.get((int) 'C'), is(6));
        assertThat(bmbc.get((int) 'G'), is(2));
        assertThat(bmbc.get((int) 'T'), is(8));
    }

    @Test
    public void testSuffixes() throws Exception {
        Map<Rule, List<Integer>> suffixes = BoyerMoore.suffixes(rules);

        Rule rule = rules.iterator().next();

        List<Integer> suff = suffixes.get(rule);

        assertThat(suff.get(0), is(1));
        assertThat(suff.get(1), is(0));
        assertThat(suff.get(2), is(0));
        assertThat(suff.get(3), is(2));
        assertThat(suff.get(4), is(0));
        assertThat(suff.get(5), is(4));
        assertThat(suff.get(6), is(0));
        assertThat(suff.get(7), is(8));
    }

    @Test
    public void testPreBmGs() throws Exception {
        Map<Rule, List<Integer>> preBmGs = BoyerMoore.preBmGs(rules);

        Rule rule = rules.iterator().next();

        List<Integer> bmGs = preBmGs.get(rule);

        assertThat(bmGs.get(0), is(7));
        assertThat(bmGs.get(1), is(7));
        assertThat(bmGs.get(2), is(7));
        assertThat(bmGs.get(3), is(2));
        assertThat(bmGs.get(4), is(7));
        assertThat(bmGs.get(5), is(4));
        assertThat(bmGs.get(6), is(7));
        assertThat(bmGs.get(7), is(1));
    }
}