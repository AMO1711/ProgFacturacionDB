module ProgramaFacturacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming; // <--- ESTA ES LA LÍNEA QUE SOLUCIONA TU ERROR

    // Abrimos los paquetes para que los frameworks (JavaFX e Hibernate) puedan trabajar
    opens controlador to javafx.fxml;
    opens modelo to org.hibernate.orm.core;
    opens persistencia to org.hibernate.orm.core; // Para que Hibernate gestione los DAOs si fuera necesario

    exports controlador;
    exports modelo;
    exports persistencia;
    exports util;
}