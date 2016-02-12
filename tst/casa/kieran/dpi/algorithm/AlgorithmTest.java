package casa.kieran.dpi.algorithm;

import casa.kieran.dpi.algorithm.ApostolicoCrochemore.ApostolicoCrochemore;
import casa.kieran.dpi.algorithm.ahocorasick.AhoCorasick;
import casa.kieran.dpi.algorithm.bitap.Bitap;
import casa.kieran.dpi.algorithm.boyermoore.BoyerMoore;
import casa.kieran.dpi.algorithm.knuthmorrispratt.KnuthMorrisPratt;
import casa.kieran.dpi.algorithm.morrispratt.MorrisPratt;
import casa.kieran.dpi.algorithm.naive.Naive;
import casa.kieran.dpi.algorithm.notsonaive.NotSoNaive;
import casa.kieran.dpi.algorithm.simon.Simon;
import casa.kieran.dpi.algorithm.trie.Trie;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.input.Text;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class AlgorithmTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmTest.class);

    public Algorithm algorithm;

    public Input input;

    public AlgorithmTest(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Before
    public void setUp() throws Exception {
        input = new Text("GCATCGCAGAGAGTATACAGTACG");
    }

    @Test
    public void testSearch() throws Exception {

        LOGGER.info("Testing search for " + algorithm.getClass().getSimpleName());

        Results results = new Results(mock(casa.kieran.dpi.test.Test.class));

        int runNumber = 0;
        String runId = "";

        algorithm.search(input, results, runNumber, runId);

        assertThat(results.getResults().size(), is(1));

        Result result = results.getResults().iterator().next();

        List<Integer> locationsShouldBe = Arrays.asList(5, 8, 10);

        assertThat("Expected 3 result location", result.getLocations().size(), is(3));

        result.getLocations().forEach(location -> assertThat(locationsShouldBe, hasItem(location)));
    }

    @Parameterized.Parameters
    public static Collection<Algorithm> instancesToTest() {
        Rule rule1 = new Rule("GCAGAGAG");
        Rule rule2 = new Rule("GA");

        Rules rules = new Rules();
        rules.addRule(rule1);
        rules.addRule(rule2);

        LOGGER.info("Searching for " + rules + "\n");

        return Arrays.asList(
                Naive.getInstance(rules),
                Trie.getInstance(rules),
                MorrisPratt.getInstance(rules),
                AhoCorasick.getInstance(rules),
                KnuthMorrisPratt.getInstance(rules),
                BoyerMoore.getInstance(rules),
                ApostolicoCrochemore.getInstance(rules),
                Bitap.getInstance(rules),
                Simon.getInstance(rules),
                NotSoNaive.getInstance(rules)
        );
    }
}