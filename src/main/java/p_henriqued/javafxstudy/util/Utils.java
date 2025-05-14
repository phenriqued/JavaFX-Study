package p_henriqued.javafxstudy.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Utils {

    public static Stage currentStage(ActionEvent actionEvent){
        return (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    public static Long tryParseToLong(String id){
        try{
           return Long.parseLong(id);
        }catch (NumberFormatException exception){
            return null;
        }
    }

    public static <T> void formatTableColumnDate(TableColumn<T, LocalDate> tableColumn, String format) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, LocalDate> cell = new TableCell<T, LocalDate>() {
                private SimpleDateFormat sdf = new SimpleDateFormat(format);
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(sdf.format(item));
                    }
                }
            };
            return cell;
        });
    }
    public static <T> void formatTableColumnDouble(TableColumn<T, BigDecimal> tableColumn, int decimalPlaces) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, BigDecimal> cell = new TableCell<T, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        Locale.setDefault(Locale.US);
                        setText(String.format("%."+decimalPlaces+"f", item));
                    }
                }
            };
            return cell;
        });
    }

}
