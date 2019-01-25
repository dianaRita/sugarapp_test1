package application.app.com.sugarapp_test1;

import android.app.ActivityManager;
import android.os.Debug;
import android.util.Log;

import com.orm.SugarRecord;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import application.app.com.sugarapp_test1.daoEntity.Datos;

public class Operador {

    private ActivityManager act;

    /**
     * Constructor.
     * Recibe como parámetro un ActivitiManager
     * @param act {@link ActivityManager}
     */
    public Operador(ActivityManager act) {
        this.act = act;
    }

    /**
     * Recupera datos la memoria utilizada por la aplicaci&oacute;n.
     * Devuelve un Map con los siguientes resultados:
     * <br>totMemAsig: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsada: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>memLib: cantidad de memoria no utilizada de la memoria asignada a la aplicaci&oacute;n (mb).
     * <br>porcMemUsada:porcentaje de memoria utilizada de la parcela asignada.
     * <br>porcMemLib: porcentaje de memoria libre de la parcela asignada.
     * @return result {@link Map} Resultados
     */
    private Map<String,Double> getMemoryApp(){
        final Runtime rTime = Runtime.getRuntime();
        //double maxMemART = (double) rTime.maxMemory() / 1048576L;
        double totMemART =  (double) rTime.totalMemory() / 1048576L;
        double memUsada =  ( (double) rTime.totalMemory() - (double) rTime.freeMemory() ) / 1048576L; // 1048576L = (1024*1024)
        double memLib = (double) rTime.freeMemory() / 1048576L;
        double porcMemLib = ( memLib * 100 ) / totMemART;
        double porcMemUsada = ( memUsada * 100 ) / totMemART;
        DecimalFormat format = new DecimalFormat("#.##");
        Map<String, Double> result = new HashMap<>();
        result.put("totMemAsig",Double.parseDouble(format.format(totMemART)));
        result.put("memUsada",Double.parseDouble(format.format(memUsada)));
        result.put("memLib",Double.parseDouble(format.format(memLib)));
        result.put("porcMemLib",Double.parseDouble(format.format(porcMemLib)));
        result.put("porcMemUsada",Double.parseDouble(format.format(porcMemUsada)));
        return result;
    }

    /**
     * Recupera datos de la memoria del dispositivo.
     * Devuelve un Map con los siguientes resultados:
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memLibDevice: la cantidad libre de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice: porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * <br>porcMemLibDevice: porcentaje de memoria libre en el dispositivo.
     * @return result {@link Map} Resultados
     */
    private Map<String, Double> getMemoryDevice(){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = act;
        activityManager.getMemoryInfo(mi);
        double memTotDevice = (double) mi.totalMem / 0x100000L; // 0x100000L = (1024*1024)
        double availableMem = (double) mi.availMem / 0x100000L;
        double usedMem = ((double) mi.totalMem / 0x100000L) - ((double) mi.availMem  / 0x100000L);
        double percentAvail = ((double) mi.availMem * 100.0 ) / (double)mi.totalMem;
        double percetUsed = (((double)mi.totalMem - (double)mi.availMem) * 100.00) / (double)mi.totalMem;

        DecimalFormat format = new DecimalFormat("#.##");
        Map<String, Double> result = new HashMap<>();
        result.put("memTotDevice", Double.parseDouble(format.format(memTotDevice)));
        result.put("memLibDevice", Double.parseDouble(format.format(availableMem)));
        result.put("memUsadaDevice", Double.parseDouble(format.format(usedMem)));
        result.put("porcMemUsadaDevice", Double.parseDouble(format.format(percetUsed)));
        result.put("porcMemLibDevice", Double.parseDouble(format.format(percentAvail)));

        return result;
    }


    /**
     * Crea un objeto de tipo Datos con valores de atributos aleatorios.
     * Se utiliza para generar nuevos datos aleatorios.
     * @return dt {@link Datos}
     */
    private Datos genDatos(){
        Random r = new Random(System.currentTimeMillis());
        long x = r.nextLong();
        int i = 1+r.nextInt(1000000);
        double d = 1+r.nextDouble();
        String t = new BigInteger(130,r).toString(36);
        Date f  = new Date((x<0)?(x/6000000)*-1:x/6000000) ;
        boolean b = ( ( i%2 ) == 0 ) ? true : false;

        Datos dt = new Datos( i, d, t, f, b );
        return dt;
    }

    /**
     * Inserta datos aleatorios en la db.
     * Utiliza el m&eacute;todos genDatos para generar los datos aleatorios.
     * Devuelve un map con resultados de los an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>ultId: ultima id insertada en la db.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (ms).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * @param c {@link Integer} cantidad de datos a insertar.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map<String,String> insertarAleatorio(Integer c){
        Map<String,Double> memApp = null;
        Map<String,Double> memDev = null;
        long id = -1;

        long ini = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            id = genDatos().save();
            if (i==(c-1)) {
                memApp = getMemoryApp();
                memDev = getMemoryDevice();
            }
        }
        long fin = System.currentTimeMillis();

        Map<String,String> rslt = new HashMap<>();
        rslt.put("tiempo", (fin-ini)+"ms");
        rslt.put("ultId", id+"");
        rslt.put("memAsigApp", memApp.get("totMemAsig")+"mb");
        rslt.put("memUsadaApp", memApp.get("memUsada")+"mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada")+"%");
        rslt.put("memTotDevice", memDev.get("memTotDevice")+"mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice")+"mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice")+"%");

        return rslt;
    }

    /**
     * Inserta datos aleatorios en la db.
     * Utiliza el m&eacute;todos genDatos para generar los datos aleatorios.
     * Devuelve un map con resultados de los an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>ultId: ultima id insertada en la db.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (ms).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * @param c {@link Integer} cantidad de datos a insertar.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map<String,String> insertar(Integer c){
        Map<String,Double> memApp = null;
        Map<String,Double> memDev = null;
        Datos d = genDatos();
        long id = -1;

        long ini = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            Datos datoIns= new Datos();
            datoIns.setInteger(d.getInteger());
            datoIns.setReal(d.getReal());
            datoIns.setText(d.getText());
            datoIns.setNumDate(d.getNumDate());
            datoIns.setNumBool(d.getNumBool());
            id = datoIns.save();
            if (i==(c-1)) {
                memApp = getMemoryApp();
                memDev = getMemoryDevice();
            }
        }
        long fin = System.currentTimeMillis();

        Map<String,String> rslt = new HashMap<>();
        rslt.put("tiempo", (fin-ini)+"ms");
        rslt.put("ultId", id+"");
        rslt.put("memAsigApp", memApp.get("totMemAsig")+"mb");
        rslt.put("memUsadaApp", memApp.get("memUsada")+"mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada")+"%");
        rslt.put("memTotDevice", memDev.get("memTotDevice")+"mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice")+"mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice")+"%");

        return rslt;
    }

    public String[] consultar(){
        long ini = System.currentTimeMillis();
        List<Datos> dts = SugarRecord.listAll( Datos.class );
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
        for ( int i = 0 ; i < n; i++) {
            Datos d = genDatos();
            d.setId(id);
            up = d.save();
            id++;
        }
        long fin = System.currentTimeMillis();
        String[] rslt = {
                "ACT",
                ( fin-ini )+"ms",
                "ram",
                "Ultimo id actualizado: " + up,
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
