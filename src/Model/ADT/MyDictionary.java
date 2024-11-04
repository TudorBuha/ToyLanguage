package Model.ADT;

import Model.Exceptions.DictionaryException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V>{
    private HashMap<K, V> elems;

    public MyDictionary() {
        this.elems = new HashMap<K, V>();
    }

    @Override
    public void addKeyValuePair(K newKey, V newValue) {
        this.elems.put(newKey, newValue);   // returns the previous V value if K was used, null otherwise
    }

    @Override
    public V lookUp(K key) throws DictionaryException {
        if (!this.elems.containsKey(key)) {
            throw new DictionaryException("Failed to get value: the given key is not in the dictionary.");
        }
        return this.elems.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return this.elems.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder elemsInString = new StringBuilder();
        elemsInString.append("{");
        int i = 0;
        for (Map.Entry<K, V> entry: this.elems.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            elemsInString.append("\"");
            elemsInString.append(key.toString());
            elemsInString.append("\"");
            elemsInString.append(": ");
            elemsInString.append(value.toString());
            if (i < this.elems.size() - 1) {
                elemsInString.append(", ");
            }
            i += 1;
        }
        elemsInString.append("}");
        return elemsInString.toString();
    }
}
