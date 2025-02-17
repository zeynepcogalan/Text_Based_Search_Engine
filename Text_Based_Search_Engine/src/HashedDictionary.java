
public class HashedDictionary<K, V, L, C, T> implements DictionaryInterface<K, V, L, C, T> {
	private SLL<V, T>[] hashTable; 
	char[] letters = new char[] {' ','a','b','c','d','e','f','g','h','i','j','k','l','m',
			                         'n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private int numberOfEntries;
	private double locationsUsed; 
	private static final int DEFAULT_SIZE = 2477; 
	public HashedDictionary() {
		this(DEFAULT_SIZE); 
	} 

	@SuppressWarnings("unchecked")
	public HashedDictionary(int tableSize) {
		int primeSize = getNextPrime(tableSize);
		hashTable = new SLL[primeSize];
		numberOfEntries = 0;
		locationsUsed = 0;	
	}

	public boolean isPrime(int num) {
		boolean prime = true;
		for (int i = 2; i <= num / 2; i++) {
			if ((num % i) == 0) {
				prime = false;
				break;
			}
		}
		return prime;
	}

	
	public int getNextPrime(int num) {
		if (num <= 1)
            return 2;
		else if(isPrime(num))
			return num;
        boolean found = false;   
        while (!found)
        {
            num++;     
            if (isPrime(num))
                found = true;
        }     
        return num;
	}
	

	public void add_LP(V value, L load, C calculation, int txt) {
		String collision = "LP";
		if(value != null && value != " ") {
			if (isHashTableTooFull(load)) 
				rehash(load, calculation, collision, txt); 
			
			int index = getHashIndex(value, calculation);
			index = probe_LP(index, value); 
			
			if ((hashTable[index] == null)) { 
				hashTable[index] = new SLL<V, T>();
				dataToAdd<V> data = new dataToAdd<V>(value, txt);  //create to give all information needed 
				data.setIndex(index);
				hashTable[index].Add(data);      //to add in SLL
				numberOfEntries++;
				locationsUsed++;
			} else {
				dataToAdd<V> data = new dataToAdd<V>(value, txt);
				data.setIndex(index);
				hashTable[index].Add(data);
			} 	
		}
	}
	
	public void add_DH(V value, L load, C calculation , int txt) {
		String collision = "DH";
		if(value != null && value != " ") {
			if (isHashTableTooFull(load))
				rehash(load, calculation, collision, txt);
			
			int index = getHashIndex(value, calculation);
			index = probe_DH(index, value);

			if ((hashTable[index] == null)) { 
				hashTable[index] = new SLL<V, T>();
				dataToAdd<V> data = new dataToAdd<V>(value, txt);  //create to give all information needed 
				data.setIndex(index);
				hashTable[index].Add(data);      //to add in SLL
				numberOfEntries++;
				locationsUsed++;
			} else { 
				dataToAdd<V> data = new dataToAdd<V>(value, txt);
				data.setIndex(index);
				hashTable[index].Add(data);
			} 
		}
	}

	@SuppressWarnings("unchecked")
	private int getHashIndex(V value, C calculation) { 
		calculation = (C) calculation.toString().toUpperCase();
		String word = String.valueOf(value);
		if(calculation.equals("SSF")) {
			int count = 0;
			for(int i = 0; i < word.length(); i++) {
				for(int j = 1; j <= letters.length - 1; j++) {
					if(word.charAt(i) == letters[j]) {
						count += j; 
					}
				}
			}
			return count;
		}
		else {  
			int count = 0;
			for(int i = 0; i < word.length(); i++) {
				for(int j = 1; j <= letters.length - 1; j++) {
					if(word.charAt(i) == letters[j]) {
						count += j * (Math.pow(7, word.length() - 1 - i)); 
					}
				}
			}
			return count % hashTable.length ;
		}
	}

	public boolean isHashTableTooFull(L load) {
		double load_factor = locationsUsed  / (double)hashTable.length ;
		double main_load_factor = Double.parseDouble(load.toString()); 
		if (load_factor >= main_load_factor) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void rehash(L load, C calculation, String collision, int txt) {
		SLL<V, T>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(2 * oldSize);
		hashTable = new SLL[newSize]; 
		numberOfEntries = 0; 
		locationsUsed = 0;
		
		if(collision.equals("LP")) {
			for (int index = 0; index < oldSize; index++) {
				if(oldTable[index] != null && oldTable[index].getHead() != null) {
					if(oldTable[index].getHead().getValue() != null)
						add_LP(oldTable[index].getHead().getValue(), load, calculation, txt);	
				}
			}
		}
		else {
			for (int index = 0; index < oldSize; index++) {
				if(oldTable[index] != null && oldTable[index].getHead() != null) {
					if(oldTable[index].getHead().getValue() != null)
						add_DH(oldTable[index].getHead().getValue(), load, calculation, txt);	
				}
			}
		}
		
	}

	private int probe_LP(int index, V value) {
		boolean found = false; 
		while (!found && (hashTable[index] != null) && (hashTable[index].getHead() != null)) {
			if (value.equals(hashTable[index].getHead().getValue())) {
				found = true; 
			}
				
			else {
				index = (index + 1) % hashTable.length;
			}
		} 
		return index; 
	}
	
	private int probe_DH(int index, V value) {
		boolean found = false;
		int i = 0;
		while (!found && (hashTable[index] != null) && (hashTable[index].getHead() != null)) {
			if (value.equals(hashTable[index].getHead().getValue())) {
				found = true; 
			}
				
			else {
				index = ((index % hashTable.length) + (i *(31 - (index % 31)) 
		                % hashTable.length)) % hashTable.length; 
				i++;
			}
		} 
			return index; 
	}

	public Result contains_LP(V value, C calculation) {
		boolean found = false;
		Result result = new Result();
		if(value != null && value != " ") {
			int index = getHashIndex(value, calculation); 
			while (!found && (hashTable[index] != null) && (hashTable[index].getHead() != null)) {
				if (value.equals(hashTable[index].getHead().getValue())) {
					found = true; 
					result.setFlag(found);
				}
				
				else {
					index = (index + 1) % hashTable.length;
					int number = result.getCollision();
					result.setCollision(number + 1);
				} 
			}
		}
		return result;
	}
	
	public Result contains_DH(V value, C calculation) {
		boolean found = false;
		Result result = new Result();
		//long time = System.nanoTime();
		int i = 0;
		if(value != null && value != " ") {
			int index = getHashIndex(value, calculation); 
			while (!found && (hashTable[index] != null) && (hashTable[index].getHead() != null)) {
				if (value.equals(hashTable[index].getHead().getValue())) {
					found = true; 
					result.setFlag(found);
				}
				
				else {
					index = ((index % hashTable.length) + (i *(31 - (index % 31)) 
			                % hashTable.length)) % hashTable.length; 
                    i++;
				}	
			}
			result.setCollision(i);
		}
		return result;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	public int getSize() {
		return numberOfEntries;
	}
}
