package application.app.com.sugarapp_test1;

import android.app.ActivityManager;
import android.os.Debug;
import android.util.Log;

import com.orm.SugarRecord;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import application.app.com.sugarapp_test1.daoEntity.Datos;

public class Operador {

    ActivityManager act;

    public Operador(ActivityManager act) {
        this.act = act;
    }

    public long getMemory(){
        ActivityManager.MemoryInfo mInfo = new ActivityManager.MemoryInfo();
        act.getMemoryInfo(mInfo);

        return ((mInfo.totalMem - mInfo.availMem)/(1024*1024));
    }


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
        long memTotUseProm =0;
        long memIniUse = getMemory();
        long ini = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            id = genDatos().save();
            memTotUseProm += getMemory();
        }
        long fin = System.currentTimeMillis();
        memTotUseProm = memTotUseProm/c;
        long memUseApp = memTotUseProm-memIniUse;
        Log.e("operacion", "mem ini used: "+memIniUse+"");
        Log.e("operacion", "mem tot used prom: "+memTotUseProm+"");
        Log.e("operacion", "mem Use app: "+memUseApp+"");
        String[] rslt = { "INS", "tiempo: "+(fin-ini), "cpu", "ram: "+(memUseApp)+"Mb", "id: "+id+"" };
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
        long n = SugarRecord.count(Datos.class);
        if (n<=0){
            String[] rslt = {"ACT", "0", "0", "0", "No existen datos a ser modificados" };
            return rslt;
        }
        long id = SugarRecord.first(Datos.class).getId();
        long up = -1;
        long ini = System.currentTimeMillis();
        for ( long i = 0 ; i < n; i++) {
            Datos d = genDatos();
            d.setId(id);
            up = d.update();
            id++;
        }
        long fin = System.currentTimeMillis();
        String[] rslt = {
                "ACT",
                ( fin-ini )+"ms",
                "ram",
                "Registros Actualizados" + up,
                "Actualización exitosa"
        };
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
