
public class SLL<T, C> {
	Node<T> head;
	
	public Node<T> getHead() {
		return head;
	}

	public void Add(dataToAdd<T> dataToAdd) {
		//to add empty SLL
		 if (head == null) {
			 Node<T> newnode = new Node<T>(dataToAdd.getValue(), dataToAdd.getTxt());
			 head = newnode;
		 }
		 //to add proper places
		 else {
			 Node<T> temp = head;
			 while (temp.getLink() != null) {  //to find node we want to add
				 temp = temp.getLink();
			 }
			 Node<T> newNode = new Node<T>(dataToAdd.getValue(), dataToAdd.getTxt());
			temp.setLink(newNode);
		 }
	 }
}
