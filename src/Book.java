public class Book {
    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer book_id;
    private String name;
    private String autor;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
