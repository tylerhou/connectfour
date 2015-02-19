package connectfour;

public class Pair<First, Second> {

	public final First first;
	public final Second second;
	
	Pair(First first, Second second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return "(" + first.toString() + ", " + second.toString() + ")";
	}
}
