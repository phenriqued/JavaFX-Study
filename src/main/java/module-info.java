module p_henriqued.javafxstudy {
    requires javafx.controls;
    requires javafx.fxml;


    opens p_henriqued.javafxstudy to javafx.fxml;
    exports p_henriqued.javafxstudy;
    exports p_henriqued.javafxstudy.Controller;
    opens p_henriqued.javafxstudy.Controller to javafx.fxml;
}