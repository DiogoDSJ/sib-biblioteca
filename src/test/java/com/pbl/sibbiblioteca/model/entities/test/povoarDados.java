package com.pbl.sibbiblioteca.model.entities.test;

import com.pbl.sibbiblioteca.dao.DAO;
import com.pbl.sibbiblioteca.model.entities.Administrador;
import com.pbl.sibbiblioteca.model.entities.Bibliotecario;
import com.pbl.sibbiblioteca.model.entities.Leitor;
import com.pbl.sibbiblioteca.model.entities.Livro;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class povoarDados {
    @Test
    void povoarDadosPrograma(){
        DAO.getBibliotecarioDAO().deleteMany();
        DAO.getLeitorDAO().deleteMany();
        DAO.getAdministradorDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
        DAO.getEmprestimoDAO().deleteMany();
        DAO.getReservaDAO().deleteMany();
        DAO.getMultaDAO().deleteMany();

        DAO.getAdministradorDAO().create(new Administrador("John Doe","1234 Main Street","(123) 456-7890","jdoe123","ABCD1234"));
        DAO.getAdministradorDAO().create(new Administrador("Alice Smith","5678 Elm Street","(987) 654-3210","asmith567","EFGH5678"));
        DAO.getAdministradorDAO().create(new Administrador("Michael Johnson","9876 Oak Street","(555) 123-4567","mjohn876","IJKL9012"));
        DAO.getAdministradorDAO().create(new Administrador("Emily Williams","54321 Maple Avenue","(444) 789-0123","ewill5432","MNOP3456"));
        DAO.getAdministradorDAO().create(new Administrador("Daniel Brown","87654 Birch Lane","(222) 333-4444","dbrown876","QRST6789"));
        DAO.getAdministradorDAO().create(new Administrador("Mia King","23456 Strawberry Lane","(555) 444-3333","mking23456","FGHI5678"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Sophia Johnson","4567 Oak Street","(123) 456-7890","sjohn4567","ABCD1234"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Emma Williams","789 Elm Street","(987) 654-3210","ewill789","EFGH5678"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Liam Brown","9876 Pine Street","(555) 123-4567","lbrown9876","IJKL9012"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Olivia Davis","54321 Cedar Avenue","(444) 789-0123","odavis54321","MNOP3456"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Noah Smith","1234 Walnut Lane","(222) 333-4444","nsmith1234","QRST6789"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Ava Martinez","6543 Maple Road","(999) 888-7777","amart6543","UVWX1234"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("William Garcia","8765 Birch Drive","(777) 666-5555","wgar8765","YZAB5678"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Isabella Hernandez","23456 Pineapple Boulevard","(111) 222-3333","ihern23456","CDEF9012"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("James Lopez","87654 Cherry Avenue","(888) 777-6666","jlopez87654","GHIJ3456"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Charlotte Taylor","987 Lemon Road","(666) 555-4444","ctay987","KLMN6789"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Ethan Clark","7654 Kiwi Street","(333) 444-5555","eclark7654","OPQR1234"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Benjamin King","8765 Grape Avenue","(999) 888-7777","bking8765","WXYZ9012"));
        DAO.getBibliotecarioDAO().create(new Bibliotecario("Abigail Martinez","987 Cherry Lane","(777) 666-5555","amart987","BCDE1234"));
        DAO.getLeitorDAO().create(new Leitor("Liam Smith","123 Oak Street","(123) 456-7890","lsmith123","ABCD1234"));
        DAO.getLeitorDAO().create(new Leitor("Emma Johnson","456 Elm Street","(987) 654-3210","ejohn456","EFGH5678"));
        DAO.getLeitorDAO().create(new Leitor("Olivia Brown","789 Pine Street","(555) 123-4567","obrown789","IJKL9012"));
        DAO.getLeitorDAO().create(new Leitor("Noah Davis","9876 Cedar Avenue","(444) 789-0123","ndavis9876","MNOP3456"));
        DAO.getLeitorDAO().create(new Leitor("Ava Wilson","54321 Maple Road","(222) 333-4444","awil54321","QRST6789"));
        DAO.getLeitorDAO().create(new Leitor("William Martinez","8765 Walnut Lane","(999) 888-7777","wmart8765","UVWX1234"));
        DAO.getLeitorDAO().create(new Leitor("Isabella Taylor","23456 Birch Drive","(777) 666-5555","itay23456","YZAB5678"));
        DAO.getLeitorDAO().create(new Leitor("Mia Garcia","6543 Pineapple Boulevard","(111) 222-3333","mgarc6543","CDEF9012"));
        DAO.getLeitorDAO().create(new Leitor("James Hernandez","87654 Cherry Avenue","(888) 777-6666","jhern87654","GHIJ3456"));
        DAO.getLeitorDAO().create(new Leitor("Sophia Lopez","987 Lemon Road","(666) 555-4444","slopez987","KLMN6789"));
        DAO.getLeitorDAO().create(new Leitor("Benjamin Clark","7654 Kiwi Street","(333) 444-5555","bclark7654","OPQR1234"));
        DAO.getLeitorDAO().create(new Leitor("Amelia Adams","4321 Strawberry Lane","(222) 333-4444","aadam4321","STUV5678"));
        DAO.getLeitorDAO().create(new Leitor("Lucas King","8765 Grape Avenue","(999) 888-7777","lking8765","WXYZ9012"));
        DAO.getLeitorDAO().create(new Leitor("Charlotte Martinez","987 Cherry Lane","(777) 666-5555","cmart987","BCDE1234"));
        DAO.getLeitorDAO().create(new Leitor("Ethan Robinson","7654 Pine Road","(555) 444-3333","erob7654","FGHI5678"));
        DAO.getLeitorDAO().create(new Leitor("Harper White","3456 Lemon Boulevard","(444) 333-2222","hwhite3456","JKLM1234"));
        DAO.getLeitorDAO().create(new Leitor("Avery Scott","2345 Apple Street","(111) 222-3333","ascott2345","NOPQ5678"));
        DAO.getLeitorDAO().create(new Leitor("Ella Hall","6543 Peach Avenue","(999) 888-7777","ehall6543","RSTU9012"));
        DAO.getLeitorDAO().create(new Leitor("Oliver Lee","5432 Orange Lane","(777) 666-5555","olee5432","VWXY3456"));
        DAO.getLeitorDAO().create(new Leitor("Sophia Carter","9876 Mango Road","(555) 444-3333","scart9876","ZABC7890"));
        DAO.getLeitorDAO().create(new Leitor("Noah Perez","8765 Banana Boulevard","(333) 222-1111","nperez8765","CDEF2345"));
        DAO.getLeitorDAO().create(new Leitor("Ava Nguyen","7654 Grapefruit Street","(222) 333-4444","anguyen7654","GHIJ6789"));
        DAO.getLeitorDAO().create(new Leitor("James Rodriguez","6543 Watermelon Avenue","(999) 888-7777","jrodr6543","KLMN0123"));
        DAO.getLeitorDAO().create(new Leitor("Emma Martinez","5432 Cantaloupe Lane","(777) 666-5555","emart5432","OPQR3456"));
        DAO.getLeitorDAO().create(new Leitor("William Rivera","4321 Honeydew Road","(555) 444-3333","wrive4321","STUV6789"));
        DAO.getLeitorDAO().create(new Leitor("Sophia Chavez","8765 Blueberry Boulevard","(333) 222-1111","schavez8765","WXYZ0123"));
        DAO.getLeitorDAO().create(new Leitor("Mia Perez","7654 Raspberry Street","(222) 333-4444","mperez7654","ABCDE2345"));
        DAO.getLeitorDAO().create(new Leitor("Elijah Stewart","6543 Blackberry Avenue","(999) 888-7777","estew6543","FGHI6789"));
        DAO.getLeitorDAO().create(new Leitor("Isabella Ramirez","5432 Cranberry Lane","(777) 666-5555","iramirez5432","JKLM0123"));
        DAO.getLeitorDAO().create(new Leitor("James Smith","123 Oak Street","(123) 456-7890","jsmith123","ABCD1234"));
        DAO.getLeitorDAO().create(new Leitor("Olivia Johnson","456 Elm Street","(987) 654-3210","ojohn456","EFGH5678"));
        DAO.getLeitorDAO().create(new Leitor("Noah Brown","789 Pine Street","(555) 123-4567","nbrown789","IJKL9012"));
        DAO.getLeitorDAO().create(new Leitor("Ava Davis","9876 Cedar Avenue","(444) 789-0123","adavis9876","MNOP3456"));
        DAO.getLeitorDAO().create(new Leitor("William Wilson","54321 Maple Road","(222) 333-4444","wwil54321","QRST6789"));
        DAO.getLeitorDAO().create(new Leitor("Isabella Martinez","8765 Walnut Lane","(999) 888-7777","imart8765","UVWX1234"));
        DAO.getLeitorDAO().create(new Leitor("Mia Taylor","23456 Birch Drive","(777) 666-5555","mtay23456","YZAB5678"));
        DAO.getLeitorDAO().create(new Leitor("James Garcia","6543 Pineapple Boulevard","(111) 222-3333","jgarc6543","CDEF9012"));
        DAO.getLeitorDAO().create(new Leitor("Sophia Hernandez","87654 Cherry Avenue","(888) 777-6666","shern87654","GHIJ3456"));
        DAO.getLeitorDAO().create(new Leitor("Benjamin Lopez","987 Lemon Road","(666) 555-4444","blopez987","ASD1312"));
        DAO.getLivroDAO().create(new Livro("9780141036144", "Haruki Murakami", "1Q84", "Vintage", "Ficção", "2011"));
        DAO.getLivroDAO().create(new Livro("9780061120084", "George Orwell", "1984", "Signet Classic", "Ficção", "1949"));
        DAO.getLivroDAO().create(new Livro("9781400079987", "Khaled Hosseini", "The Kite Runner", "Riverhead Books", "Ficção", "2003"));
        DAO.getLivroDAO().create(new Livro("9780143105985", "J.D. Salinger", "The Catcher in the Rye", "Back Bay Books", "Ficção", "1951"));
        DAO.getLivroDAO().create(new Livro("9780385517255", "Dan Brown", "The Da Vinci Code", "Doubleday", "Ficção", "2003"));
        DAO.getLivroDAO().create(new Livro("9780307588364", "Stieg Larsson", "The Girl with the Dragon Tattoo", "Vintage Crime/Black Lizard", "Ficção", "2005"));
        DAO.getLivroDAO().create(new Livro("9780385537857", "Gillian Flynn", "Gone Girl", "Broadway Books", "Ficção", "2012"));
        DAO.getLivroDAO().create(new Livro("9780743273565", "Stephenie Meyer", "Twilight", "Little, Brown and Company", "Ficção", "2005"));
        DAO.getLivroDAO().create(new Livro("9780671027346", "J.R.R. Tolkien", "The Lord of the Rings", "Houghton Mifflin Harcourt", "Ficção", "1954"));
        DAO.getLivroDAO().create(new Livro("9780679745587", "Gabriel Garcia Marquez", "One Hundred Years of Solitude", "Harper Perennial Modern Classics", "Ficção", "1967"));
        DAO.getLivroDAO().create(new Livro("9780143127550", "Ray Bradbury", "Fahrenheit 451", "Simon & Schuster", "Ficção", "1953"));
        DAO.getLivroDAO().create(new Livro("9780307352149", "Aldous Huxley", "Brave New World", "Harper Perennial Modern Classics", "Ficção", "1932"));
        DAO.getLivroDAO().create(new Livro("9780375403170", "Markus Zusak", "The Book Thief", "Knopf Books for Young Readers", "Ficção", "2005"));
        DAO.getLivroDAO().create(new Livro("9780679755333", "Albert Camus", "The Stranger", "Vintage International", "Ficção", "1942"));
        DAO.getLivroDAO().create(new Livro("9780142437209", "Jane Austen", "Pride and Prejudice", "Penguin Classics", "Ficção", "1813"));
        DAO.getLivroDAO().create(new Livro("9781400033416", "Toni Morrison", "Beloved", "Vintage", "Ficção", "1987"));
        DAO.getLivroDAO().create(new Livro("9780452284244", "J.K. Rowling", "Harry Potter and the Sorcerer's Stone", "Scholastic", "Ficção", "1997"));
        DAO.getLivroDAO().create(new Livro("9780061122415", "Harper Lee", "To Kill a Mockingbird", "Harper Perennial Modern Classics", "Ficção", "1960"));
        DAO.getLivroDAO().create(new Livro("9780385492084", "Philip K. Dick", "Do Androids Dream of Electric Sheep?", "Del Rey Books", "Ficção", "1968"));
        DAO.getLivroDAO().create(new Livro("9780767908184", "Malcolm Gladwell", "Blink: The Power of Thinking Without Thinking", "Back Bay Books", "Não Ficção", "2005"));
        DAO.getLivroDAO().create(new Livro("9780345803527", "Yuval Noah Harari", "Sapiens: A Brief History of Humankind", "Harper Perennial", "Não Ficção", "2011"));
        DAO.getLivroDAO().create(new Livro("9780061234004", "Stephen Hawking", "A Brief History of Time", "Bantam Books", "Não Ficção", "1988"));
        DAO.getLivroDAO().create(new Livro("9780140449297", "Plato", "The Republic", "Penguin Classics", "Não Ficção", "380 BC"));
        DAO.getLivroDAO().create(new Livro("9780679723011", "Franz Kafka", "The Metamorphosis", "Schocken", "Ficção", "1915"));
        DAO.getLivroDAO().create(new Livro("9780679643242", "Fyodor Dostoevsky", "Crime and Punishment", "Vintage Classics", "Ficção", "1866"));
        DAO.getLivroDAO().create(new Livro("9781594480003", "Donna Tartt", "The Secret History", "Vintage", "Ficção", "1992"));
        DAO.getLivroDAO().create(new Livro("9780525474932", "Jonathan Safran Foer", "Extremely Loud and Incredibly Close", "Mariner Books", "Ficção", "2005"));
        DAO.getLivroDAO().create(new Livro("9780679406419", "Margaret Atwood", "The Handmaid's Tale", "Anchor", "Ficção", "1985"));
        DAO.getLivroDAO().create(new Livro("9780670020553", "Paulo Coelho", "The Alchemist", "HarperOne", "Ficção", "1988"));
        DAO.getLivroDAO().create(new Livro("9780140185215", "Gustave Flaubert", "Madame Bovary", "Penguin Classics", "Ficção", "1856"));
        DAO.getLivroDAO().create(new Livro("9780143105428", "William Golding", "Lord of the Flies", "Penguin Books", "Ficção", "1954"));
        DAO.getLivroDAO().create(new Livro("9780316015844", "David Foster Wallace", "Infinite Jest", "Back Bay Books", "Ficção", "1996"));
        DAO.getLivroDAO().create(new Livro("9780544003415", "J.R.R. Tolkien", "The Hobbit", "Mariner Books", "Ficção", "1937"));
        DAO.getLivroDAO().create(new Livro("9780132350884", "John Smith", "The Art of Programming", "Tech Publishing", "Technology", "2019"));
        DAO.getLivroDAO().create(new Livro("9781451648546", "David Johnson", "The Power of Habit", "Random House", "Self-Help", "2012"));
        DAO.getLivroDAO().create(new Livro("9780679732761", "Michael Thompson", "Thinking, Fast and Slow", "Farrar, Straus and Giroux", "Psychology", "2011"));
        DAO.getLivroDAO().create(new Livro("9781982103454", "Jessica Lee", "Atomic Habits", "Avery", "Self-Improvement", "2018"));
        DAO.getLivroDAO().create(new Livro("9780399590504", "Daniel Wilson", "Robopocalypse", "Doubleday", "Science Fiction", "2011"));
        DAO.getLivroDAO().create(new Livro("9781982110568", "Jennifer Taylor", "Educated", "Random House", "Biography", "2018"));
        DAO.getLivroDAO().create(new Livro("9780316769488", "Kevin Garcia", "To Kill a Mockingbird", "J.B. Lippincott & Co.", "Classic", "1960"));
        DAO.getLivroDAO().create(new Livro("9780307592736", "Michelle Martinez", "The Night Circus", "Doubleday", "Fantasy", "2011"));
        DAO.getLivroDAO().create(new Livro("9780307743657", "Andrew Scott", "Sapiens: A Brief History of Humankind", "Harper", "History", "2011"));
        DAO.getLivroDAO().create(new Livro("9781476770383", "Lisa Nguyen", "The Girl on the Train", "Riverhead Books", "Mystery", "2015"));
        DAO.getLivroDAO().create(new Livro("9781451621716", "Matthew Hall", "Steve Jobs", "Simon & Schuster", "Biography", "2011"));
        DAO.getLivroDAO().create(new Livro("9780307476463", "Amanda Thompson", "The Help", "Putnam Adult", "Historical Fiction", "2009"));
        /* Caso queira testar um empréstimo vencido ou reserva vencido, siga essas instruções, crie o empréstimo usando
        uma conta de bibliotecário/administrador e após isso, logue na conta do Leitor, olhe o id do empréstimo/reserva
        e substitua embaixo. Lembrando que as reservas vencidas são apagadas ao início do programa.
         */
        //DAO.getEmprestimoDAO().findByPk("IdEmprestimo").setDataFim(LocalDate.now().minusDays(int DiasParaSubtrair));
        //DAO.getReservaDAO().findByPk("idReserva").setDataFimReserva(LocalDate.now().minusDays(int DiasParaSubtrair));
    }
}
