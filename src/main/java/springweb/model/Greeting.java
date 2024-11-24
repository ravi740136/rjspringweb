package springweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GREETING")
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	private String header;

    // Constructors, Getters, and Setters
    public Greeting() {}

    public Greeting(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	@Override
	public String toString() {
		return "Greeting [id=" + id + ", content=" + content + ", header=" + header + "]";
	}
    
 
}
