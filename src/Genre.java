public class Genre {
    private String name;
    private String description;

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return '\n'+"Жанр:"  + " Название= " + name  + " Описание= " + description;
    }
    public String getName(){
        return name;
    }

}
