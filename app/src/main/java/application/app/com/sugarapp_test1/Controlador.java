package application.app.com.sugarapp_test1;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Controlador {

    private MainActivity m;
    private Operador op;

    public Controlador( MainActivity m ) {
        this.m = m;
        this.op = new Operador();
        asignaEvento( m.getBtnIns() ); asignaEvento( m.getBtnCons() );
        asignaEvento( m.getBtnAct() ); asignaEvento( m.getBtnElim() );
    }

    public void asignaEvento( final Button btn ){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().equals("Insertar")){
                    Integer c = new Integer( Integer.parseInt( m.gettCant().getText().toString() ) );
                    estado("INS");
                    String[] r = op.insertar(c);
                    Log.e("Mensaje", ">>>>> " + r[0] + " " + r[1] + " " + r[2] + " " + r[3] + " " + r[4]);
                }
                if (btn.getText().equals("Consultar")){

                    String[] r = op.consultar();
                    m.gettCnsl().setText( r[4] );
                    Log.e("Mensaje", ">>>>> " + r[0] + " " + r[1] + " " + r[2] + " " + r[3]);
                }
                if (btn.getText().equals("Actualizar")){
                    Toast.makeText( m.getApplicationContext(), "Actualizar",Toast.LENGTH_LONG ).show();
                    String[] r = op.actualizar();
                    m.gettCnsl().setText( r[5] );
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
     * Modifica el estado de edición de la app.
     * Necesita un parametro de tipo String los cuales pueden ser
     * INS, CON, ACT, ELI, NUE.
     * @param e {@link String}
     */
    private void estado(String e){
        switch (e){
            case "INS":
                //m.getPrgrs().setVisibility(View.GONE);
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
}
