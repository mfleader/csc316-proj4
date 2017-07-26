package main;


public class HashTable<K, V> {
	

	public static final int TABLE_SIZE = (int) Math.pow(2, 15);
	private LinkedList<K, V>[] table;
	public static final double PHI = (1 + Math.sqrt(5)) / 2;
	private int numKeys;
	private int numProbes;
	
	@SuppressWarnings("unchecked")
	public HashTable() {
		table = (LinkedList<K, V>[]) new LinkedList[TABLE_SIZE];
		numKeys = 0;
		numProbes = 0;
	}
	
	public int goldenCompression(int f) {
		f = Math.abs(f);
		return (int) (TABLE_SIZE * ((f * PHI) -  Math.floor(f * PHI)));
		//return Math.abs((int) (TABLE_SIZE * f / PHI));
	}
	

	public int polynomialHash(String key) {		
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
	
	public int hashValue(K key) {
		if (key instanceof String) {
			String keyString = (String) key;
			return goldenCompression(polynomialHash(keyString));
		} 		
		return goldenCompression(key.hashCode());
	}
	
	
	public void insert(K key, V value) {
		int hashValue = hashValue(key);
		if (table[hashValue] == null) {
			table[hashValue] = new LinkedList<K,V>();
		}
		table[hashValue].add(key, value);
		numKeys++;
	}
	
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
	
	public V delete(K key) {
		int hashValue = hashValue(key);
		if (table[hashValue] != null) {
			return table[hashValue].remove(key);
		}
		return null;
	}
	
	public double loadFactor() {
		return Math.round((double) numKeys / TABLE_SIZE * 1000.0) / 1000.0 ;
	}
	
	public int numKeys() {
		return numKeys;
	}
	
	public int getProbed(K key) {
		int hashValue = hashValue(key);
		return table[hashValue].getProbed();				
	}
	
	public void deProbe(K key) {
		int hashValue = hashValue(key);
		table[hashValue].deProbe();
	}
	
	public int numProbes() {
		return numProbes;
	}

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
	
	public int lastBucket() {
		int bucket = 0;
		for (int k = table.length - 1; k > -1; k--) {
			if (table[k] != null) {
				return k;
			}
		}
		return 0;
	}
	


}
