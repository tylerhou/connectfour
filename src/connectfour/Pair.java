package connectfour;

public class Pair<First, Second> {

	public final First first;
	public final Second second;
	
	Pair(First first, Second second) {
		this.first = first;
		this.second = second;
	}
	
	public First first(){ return first; }
	
	public Second second(){ return second; }

	@Override
	public String toString() {
		return "(" + first.toString() + ", " + second.toString() + ")";
	}
}
