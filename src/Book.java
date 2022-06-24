import java.time.LocalDateTime;

public class Book {
    private String name;
    private String description;
    private int yearOfRelease;
    Author author;
    Genre genre;

    public Book(String name, String description, int yearOfRelease,Author author, Genre genre) {
        this.name = name;
        this.description = description;
        this.yearOfRelease = yearOfRelease;
        this.author=author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return '\n'+"Книга: " + "Название= " + name  + " Описание= " + description  +'\n' +"Год релиза= " + yearOfRelease  + " Жанр= " + genre.getName()+" Автор "+author.getName()+" "+author.getSurname();
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
