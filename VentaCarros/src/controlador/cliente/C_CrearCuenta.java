package controlador.cliente;

import com.github.fxrouter.FXRouter;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

public class C_CrearCuenta {

    @FXML StackPane st_dialogs;

    @FXML JFXButton btn_proceder;
    @FXML JFXButton btn_regresar;

    @FXML JFXTextField tf_nombre;
    @FXML JFXTextField tf_apellidos;
    @FXML JFXDatePicker tf_fecha_nacimiento;
    @FXML JFXTextField tf_cedula;
    @FXML JFXTextField tf_telefono;
    @FXML JFXTextField tf_zip_code;
    @FXML JFXTextField tf_correo;
    @FXML JFXPasswordField tf_contrasenia;

    public void initialize() throws Exception {
        initComponentes();
    }

    // Inicializar las referecias de los handlers de los componentes de la UI
    private void initComponentes() throws Exception {
        btn_proceder.setOnAction(this::handle_btn_proceder);
        btn_regresar.setOnAction(this::handle_btn_regresar);
    }

    private void handle_btn_proceder(ActionEvent event) {
        if (!tf_nombre.getText().trim().equals("") && !tf_apellidos.getText().trim().equals("") &&
            tf_fecha_nacimiento.getValue() != null  && !tf_cedula.getText().trim().equals("") &&
            !tf_telefono.getText().trim().equals("") && !tf_zip_code.getText().trim().equals("") &&
            !tf_correo.getText().trim().equals("") && !tf_contrasenia.getText().trim().equals("")) {

            LocalDate localDate = tf_fecha_nacimiento.getValue();
            System.out.println(localDate);
        }
        else {
            mostrarMensaje("Datos incompletos", "Por favor, todos los campos son requeridos para el registro!");
        }
    }

    private void handle_btn_regresar(ActionEvent event) {
        try {
            FXRouter.goTo("InicioSesion");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String encabezado, String cuerpo) {
        JFXDialogLayout content= new JFXDialogLayout();
        content.setHeading(new Text(encabezado));
        content.setBody(new Text(cuerpo));
        JFXDialog dialog =new JFXDialog(st_dialogs, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Cerrar");
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }
}