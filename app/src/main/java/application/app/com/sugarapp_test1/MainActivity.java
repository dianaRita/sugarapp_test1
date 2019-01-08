package application.app.com.sugarapp_test1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnIns, btnCons, btnAct, btnElim;
    private TextView tCant, tCnsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnIns = findViewById(R.id.btnInser);
        this.btnCons = findViewById(R.id.btnCons);
        this.btnAct = findViewById(R.id.btnAct);
        this.btnElim = findViewById(R.id.btnElim);

        this.tCant = findViewById(R.id.tCant);
        this.tCnsl = findViewById(R.id.tCnsl);

        new Controlador(this);

    }



    public Button getBtnIns() {
        return btnIns;
    }

    public Button getBtnCons() {
        return btnCons;
    }

    public Button getBtnAct() {
        return btnAct;
    }

    public Button getBtnElim() {
        return btnElim;
    }

    public TextView gettCant() {
        return tCant;
    }

    public TextView gettCnsl() {
        return tCnsl;
    }
}