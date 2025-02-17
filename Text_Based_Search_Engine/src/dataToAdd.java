
public class dataToAdd<V> {
	private int index;
	private V value;
	private int txt;
	
	public dataToAdd(V value, int txt) {
		this.value = value;
		this.txt = txt;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	public int getTxt() {
		return txt;
	}
	public void setTxt(int txt) {
		this.txt = txt;
	}
}
