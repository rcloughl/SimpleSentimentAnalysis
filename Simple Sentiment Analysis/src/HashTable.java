// HashTable.java

public class HashTable<Key, Value> {
    // these are parallel arrays, so indices in one correspond to the other
    private Value[] values;
    private Key[] keys;
    private int maxSize;
    private int numEntries;

    public HashTable(int maxSize) {
        this.maxSize = maxSize;
        
        // make the arrays (with generic array workaround)
        values = (Value[]) new Object[maxSize];
        keys = (Key[]) new Object[maxSize];
        numEntries = 0;
    }

    // insert a key/value pair
    public void insert(Key key, Value value) {
        //checks if the size of the hash is over half
        if ((numEntries*2)>=maxSize)
            expand();

        // get the starting index from hash function
        int index = Math.abs(key.hashCode()) % maxSize;
   
        // linear probe until spot is open
        while (values[index] != null) {
            index = (index + 1) % maxSize;
        }
    
        // now insert this pair here
        values[index] = value;
        keys[index] = key;
        numEntries++;
    }

    // lookup a value by its key
    public Value lookup(Key key) {
        // get the starting index from hash function
        int index = Math.abs(key.hashCode()) % maxSize;

        // loop while there is data here
        while (keys[index] != null) {
            // if the key here matches target, return corresponding value
            if (keys[index].equals(key)) {
                return values[index];
            }

            // move to next index to search (with wrap around)
            index = (index + 1) % maxSize;
        }

        // if we fell off the end, the key is not here
        return null;
    }

    private void expand() {
        int newMax = 2 * maxSize;
        Value[] nValues = (Value[]) new Object[newMax];
        Key[] nKeys = (Key[]) new Object[newMax];
        int index;
        for (Key k : keys) {
            if (k!=null) {
                index = Math.abs(k.hashCode() % newMax);
                nValues[index] = lookup(k);
                nKeys[index] = k;
            }
        }

        keys=nKeys;
        values=nValues;
        maxSize =newMax;
    }
}
