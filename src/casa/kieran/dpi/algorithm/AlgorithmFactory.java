package casa.kieran.dpi.algorithm;

import casa.kieran.dpi.algorithm.ahocorasick.AhoCorasick;
import casa.kieran.dpi.algorithm.apostolicocrochemore.ApostolicoCrochemore;
import casa.kieran.dpi.algorithm.apostolicogiancarlo.ApostolicoGiancarlo;
import casa.kieran.dpi.algorithm.bitap.Bitap;
import casa.kieran.dpi.algorithm.boyermoore.BoyerMoore;
import casa.kieran.dpi.algorithm.colussi.Colussi;
import casa.kieran.dpi.algorithm.galilgiancarlo.GalilGiancarlo;
import casa.kieran.dpi.algorithm.horspool.Horspool;
import casa.kieran.dpi.algorithm.knuthmorrispratt.KnuthMorrisPratt;
import casa.kieran.dpi.algorithm.morrispratt.MorrisPratt;
import casa.kieran.dpi.algorithm.naive.Naive;
import casa.kieran.dpi.algorithm.notsonaive.NotSoNaive;
import casa.kieran.dpi.algorithm.quicksearch.QuickSearch;
import casa.kieran.dpi.algorithm.rabinkarp.RabinKarp;
import casa.kieran.dpi.algorithm.raita.Raita;
import casa.kieran.dpi.algorithm.reversecolussi.ReverseColussi;
import casa.kieran.dpi.algorithm.simon.Simon;
import casa.kieran.dpi.algorithm.smith.Smith;
import casa.kieran.dpi.algorithm.trie.Trie;
import casa.kieran.dpi.algorithm.turboboyermoore.TurboBoyerMoore;
import casa.kieran.dpi.algorithm.zhutakaoka.ZhuTakaoka;
import casa.kieran.dpi.exception.UnimplementedAlgorithmException;
import casa.kieran.dpi.rule.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlgorithmFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmFactory.class);

    public static Algorithm getAlgorithm(Class<Algorithm> algorithmClass, Rules rules) throws
            UnimplementedAlgorithmException {
        return getAlgorithm(algorithmClass.getName(), rules);
    }

    public static Algorithm getAlgorithm(String className, Rules rules) throws UnimplementedAlgorithmException {
        Algorithm algorithm;

        switch (className) {
            case "Bitap":
                algorithm = Bitap.getInstance(rules);
                break;
            case "BoyerMoore":
                algorithm = BoyerMoore.getInstance(rules);
                break;
            case "MorrisPratt":
                algorithm = MorrisPratt.getInstance(rules);
                break;
            case "KnuthMorrisPratt":
                algorithm = KnuthMorrisPratt.getInstance(rules);
                break;
            case "Naive":
                algorithm = Naive.getInstance(rules);
                break;
            case "Trie":
                algorithm = Trie.getInstance(rules);
                break;
            case "AhoCorasick":
                algorithm = AhoCorasick.getInstance(rules);
                break;
            case "Simon":
                algorithm = Simon.getInstance(rules);
                break;
            case "ApostolicoCrochemore":
                algorithm = ApostolicoCrochemore.getInstance(rules);
                break;
            case "NotSoNaive":
                algorithm = NotSoNaive.getInstance(rules);
                break;
            case "TurboBoyerMoore":
                algorithm = TurboBoyerMoore.getInstance(rules);
                break;
            case "ApostolicoGiancarlo":
                algorithm = ApostolicoGiancarlo.getInstance(rules);
                break;
            case "Colussi":
                algorithm = Colussi.getInstance(rules);
                break;
            case "GalilGiancarlo":
                algorithm = GalilGiancarlo.getInstance(rules);
                break;
            case "ReverseColussi":
                algorithm = ReverseColussi.getInstance(rules);
                break;
            case "Horspool":
                algorithm = Horspool.getInstance(rules);
                break;
            case "ZhuTakaoka":
                algorithm = ZhuTakaoka.getInstance(rules);
                break;
            case "RabinKarp":
                algorithm = RabinKarp.getInstance(rules);
                break;
            case "QuickSearch":
                algorithm = QuickSearch.getInstance(rules);
                break;
            case "Smith":
                algorithm = Smith.getInstance(rules);
                break;
            case "Raita":
                algorithm = Raita.getInstance(rules);
                break;
            default:
                LOGGER.error("Unimplemented Algorithm: " + className, new UnimplementedAlgorithmException());
                throw new UnimplementedAlgorithmException();
        }

        return algorithm;
    }
}
