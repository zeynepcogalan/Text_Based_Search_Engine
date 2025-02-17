public class Node<T> {
	private T value;
	private int txt;
	private Node<T> link;
	
	public Node(T value, int txt) {
		this.value = value;
		this.txt = txt;
		link = null;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public int getTxt() {
		return txt;
	}
	public void setTxt(int txt) {
		this.txt = txt;
	}
	public Node<T> getLink() {
		return link;
	}
	public void setLink(Node<T> link) {
		this.link = link;
	}
	
}
