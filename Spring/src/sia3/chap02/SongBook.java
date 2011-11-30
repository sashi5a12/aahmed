package sia3.chap02;

public class SongBook {
	private String[] songTitles;

	public SongBook(String[] songTitles) {
		this.songTitles = songTitles;
	}
	public String pickASong() {
		return songTitles[0];
	}
}
