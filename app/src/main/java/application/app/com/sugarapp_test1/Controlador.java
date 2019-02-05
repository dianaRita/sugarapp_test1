package application.app.com.sugarapp_test1;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

/**
 * Controla la vista de la aplicación.
 * Es la encargada de asignar los eventos y realizar la peticiones de las operaciones,
 * además de formatear los resultados para mostrarlos al usuario.
 */
public class Controlador {

    private MainActivity m;
    private Operador op;
    private Handler hndlr;

    public Controlador(MainActivity m) {
        this.m = m;
        this.op = new Operador((ActivityManager) m.getSystemService(Context.ACTIVITY_SERVICE));
        asignaEvento(m.getBtnIns());
        asignaEvento(m.getBtnCons());
        asignaEvento(m.getBtnAct());
        asignaEvento(m.getBtnElim());
        this.hndlr = new Handler();
    }

    // < EVENTOS >

    /**
     * Asigna los eventos a cada botón según su texto.
     * Recibe un botón como parámetro.
     * @param btn {@link Button} botón al que se le asignará el evento.
     */
    private void asignaEvento(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().equals("Insertar")) {
                    if ( verificaNum( m.gettCant().getText().toString() ) ) {
                        estado(true);
                        hiloInserta( m.getSwtch().isChecked() );
                    }
                }
                if (btn.getText().equals("Consultar")) {
                    if ( op.getCant() > 0 ){
                        estado(true);
                        hiloConsulta();
                    } else {
                        muestraResult("DEF", null);
                    }
                }
                if (btn.getText().equals("Actualizar")) {
                    if ( op.getCant() > 0 ){
                        estado(true);
                        hiloActualiza( m.getSwtch().isChecked() );
                    } else {
                        muestraResult("DEF", null);
                    }
                }
                if (btn.getText().equals("Eliminar")) {
                    if ( op.getCant() > 0 ){
                        estado(true);
                        hiloElimina();
                    } else {
                        muestraResult("DEF", null);
                    }
                }
            }
        });
    }

    // </ EVENTOS >

    // < CONTROL DE ESTADOS > //

    /***
     * Modifica el estado de edici&oacute;n de la app.
     * Necesita un par&aacute;metro de tipo boolean para determinar si se bloquea o no la app
     * @param esBloqueo {@link boolean} si es para bloquear = true, sino = false.
     */
    private void estado(boolean esBloqueo) {
        if (esBloqueo){
            m.getPrgrs().setVisibility(View.VISIBLE);
            activaControles(false);
        } else {
            m.getPrgrs().setVisibility(View.GONE);
            activaControles(true);
        }
    }

    /**
     * Activa o desactiva los botones según el parámetro booleano que reciba.
     * true = activados, false = desativados
     * @param a boolean
     */
    private void activaControles(boolean a) {
        m.getBtnIns().setEnabled(a);
        m.getBtnCons().setEnabled(a);
        m.getBtnAct().setEnabled(a);
        m.getBtnElim().setEnabled(a);
        m.gettCant().setEnabled(a);
        m.getSwtch().setEnabled(a);
        m.gettCnsl().setEnabled(a);
    }

    /**
     * Muestra el resultado de la operaci&oacute;n
     * en la vista. Recibe dos par&aacute;metos: la operaci&oacute;n y los resultados
     *
     * @param op { @link String } operaci&oacute;n que fue relizada. Pueden ser:
     *           INS, ACT, CON, ELI respectivamente para cada operaci&oacute;n
     * @param r  { @link String[] } Array de string que tiene los resultados de la operaci&oacute;n
     */
    private void muestraResult(String op, Map<String, String> r) {
        StringBuilder msg = new StringBuilder("");
        switch (op) {
            case "INS":
                msg.append("Inserción exitosa!").append("\n");
                msg.append("Ultima id insertada: ").append(r.get("ultId")).append("\n\n");
                msg.append("Duración del proceso: ").append(r.get("tiempo")).append("\n\n");
                msg.append("Memoria total del dispositivo:").append(r.get("memTotDevice")).append("\n");
                msg.append("Memoria ocupada en el dispositivo:").append(r.get("memUsadaDevice")).append("\n");
                msg.append("Porcentaje de memoria ocupada en el dispositivo:").append(r.get("porcMemUsadaDevice")).append("\n\n");
                msg.append("Memoria asignada a la app: ").append(r.get("memAsigApp")).append("\n");
                msg.append("Memoria utilizada por la app: ").append(r.get("memUsadaApp")).append("\n");
                msg.append("Porcentaje de memoria utilizada por la app: ").append(r.get("porcMemUsadaApp"));

                m.gettCnsl().setText(msg);
                break;
            case "CON":
                msg.append("Consulta exitosa!").append("\n");
                msg.append("Cantida de datos recuperados: ").append(r.get("cantCon")).append("\n\n");
                msg.append("Duración del proceso: ").append(r.get("tiempo")).append("\n\n");
                msg.append("Memoria total del dispositivo:").append(r.get("memTotDevice")).append("\n");
                msg.append("Memoria ocupada en el dispositivo:").append(r.get("memUsadaDevice")).append("\n");
                msg.append("Porcentaje de memoria ocupada en el dispositivo:").append(r.get("porcMemUsadaDevice")).append("\n\n");
                msg.append("Memoria asignada a la app: ").append(r.get("memAsigApp")).append("\n");
                msg.append("Memoria utilizada por la app: ").append(r.get("memUsadaApp")).append("\n");
                msg.append("Porcentaje de memoria utilizada por la app: ").append(r.get("porcMemUsadaApp")).append("\n\n");
                msg.append("Datos :\n").append(r.get("datos"));

                m.gettCnsl().setText(msg);
                break;
            case "ACT":
                msg.append("Actualización exitosa!").append("\n");
                msg.append("Ultima id actualizado: ").append(r.get("ultId")).append("\n\n");
                msg.append("Duración del proceso: ").append(r.get("tiempo")).append("\n\n");
                msg.append("Memoria total del dispositivo:").append(r.get("memTotDevice")).append("\n");
                msg.append("Memoria ocupada en el dispositivo:").append(r.get("memUsadaDevice")).append("\n");
                msg.append("Porcentaje de memoria ocupada en el dispositivo:").append(r.get("porcMemUsadaDevice")).append("\n\n");
                msg.append("Memoria asignada a la app: ").append(r.get("memAsigApp")).append("\n");
                msg.append("Memoria utilizada por la app: ").append(r.get("memUsadaApp")).append("\n");
                msg.append("Porcentaje de memoria utilizada por la app: ").append(r.get("porcMemUsadaApp"));

                m.gettCnsl().setText(msg);
                break;
            case "ELI":
                msg.append("Eliminación exitosa!").append("\n");
                msg.append("Cantidad de registros eliminados: ").append(r.get("cantElim")).append("\n\n");
                msg.append("Duración del proceso: ").append(r.get("tiempo")).append("\n\n");
                msg.append("Memoria total del dispositivo:").append(r.get("memTotDevice")).append("\n");
                msg.append("Memoria ocupada en el dispositivo:").append(r.get("memUsadaDevice")).append("\n");
                msg.append("Porcentaje de memoria ocupada en el dispositivo:").append(r.get("porcMemUsadaDevice")).append("\n\n");
                msg.append("Memoria asignada a la app: ").append(r.get("memAsigApp")).append("\n");
                msg.append("Memoria utilizada por la app: ").append(r.get("memUsadaApp")).append("\n");
                msg.append("Porcentaje de memoria utilizada por la app: ").append(r.get("porcMemUsadaApp"));

                m.gettCnsl().setText(msg);
                break;
            default:
                msg.append( "No hay registros para realizar la operación." );
                msg.append( "\nPrimero debe realizar inserciones" );
                m.gettCnsl().setText(msg);
                break;
        }
    }

    // </ CONTROL DE ESTADOS > //


    // < VERIFICACIÓN DE ENTRADA > //

    /**
     * Verifica que el valor ingresado por el usuario sea un n&uacute;mero mayor a cero.
     * Recibe un par&aacute;metro n de tipo string, que es el valor ingresado por el usuario.
     * Si el n&uacute;mero no es v&aacute;lido o es menor o igual a cero emite un mensaje al usuario.
     * @param n {@link String}
     * @return boolean verdadero si es un n&uacute;mero mayor a cero, false si no
     */
    private boolean verificaNum(String n) {

        if ( n.equals("") ){
            Toast.makeText(m.getApplicationContext(), "Ingrese una cantidad mayor a cero", Toast.LENGTH_LONG).show();
            return false;
        } else {
            int num = Integer.parseInt(n);
            if ( num > 0 )
                return true;
            else {
                Toast.makeText(m.getApplicationContext(), "Ingrese una cantidad mayor a cero", Toast.LENGTH_LONG).show();
                return false;
            }
        }

    }

    // < VERIFICACIÓN DE ENTRADA > //


    // < HILOS DE OPERACIONES > //

    /**
     * Crea nuevo hilo de ejecución paralela para realizar las inserciones.
     * Utiliza un escuchador de eventos para manejar cuando se finalice el proceso. y así
     * procesar los resultados. Recibe un parámetro booleano para determinar que valores
     * de datos utilizar para las inserciones, los valores pueden ser aleatorios o no
     * dependiendo del parámetro.
     * @param esAleatorio boolean
     */
    private void hiloInserta(final boolean esAleatorio) {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                Integer c = Integer.parseInt(m.gettCant().getText().toString());
                final Map<String, String> res = esAleatorio ? op.insertaAleatorio(c) : op.inserta(c);
                hndlr.post(new Runnable() {
                    @Override
                    public void run() {
                        estado(false);
                        muestraResult("INS", res);
                    }
                });
            }
        };
        hilo.start();
    }

    /**
     * Crea nuevo hilo de ejecución paralela para realizar las actualizaciones.
     * Utiliza un escuchador de eventos para manejar cuando se finalice el proceso. y así
     * procesar los resultados. Recibe un parámetro booleano para determinar que valores
     * de datos utilizar para las actualizaciones, los valores pueden ser aleatorios o no
     * dependiendo del parámetro.
     * @param esAleatorio boolean
     */
    private void hiloActualiza(final boolean esAleatorio) {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                final Map<String, String> res = esAleatorio ? op.actualizaAleatorio() : op.actualiza();
                hndlr.post(new Runnable() {
                    @Override
                    public void run() {
                        estado(false);
                        muestraResult("ACT", res);
                    }
                });
            }
        };
        hilo.start();
    }

    /**
     * Crea nuevo hilo de ejecución paralela para realizar las actualizaciones.
     * Utiliza un escuchador de eventos para manejar cuando se finalice el proceso. y así
     * procesar los resultados.
     */
    private void hiloConsulta() {
        Thread hilo = new Thread() {
            @Override
            public void run() {

                final Map<String, String> res = op.consulta();
                hndlr.post(new Runnable() {
                    @Override
                    public void run() {
                        estado(false);
                        muestraResult("CON", res);
                    }
                });
            }
        };
        hilo.start();
    }

    /**
     * Crea nuevo hilo de ejecución paralela para realizar las eliminaciones.
     * Utiliza un escuchador de eventos para manejar cuando se finalice el proceso. y así
     * procesar los resultados.
     */
    private void hiloElimina() {
        Thread hilo = new Thread() {
            @Override
            public void run() {

                final Map<String, String> res = op.elimina();
                hndlr.post(new Runnable() {
                    @Override
                    public void run() {
                        estado(false);
                        muestraResult("ELI", res);
                    }
                });
            }
        };
        hilo.start();
    }
    // < HILOS DE OPERACIONES > //
}
