package database;
/** Klasa umo¿liwiaj¹ca dodanie nieobowi¹zkowych informacji dotycz¹cych artyku³u */
public class ArticleOptionalInfo {
	private String title;
	private String authors;
	private String source;

	private Integer year;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "TITLE: " + title + "  AUTHORS: " + authors + " SOURCE: "
				+ source + "  Year: " + year;
	}
}
