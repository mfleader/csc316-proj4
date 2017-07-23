package main;

public class HashTable<K, V> {
	

	private static final int TABLE_SIZE = 25144;
	private LinkedList<K, V>[] table;
	private double PHI = (1 + Math.sqrt(5)) / 2;
	
	public HashTable() {
		table = new LinkedList[TABLE_SIZE];
		for (int k = 0; k < TABLE_SIZE; k++) {
			table[k] = new LinkedList<K, V>();
		}
	}
	
	private int hash(K key) {
		int f = 0;
		if (key instanceof String) {
			String keyString = (String) key;
			f = polynomialHash(keyString);
		} else {
			f = key.hashCode();
		}
		return (int) (TABLE_SIZE * ((f / PHI) -  (int) (f / PHI)));
	}
	


	private int polynomialHash(String key) {		
		int f = 0;
		for (int k = key.length() - 1; k > -1; k--) {
			f += key.charAt(k) * Math.pow(2, k);
		}
		return f;
	}
	
	
	
	public void insert(K key, V value) {
		
	}
	
	public V lookUp(double key) {
		return null;
	}
	
	public V delete(double key) {
		return null;
	}

}
