import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

public class Main {
    public static void main(String[] args) {
        Book book = Book.of(
            new String[] {
                "0",
                "To Kill a Mockingbird",
                "Harper Lee",
                """
                    The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it.
                    \"To Kill A Mockingbird\" became both an instant bestseller and a critical success when it was first published in 1960.
                    It went on to win the Pulitzer Prize in 1961 and was later made into an Academy Award-winning film, also a classic.
                    Compassionate, dramatic, and deeply moving, \"To Kill A Mockingbird\" takes readers to the roots of human behavior -
                    to innocence and experience, kindness and cruelty, love and hatred, humor and pathos.
                    Now with over 18 million copies in print and translated into forty languages, this regional story by a young Alabama woman claims universal appeal.
                    Harper Lee always considered her book to be a simple love story. Today it is regarded as a masterpiece of American literature.
                    """,
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
                "4.27",
                "5691311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
            }
        );

        System.out.println(
            book
        );
    }
}
