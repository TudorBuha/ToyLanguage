package Model.ADT;

import Model.Exceptions.DictionaryException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V>{
    protected HashMap<K, V> elems;

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
    public V removeByKey(K key) throws DictionaryException {
        V value = this.elems.remove(key);
        if (value != null) {
            return value;
        }
        else {
            throw new DictionaryException("Failed to remove key-value pair: The given key is not in the dictionary");
        }
    }

    @Override
    public boolean isDefined(K key) {
        return this.elems.containsKey(key);
    }

    @Override
    public Map<K, V> getContent() {
        return this.elems;
    }

    @Override
    public void setContent(Map<K, V> newContent) {
        this.elems.clear();
        this.elems.putAll(newContent);
    }

    @Override
    public String toString() {
        StringBuilder elemsInString = new StringBuilder();
        int i = 0;
        for (Map.Entry<K, V> entry: this.elems.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            elemsInString.append(key.toString());
            elemsInString.append(" --> ");
            elemsInString.append(value.toString());
            if (i < this.elems.size() - 1) {
                elemsInString.append("\n");
            }
            i += 1;
        }
        return elemsInString.toString();
    }

    @Override
    public IDictionary<K, V> shallowCopy() {
        MyDictionary<K, V> newDict = new MyDictionary<K, V>();
        newDict.setContent(this.elems);
        return newDict;
    }
}
