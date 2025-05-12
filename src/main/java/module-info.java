module p_henriqued.javafxstudy {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens p_henriqued.javafxstudy to javafx.fxml;
    exports p_henriqued.javafxstudy;
    exports p_henriqued.javafxstudy.Controller;
    exports p_henriqued.javafxstudy.Controller.DepartmentController;
    opens p_henriqued.javafxstudy.Controller to javafx.fxml;
    opens p_henriqued.javafxstudy.Controller.DepartmentController to javafx.fxml;
}