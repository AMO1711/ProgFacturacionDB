module ProgramaFacturacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;

    // Abrimos los paquetes para que los frameworks (JavaFX e Hibernate) puedan trabajar
    opens controlador to javafx.fxml;
    // Abrimos el nuevo paquete a JavaFX para que pueda leer los @FXML
    opens modelo.view_controllers to javafx.fxml;
    opens modelo to org.hibernate.orm.core;
    opens persistencia to org.hibernate.orm.core; // Para que Hibernate gestione los DAOs si fuera necesario

    exports controlador;
    exports modelo;
    exports modelo.view_controllers;
    exports persistencia;
    exports util;
}