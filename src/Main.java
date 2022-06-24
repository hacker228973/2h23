import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String fileNameGenres = "Genres.txt";
    static String fileNameAuthors = "Authors.txt";
    static String fileNameBooks = "Books.txt";

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        List<Genre> genres = recordAllGenresFromFileToList();
        List<Author> authors = recordAllAuthorsFromFileToList(genres);
        List<Book> books = recordAllBooksFromFileToList(authors, genres);
        System.out.println("Доброго времени суток," + '\n' + "Программа Умный полностью в вашем распоряжении" + '\n' + "Напишите exit в случае если вы хотите меня кинуть");
        System.out.println("Список команд");
        System.out.println("1. Добавить жанр");
        System.out.println("2. Добавить автора");
        System.out.println("3. Добавить книгу");
        System.out.println("4. Удалить жанр");
        System.out.println("5. Удалить автора");
        System.out.println("6. Удалить книгу");
        System.out.println("7. Вывести все жанры");
        System.out.println("8. Вывести всех авторов");
        System.out.println("9. Вывести все книги");
        cleverProgram(scanner, genres, authors, books);

        recordAllGenresFromListToFile(genres);
        recordAllAuthorFromListToFile(authors);
        recordAllBooksFromListToFile(books);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ЗАПИСЬ ЖАНРОВ ИЗ ФАЙЛА//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Genre> recordAllGenresFromFileToList() {
        List<Genre> newGenres = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileNameGenres))) {
            br.readLine();
            String str;
            while ((str = br.readLine()) != null) {
                str = str.replaceAll("Жанр: Название= ", "");
                str = str.replaceAll("Описание= ", "");
                str = str.replaceAll(",", "");
                str = str.replaceAll("]", "");
                String[] arr = str.split(" ");
                String name = arr[0];
                StringBuilder description = new StringBuilder();
                for(int i = 1;i<arr.length;i++){
                    if(description.toString().equals("")){
                        description.append(arr[i]);
                    }
                    else{
                        description.append(" ").append(arr[i]);
                    }

                }
                newGenres.add(new Genre(name, description.toString()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newGenres;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ЗАПИСЬ АВТОРОВ ИЗ ФАЙЛА//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Author> recordAllAuthorsFromFileToList(List<Genre> genres) {
        List<Author> newAuthors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileNameAuthors))) {

            br.readLine();
            String str;
            while ((str = br.readLine()) != null) {

                str = str.replaceAll("Автор Имя= ", "");
                str = str.replaceAll(", Фамилия=", "");
                str = str.replaceAll(",", "");
                str = str.replaceAll("]", "");
                String[] arr = str.split(" ");
                String name = arr[0];
                String surname = arr[1];
                Author newAuthor = new Author(name, surname);
                String nextLine = br.readLine();
                if (nextLine != null) {
                    String[] wordsOfNextLine = nextLine.split(" ");
                    if (wordsOfNextLine[0].equals("Книга:")) {
                        thisMethod(nextLine, br, genres, newAuthor);


                    }
                }
//


                newAuthors.add(newAuthor);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newAuthors;
    }

    public static void thisMethod(String nextLine, BufferedReader br, List<Genre> genres, Author newAuthor) throws IOException {
        nextLine = nextLine.replaceAll("Книга: Название= ", "");

        nextLine = nextLine.replaceAll(",", "");
        nextLine = nextLine.replaceAll("]", "");


        String[] arr2 = nextLine.split(" ");
        StringBuilder nameBook = new StringBuilder();

        String source;
        int index = 0;
        while (!arr2[index].equals("Описание=")) {
            source = arr2[index];
            if (nameBook.toString().equals("")) {
                nameBook.append(source);
            } else {
                nameBook.append(" ").append(source);
            }

            index++;

        }

        StringBuilder descriptionBook = new StringBuilder();
        for (int i = index + 1; i < arr2.length; i++) {


            if (descriptionBook.toString().equals("")) {
                descriptionBook.append(arr2[i]);
            } else {
                descriptionBook.append(" ").append(arr2[i]);
            }
        }

        String str2;
        str2 = br.readLine();
        str2 = str2.replaceAll(",", "");
        str2 = str2.replaceAll("]", "");
        arr2 = str2.split(" ");
        int yearOfRelease;
        try {
            yearOfRelease = Integer.parseInt(arr2[2]);
        } catch (Exception e) {
            System.out.println("Exception " + e);
            return;
        }

        Genre genre = null;
        String nameOfGenre = arr2[4];
        for (Genre value : genres) {
            if (nameOfGenre.equals(value.getName())) {
                genre = value;
            }
        }
        if (genre == null) {
            System.out.println("Извините произошла ошибка в программе");
            return;
        }


        newAuthor.addBookToList(new Book(nameBook.toString(), descriptionBook.toString(), yearOfRelease, newAuthor, genre));
        nextLine = br.readLine();
        if (nextLine.equals(",")) {
            return;
        }
        String[] checkNextLine = nextLine.split(" ");
        if (checkNextLine[0].equals("Книга:")) {
            thisMethod(nextLine, br, genres, newAuthor);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ЗАПИСЬ КНИГ ИЗ ФАЙЛА////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void readBooksFromFile(String nextLine, BufferedReader br, List<Author> authors, List<Genre> genres, List<Book> newBooks) throws IOException {
        nextLine = nextLine.replaceAll("Книга: Название= ", "");

        nextLine = nextLine.replaceAll(",", "");
        nextLine = nextLine.replaceAll("]", "");


        String[] arr2 = nextLine.split(" ");
        StringBuilder nameBook = new StringBuilder();

        String source;
        int index = 0;
        while (!arr2[index].equals("Описание=")) {
            source = arr2[index];
            if (nameBook.toString().equals("")) {
                nameBook.append(source);
            } else {
                nameBook.append(" ").append(source);
            }

            index++;

        }

        StringBuilder descriptionBook = new StringBuilder();
        for (int i = index + 1; i < arr2.length; i++) {

            if (descriptionBook.toString().equals("")) {
                descriptionBook.append(arr2[i]);
            } else {
                descriptionBook.append(" ").append(arr2[i]);
            }
        }

        String str2;
        str2 = br.readLine();
        str2 = str2.replaceAll(",", "");
        str2 = str2.replaceAll("]", "");
        arr2 = str2.split(" ");
        int yearOfRelease;
        try {
            yearOfRelease = Integer.parseInt(arr2[2]);
        } catch (Exception e) {
            System.out.println("Exception " + e);
            return;
        }
        Author author = null;
        String authorName = arr2[6];
        String authorSurname = arr2[7];
        for (Author value : authors) {
            if (authorName.equals(value.getName()) & authorSurname.equals(value.getSurname())) {
                author = value;
            }
        }
        Genre genre = null;
        String nameOfGenre = arr2[4];
        for (Genre value : genres) {
            if (nameOfGenre.equals(value.getName())) {
                genre = value;
            }
        }
        if (genre == null & author == null) {
            System.out.println("Извините произошла ошибка в программе");
            return;
        }


        newBooks.add(new Book(nameBook.toString(), descriptionBook.toString(), yearOfRelease, author, genre));

    }

    public static List<Book> recordAllBooksFromFileToList(List<Author> authors, List<Genre> genres) {
        List<Book> newBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileNameBooks))) {
            br.readLine();
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                readBooksFromFile(nextLine, br, authors, genres, newBooks);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newBooks;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ЗАПИСЬ ЖАНРОВ ИЗ КОЛЛЕКЦИИ//////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void recordAllGenresFromListToFile(List<Genre> genres) {
        PrintWriter pwG = null;
        try {
            pwG = new PrintWriter(fileNameGenres);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert pwG != null;
        pwG.println(genres);
        pwG.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ЗАПИСЬ АВТОРОВ ИЗ КОЛЛЕКЦИИ/////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void recordAllAuthorFromListToFile(List<Author> authors) {
        PrintWriter pwA = null;
        try {
            pwA = new PrintWriter(fileNameAuthors);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert pwA != null;
        pwA.println(authors);
        pwA.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ЗАПИСЬ КНИГ ИЗ КОЛЛЕКЦИИ/////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void recordAllBooksFromListToFile(List<Book> books) {
        PrintWriter pwB = null;
        try {
            pwB = new PrintWriter(fileNameBooks);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert pwB != null;
        pwB.println(books);
        pwB.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ВСПОМОГАТЕЛЬНЫЙ МЕТОД///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Author takeVariable(Scanner scanner, String name, String surname) {
        String variable = scanner.nextLine();
        switch (variable) {
            case ("Да"):
                return new Author(name, surname);
            case ("Нет"):
                break;
            default:
                System.out.println("Команда не найдена, введите Да или Нет");
                takeVariable(scanner, name, surname);
                break;
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ДОБАВИТЬ КНИГУ В КОЛЛЕКЦИЮ//////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Book addBookToCollection(List<Book> books, List<Genre> genres, List<Author> authors, Scanner scanner) {
        if (genres.size() == 0) {
            System.out.println("Извините но вы не можете добавить книгу, не добавив жанры");
            return null;
        }
        System.out.println("Введите название книги");
        String name = scanner.nextLine();
        System.out.println("Введите краткое описание книги");
        String description = scanner.nextLine();
        System.out.println("Введите год выпуска книги");
        String strDataOfRelease = scanner.nextLine();
        int yearOfReleaseBook;
        try {
            yearOfReleaseBook = Integer.parseInt(strDataOfRelease);
        } catch (Exception e) {
            System.out.println("Exception " + e);
            return null;
        }
        if (books.size() != 0) {
            for (Book value : books) {
                if (name.equals(value.getName()) & description.equals(value.getDescription()) & yearOfReleaseBook == value.getYearOfRelease()) {
                    System.out.println("Извините, но такая книга уже есть");
                    return null;
                }
            }
        }
        System.out.println("Введите имя автора");
        String authorName = scanner.nextLine();
        System.out.println("Введите фамилию автора");
        String authorSurname = scanner.nextLine();
        Author author = null;
        if (authors.size() == 0) {
            System.out.println("Извините, не может быть книги без автора");
            return null;
        }
        for (Author value : authors) {
            if (authorName.equals(value.getName())) {
                if (authorSurname.equals(value.getSurname())) {
                    System.out.println("Автор найден");
                    author = value;
                    break;
                }

            }


        }
        if (author == null) {
            System.out.println("Извините автор не найден. Хотите создать нового автора," + '\n' + "или Нет?");
            author = takeVariable(scanner, authorName, authorSurname);
            if (author == null) {
                System.out.println("Извините без автора нельзя создать книгу");
                return null;
            }
        }
        System.out.println("Введите название жанра");
        String nameGenre = scanner.nextLine();
        for (Genre value : genres) {
            if (nameGenre.equals(value.getName())) {

                Book book = new Book(name, description, yearOfReleaseBook, author, value);

                author.addBookToList(book);
                System.out.println("Книга добавлена");
                return book;
            }


        }
        System.out.println("Извините жанр не найден");
        return null;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ДОБАВИТЬ ЖАНР В КОЛЛЕКЦИЮ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Genre addGenreToCollection(List<Genre> genres, Scanner scanner) {
        System.out.println("Введите название Жанра");
        String name = scanner.nextLine();
        for (Genre value : genres) {
            if (name.equals(value.getName())) {
                System.out.println("Извините но такой жанр уже есть");
            }
        }
        System.out.println("Введите краткое описание Жанра");
        String description = scanner.nextLine();
        System.out.println("Жанр добавлен");
        return new Genre(name, description);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////ДОБАВИТЬ АВТОРА В КОЛЛЕКЦИЮ/////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Author addAuthorToCollection(List<Author> authors, Scanner scanner) {
        System.out.println("Введите имя автора");
        String name = scanner.nextLine();
        System.out.println("Введите Фамилию автора");
        String surname = scanner.nextLine();
        for (Author value : authors) {
            if (name.equals(value.getName())) {
                System.out.println("Извините но такой автор уже есть");
            }
        }
        System.out.println("Автор добавлен");
        return new Author(name, surname);
    }

    ///////////////////////////////////////////       ВЫВЕСТИ       //////////////////////////////////////////////////////////////
    public static void withdrawAllGenres(List<Genre> genres) {
        System.out.println(genres);
    }

    public static void withdrawAllBooks(List<Book> books) {
        System.out.println(books);
    }

    public static void withdrawAllAuthors(List<Author> authors) {
        System.out.println(authors);
    }

    ///////////////////////////////////////////       УДАЛЕНИЕ       //////////////////////////////////////////////////////////////
    public static void removeGenreFromCollection(List<Genre> genres, Scanner scanner) {
        System.out.println("Введите название жанра");
        String name = scanner.nextLine();
        for (int i = 0; i < genres.size(); i++) {
            if (name.equals(genres.get(i).getName())) {
                genres.remove(i);
                System.out.println("Успешно удалено");
                return;
            }
        }
        System.out.println("Извините жанр не найдет");
    }

    public static void removeBookFromCollection(List<Book> books, Scanner scanner) {
        System.out.println("Введите название книги");
        String name = scanner.nextLine();
        for (int i = 0; i < books.size(); i++) {
            if (name.equals(books.get(i).getName())) {
                books.remove(i);
                System.out.println("Успешно удалено");
                return;
            }
        }
        System.out.println("Извините жанр не найдет");
    }

    public static void removeAuthorFromCollection(List<Author> authors, Scanner scanner) {
        System.out.println("Введите имя автора");
        String authorName = scanner.nextLine();
        System.out.println("Введите фамилию автора");
        String authorSurname = scanner.nextLine();
        for (Author value : authors) {
            if (authorName.equals(value.getName())) {
                if (authorSurname.equals(value.getSurname())) {
                    System.out.println("Автор найден");
                    System.out.println("Удалено");
                    authors.remove(value);
                    return;
                }
            } else {
                System.out.println("Автор не найден");
                return;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////УМНЫЙ ПРОГРАМА//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void cleverProgram(Scanner scanner, List<Genre> genres, List<Author> authors, List<Book> books) {
        while (true) {

            String variable = scanner.nextLine();
            if (variable.equals("exit")) {
                System.out.println("Программа остановлена");
                return;
            }
            switch (variable) {
                case ("1"):
                case ("Добавить жанр"):
                    genres.add(addGenreToCollection(genres, scanner));
                    break;
                case ("2"):
                case ("Добавить автора"):
                    authors.add(addAuthorToCollection(authors, scanner));
                    break;
                case ("3"):
                case ("Добавить книгу"):
                    books.add(addBookToCollection(books, genres, authors, scanner));
                    break;
                case ("4"):
                case ("Удалить жанр"):
                    removeGenreFromCollection(genres, scanner);
                    break;
                case ("5"):
                case ("Удалить автора"):
                    removeAuthorFromCollection(authors, scanner);
                    break;
                case ("6"):
                case ("Удалить книгу"):
                    removeBookFromCollection(books, scanner);
                    break;
                case ("7"):
                case ("Вывести все жанры"):
                    withdrawAllGenres(genres);
                    break;
                case ("8"):
                case ("Вывести всех авторов"):
                    withdrawAllAuthors(authors);
                    break;
                case ("9"):
                case ("Вывести все книги"):
                    withdrawAllBooks(books);
                    break;
                default:
                    System.out.println("Команда не найдена");
                    break;
            }
        }
    }
}