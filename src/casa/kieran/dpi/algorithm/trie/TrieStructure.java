package casa.kieran.dpi.algorithm.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (term.size() == 0) {
            this.isLeaf = true;
            return;
        }

        if (!this.children.containsKey(term.get(0))) {
            TrieStructure newTrieStructure = new TrieStructure(term.get(0));
            this.children.put(term.get(0), newTrieStructure);
        }

        this.children.get(term.get(0)).insert(new ArrayList<>(term.subList(1, term.size())));
    }

    public Boolean search(List<Byte> term) {
        if (this.isLeaf) {
            return true;
        }
        if (term.size() == 0) {
            return false;
        }
        if (this.children.containsKey(term.get(0))) {
            return this.children.get(term.get(0)).search(new ArrayList<>(term.subList(1, term.size())));
        }

        return false;
    }

}
