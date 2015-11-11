package casa.kieran.algorithm.trie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kieran on 2015/11/10.
 */
public class TrieStructure {
    private HashMap<Byte, TrieStructure> children;
    private Byte character;
    private TrieStructure next;
    private Boolean isLeaf = false;

    public TrieStructure() {
        this.children = new HashMap<>();
    }

    public TrieStructure(Byte character) {
        this.children = new HashMap<>();
        this.character = character;
    }


    public void insert(ArrayList<Byte> term) {
        if (!this.children.containsKey(term.get(0))) {
            TrieStructure newTrieStructure = new TrieStructure(term.get(0));
            this.children.put(term.get(0), newTrieStructure);
        }

        if (term.size() == 1) {
            this.isLeaf = true;
            return;
        }

        this.children.get(term.get(0)).insert(new ArrayList<>(term.subList(1, term.size())));
    }

    public Boolean search(ArrayList<Byte> term) {
        if (this.isLeaf && term.size() == 1) {
            return true;
        }
        if (this.children.containsKey(term.get(0))) {
            return this.children.get(term.get(0)).search(new ArrayList<>(term.subList(1, term.size())));
        }

        return false;
    }

}
