import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class BlogWritable implements Writable, Comparable<BlogWritable> {
	private String author;
	private String content;

	public BlogWritable() {
	}

	public BlogWritable(String author, String content) {
		this.author = author;
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		author = input.readUTF();
		content = input.readUTF();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(author);
		output.writeUTF(content);
	}

	@Override
	public int compareTo(BlogWritable other) {
		return author.compareTo(other.author);
	}
}