
public class Result {
	private int collision;
	private boolean flag;
	private long time;
	private String value;
	
	public Result() {
		collision = 0;
		flag = false;
		time = 0;
		value = "";
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getCollision() {
		return collision;
	}
	public void setCollision(int collision) {
		this.collision = collision;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
