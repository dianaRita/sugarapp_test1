package application.app.com.sugarapp_test1;

import com.orm.SugarRecord;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import application.app.com.sugarapp_test1.daoEntity.Datos;

public class Operador {

    /**
     * Crea un objeto de tipo Datos.
     * Se utiliza para generar nuevos datos aleatorios
     * @return dts Datos
     */
    private Datos genDatos(){
        Random r = new Random(System.currentTimeMillis());
        long x = r.nextLong();
        int i = 1+r.nextInt(1000000);
        double d = 1+r.nextDouble();
        String t = new BigInteger(130,r).toString(36);
        Date f  = new Date((x<0)?(x/6000000)*-1:x/6000000) ;
        boolean b = ( ( i%2 ) == 0 ) ? true : false;

        Datos dts = new Datos( i, d, t, f, b );

        return dts;
    }


    public String[] insertar(Integer c){

        long id = -1;
        long ini = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            id = genDatos().save();
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
            stDts = d.toString() + "\n"+stDts;
        }

        String[] rslt = { "CON", "tiempo: "+( fin-ini ), "cpu", "ram", stDts };
        return rslt;
    }

    public String[] actualizar(){

        String stDts = "";
        long n = SugarRecord.count(Datos.class);
        long id = SugarRecord.first(Datos.class).getId();
        long up = -1;
        long ini = System.currentTimeMillis();
        for ( long i = 0 ; i < n; i++) {
            Datos d = genDatos();
            d.setId(id);
            up = d.update();
            stDts = d.toString() + "\n"+stDts;
            id++;
        }
        long fin = System.currentTimeMillis();
        String[] rslt = {"ACT", "tiempo: "+( fin-ini ), "cpu", "ram", "fin: " + up, stDts };
        return rslt;
    }

    public String[] eliminar(){
        long ini = System.currentTimeMillis();
        int i = SugarRecord.deleteAll(Datos.class);
        long fin = System.currentTimeMillis();
        String[] rslt = {"ELI", "tiempo: "+( fin-ini ), "cpu", "ram", "Del: " + i };
        return rslt;
    }

}
