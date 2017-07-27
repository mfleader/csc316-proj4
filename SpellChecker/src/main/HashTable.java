package main;

/**
 * This class models the state and behavior of a HashTable with
 * Separate Chaining.
 * @author Matthew F Leader
 *
 * @param <K>
 * 					the key type for this table
 * @param <V>
 * 					the value types matched to the keys
 */
public class HashTable<K, V> {
	
	/** size of the table */
	public static final int TABLE_SIZE = (int) Math.pow(2, 15);
	/** array of buckets that hold hash nodes */
	private LinkedHashNodeList<K, V>[] table;
	/** the value of the golden ratio */
	public static final double PHI = (1 + Math.sqrt(5)) / 2;
	/** the number of keys in this hash table */
	private int numKeys;
	/** the total number of key comparisons made over every bucket in the table */
	private int numProbes;
	
	/**
	 * HashTable constructor
	 */
	@SuppressWarnings("unchecked")
	public HashTable() {
		table = (LinkedHashNodeList<K, V>[]) new LinkedHashNodeList[TABLE_SIZE];
		numKeys = 0;
		numProbes = 0;
	}
	
	/**
	 * Compress a given integer to fit within the hash table
	 * @param f
	 * 				a given int
	 * @return a compressed integer within the bounds of the table
	 */
	public int goldenCompression(int f) {
		f = Math.abs(f);
		return (int) (TABLE_SIZE * ((f * PHI) -  Math.floor(f * PHI)));
		//return Math.abs((int) (TABLE_SIZE * f / PHI)); this equation is
		//from slide 22 does not solely take the fractional part, so it does
		//not compress
	}
	
	/**
	 * Polynomial hash of a String object that incorporates letter
	 * position.
	 * @param key
	 * 					the key to be hashed
	 * @return an integer representing the key
	 */
	public int polynomialHash(String key) {		
		//added extra variables to view their values in the debugger
		int hash = 0;
		int index = 0;
		int val = 0;
		int power = 1;
		for (int k = key.length() - 1; k > -1; k--) {
			power = (int) Math.pow(128, k);
			val = key.charAt(index) * power;
			hash += val;
			index++;
		}
		return hash;
	}
	
	/**
	 * The hash function for this hash table
	 * @param key
	 * 					the key to be hashed
	 * @return an integer representing the hash within the range
	 * 			of the table size
	 */
	public int hashValue(K key) {
		if (key instanceof String) {
			String keyString = (String) key;
			return goldenCompression(polynomialHash(keyString));
		} 		
		return goldenCompression(key.hashCode());
	}
	
	/**
	 * Inserts key and value into a hash node in the appropriate bucket
	 * in the table
	 * @param key
	 * 					the key associated with the given value
	 * @param value
	 * 					the value associated with the given key
	 */
	public void insert(K key, V value) {
		int hashValue = hashValue(key);
		if (table[hashValue] == null) {
			table[hashValue] = new LinkedHashNodeList<K,V>();
		}
		table[hashValue].add(key, value);
		numKeys++;
	}
	
	/**
	 * Looks up a key in the hash table at the index represented by the
	 * hash value of the key
	 * @param key
	 * 					the key to lookup
	 * @return a value corresponding to the key, or null if the key
	 * 		   was not found
	 */
	public V lookup(K key) {
		int hashValue = hashValue(key);
		if (table[hashValue] != null) {
			V value = table[hashValue].find(key);
			numProbes += table[hashValue].getProbed();
			table[hashValue].deProbe();
			return value;
		}
		return null;
	}
	
	/**
	 * Delete a value given it's key
	 * @param key
	 * 					the key of the value to delete
	 * @return the value and key pair deleted
	 */
	public V delete(K key) {
		int hashValue = hashValue(key);
		if (table[hashValue] != null) {
			return table[hashValue].remove(key);
		}
		return null;
	}
	
	/**
	 * The current load factor of the table
	 * @return the hash table's load factor
	 */
	public double loadFactor() {
		return Math.round((double) numKeys / TABLE_SIZE * 1000.0) / 1000.0 ;
	}
	
	/**
	 * The number of keys in the hash table
	 * @return the number of keys in the hash table
	 */
	public int numKeys() {
		return numKeys;
	}
	
	/**
	 * 
	 * @return the number of probes on this table
	 */
	public int numProbes() {
		return numProbes;
	}

	/**
	 * Find the bucket with the longest list
	 * @return the index of the bucket with the longest list
	 * 			and the size of that list
	 */
	public String largestBucket() {
		int bucketSize = 1;
		String bucket = "N/A";
		for (int k = 0; k < table.length; k++) {
			if (table[k] != null && table[k].size() > bucketSize) {
				bucketSize = table[k].size();
				bucket = "table[" + k + "].size() = " + table[k].size();
			}
		}
		return bucket;
	}
	
	/**
	 * The last bucket used in the array
	 * @return the index of the last bucket in the array
	 */
	public int lastBucket() {
		for (int k = table.length - 1; k > -1; k--) {
			if (table[k] != null) {
				return k;
			}
		}
		return 0;
	}
}
