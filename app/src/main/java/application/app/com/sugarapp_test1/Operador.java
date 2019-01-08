package application.app.com.sugarapp_test1;

import android.util.Base64;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.app.com.sugarapp_test1.daoEntity.Datos;

public class Operador {

    public String[] insertar(Integer c){

        Datos d;
        long id = -1;
        long ini = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {

            d = new Datos(
                    new Integer( i ),
                    new Double( ( int ) ( Math.random() * 100000 ) + 1 ),
                    Base64.encodeToString( ( (c+i)+"").getBytes(),Base64.DEFAULT ),
                    new Date( Long.parseLong(( ( int ) ( Math.random() * 100000 ) + 1 ) + "" )),
                    ( ( i%2 ) == 0 ) ? true : false
            );
            id = d.save();
        }
        long fin = System.currentTimeMillis();
        String[] rslt = { "INS", "tiempo: "+(fin-ini), "cpu", "ram", "id: "+id+"" };
        return rslt;
    }

    public String[] consultar(){
        List<Datos> dts = new ArrayList<Datos>();

        long ini = System.currentTimeMillis();
        dts = SugarRecord.listAll( Datos.class );
        long fin = System.currentTimeMillis();

        String stDts = "";
        for ( Datos d: dts ) {
            stDts = d.getInteger()
                    +" "+d.getReal()
                    +" "+d.getText()
                    +" "+d.getNumDate()
                    +" "+d.getNumBool()
                    +"\n"+stDts;
        }

        String[] rslt = { "CON", "tiempo: "+( fin-ini ), "cpu", "ram", stDts };
        return rslt;
    }

    public String[] actualizar(){
        String[] rslt = {"ACT", "tiempo", "cpu", "ram"};
        return rslt;
    }

    public String[] eliminar(){
        long fin = System.currentTimeMillis();
        int i = SugarRecord.deleteAll(Datos.class);
        long ini = System.currentTimeMillis();
        String[] rslt = {"ELI", "tiempo: "+( fin-ini ), "cpu", "ram", "Del: " + i };
        return rslt;
    }

}
