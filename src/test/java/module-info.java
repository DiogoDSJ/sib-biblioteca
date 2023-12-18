module com.pbl.sibbiblioteca.model.entities {
    requires org.junit.jupiter.api;
    requires com.pbl.sibblioteca;
    opens com.pbl.sibbiblioteca.model.entities.test to org.junit.platform.commons;
}