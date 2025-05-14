module p_henriqued.javafxstudy {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires mysql.connector.j;


    opens p_henriqued.javafxstudy to javafx.fxml;
    opens p_henriqued.javafxstudy.Controller to javafx.fxml;
    opens p_henriqued.javafxstudy.Controller.DepartmentController to javafx.fxml;
    opens p_henriqued.javafxstudy.models.Department to org.hibernate.orm.core;
    opens p_henriqued.javafxstudy.listeners to javafx.fxml;

    exports p_henriqued.javafxstudy;
    exports p_henriqued.javafxstudy.Controller;
    exports p_henriqued.javafxstudy.Controller.DepartmentController;
    exports p_henriqued.javafxstudy.models.Department;
    exports p_henriqued.javafxstudy.listeners;
}