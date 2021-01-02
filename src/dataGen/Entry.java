package dataGen;

public class Entry implements Comparable<Entry> {
	int id;
	int time;
	
	public Entry(int id2, Integer time2) {
		this.id = id2;
		this.time = time2;
	}

	public int compareTo(Entry e) {
		return time - e.time;
	}
	
	public String toString() {
		return id + ", " + time + "\n";
	}
}
