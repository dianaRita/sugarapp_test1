package application.app.com.sugarapp_test1;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

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

    public void asignaEvento(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().equals("Insertar")) {
                    if ( !m.gettCant().getText().toString().equals("")
                            || (Integer.parseInt(m.gettCant().getText().toString()) <= 0) ) {
                        estado("INS");
                        hiloInserta(m.getSwtch().isChecked());
                    } else {
                        Toast.makeText(m.getApplicationContext(), "Ingrese una cantidad mayor a cero", Toast.LENGTH_LONG).show();
                    }
                }
                if (btn.getText().equals("Consultar")) {

                    String[] r = op.consultar();
                    m.gettCnsl().setText(r[4]);
                    Log.e("Mensaje", ">>>>> " + r[0] + " " + r[1] + " " + r[2] + " " + r[3]);
                }
                if (btn.getText().equals("Actualizar")) {
                    Toast.makeText(m.getApplicationContext(), "Actualizar", Toast.LENGTH_LONG).show();
                    String[] r = op.actualizar();
                    m.gettCnsl().setText(r[4]);
                    Log.e("Mensaje", ">>>>> " + r[0] + " " + r[1] + " " + r[2] + " " + r[3] + " " + r[4]);
                }
                if (btn.getText().equals("Eliminar")) {
                    Toast.makeText(m.getApplicationContext(), "Eliminar", Toast.LENGTH_LONG).show();
                    String[] r = op.eliminar();
                    Log.e("Mensaje", ">>>>> " + r[0] + " " + r[1] + " " + r[2] + " " + r[3] + " " + r[4]);
                }
            }
        });
    }

    // < CONTROL DE ESTADOS > //

    /***
     * Modifica el estado de edici&oacute;n de la app.
     * Necesita un parametro de tipo String los cuales pueden ser
     * INS, CON, ACT, ELI, NUE.
     * @param e {@link String} estado.
     * @author DianaNu&ntilde;ez
     */
    private void estado(String e) {
        switch (e) {
            case "INS":
                m.getPrgrs().setVisibility(View.VISIBLE);
                activaControles(false);
                break;
            case "CON":

                break;
            case "ACT":

                break;
            case "ELI":

                break;
            case "NUE":

                break;
            default:
                m.getPrgrs().setVisibility(View.GONE);
                activaControles(true);
                break;
        }
    }

    private void activaControles(boolean a) {
        m.getBtnIns().setEnabled(a);
        m.getBtnCons().setEnabled(a);
        m.getBtnAct().setEnabled(a);
        m.getBtnElim().setEnabled(a);
        m.gettCant().setEnabled(a);
        m.getSwtch().setEnabled(a);
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
        switch (op) {
            case "INS":
                StringBuilder msg = new StringBuilder();
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

                break;
            case "ACT":

                break;
            case "ELI":

                break;
            case "NUE":

                break;
            default:

                break;
        }
    }

    // </ CONTROL DE ESTADOS > //


    // < HILOS DE OPERACIONES > //

    /**
     * Crea nuevo hilo e inserta con datos aleatorios
     */
    private void hiloInserta(final boolean esAleatorio) {
        Thread insExec = new Thread() {
            @Override
            public void run() {
                Integer c = new Integer(Integer.parseInt(m.gettCant().getText().toString()));
                final Map<String, String> res = esAleatorio ? op.insertarAleatorio(c) : op.insertar(c);
                hndlr.post(new Runnable() {
                    @Override
                    public void run() {
                        estado("fin");
                        muestraResult("INS", res);
                    }
                });
            }
        };
        insExec.start();
    }

    // < HILOS DE OPERACIONES > //
}
