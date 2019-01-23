package application.app.com.sugarapp_test1;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Controlador {

    private MainActivity m;
    private Operador op;
    private Handler hndlr;
    private String[] resp;

    public Controlador( MainActivity m ) {
        this.m = m;
        this.op = new Operador((ActivityManager) m.getSystemService(Context.ACTIVITY_SERVICE));
        asignaEvento( m.getBtnIns() ); asignaEvento( m.getBtnCons() );
        asignaEvento( m.getBtnAct() ); asignaEvento( m.getBtnElim() );
        this.hndlr= new Handler();
    }

    public void asignaEvento( final Button btn ){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().equals("Insertar")){
                    if(!m.gettCant().getText().toString().equals("")){
                        estado("INS",null);
                        hiloInserta(m.getSwtch().isChecked());
                    } else {
                        Toast.makeText(m.getApplicationContext(),"Ingrese la cantidad", Toast.LENGTH_LONG).show();
                    }
                }
                if (btn.getText().equals("Consultar")){

                    String[] r = op.consultar();
                    m.gettCnsl().setText( r[4] );
                    Log.e("Mensaje", ">>>>> " + r[0] + " " + r[1] + " " + r[2] + " " + r[3]);
                }
                if (btn.getText().equals("Actualizar")){
                    Toast.makeText( m.getApplicationContext(), "Actualizar",Toast.LENGTH_LONG ).show();
                    String[] r = op.actualizar();
                    m.gettCnsl().setText( r[4] );
                    Log.e("Mensaje", ">>>>> " + r[0] + " " + r[1] + " " + r[2] + " " + r[3] + " " + r[4]);
                }
                if (btn.getText().equals("Eliminar")){
                    Toast.makeText( m.getApplicationContext(), "Eliminar",Toast.LENGTH_LONG ).show();
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
     * @param r {@link String[]} respuesta de la operaci&oacute;n.
     * @author DianaNu&ntilde;ez
     */
    private void estado(String e, String[] r){
        switch (e){
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
                m.gettMem().setText( r[3].toString() );
                activaControles(true);
                break;
        }
    }

    private void activaControles(boolean a){
        m.getBtnIns().setEnabled(a);
        m.getBtnCons().setEnabled(a);
        m.getBtnAct().setEnabled(a);
        m.getBtnElim().setEnabled(a);
        m.gettCant().setEnabled(a);
        m.getSwtch().setEnabled(a);
    }

    // </ CONTROL DE ESTADOS > //


    // < HILOS DE OPERACIONES > //

    /**
     * Crea nuevo hilo e inserta con datos aleatorios
     */
    private void hiloInserta(final boolean esAleatorio){
        Thread insExec = new Thread() {
            @Override
            public void run() {
                Integer c = new Integer( Integer.parseInt( m.gettCant().getText().toString() ) );
                resp = op.insertar(c);
                Log.e("Mensaje", ">>>>> " + resp[0] + " " + resp[1] + " " + resp[2] + " " + resp[3] + " " + resp[4]);
                hndlr.post(new Runnable() {
                    @Override
                    public void run() {
                        estado("fin", resp);
                    }
                });
            }
        };
        insExec.start();
    }

    // < HILOS DE OPERACIONES > //
}
