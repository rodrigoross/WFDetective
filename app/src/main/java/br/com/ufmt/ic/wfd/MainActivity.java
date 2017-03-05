package br.com.ufmt.ic.wfd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.ufmt.ic.wfd.R;

public class MainActivity extends AppCompatActivity {

    //Variaveis
    private Button btn_procurar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008421")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linkar interface as váriaveis
        btn_procurar = (Button) findViewById(R.id.btn_procurar);
        btn_procurar.setOnClickListener(abrirLista);
    }

    //Ação ao pressionar o botão de procura
    View.OnClickListener abrirLista = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //abertura da lista de wifis no local
            Intent mostrarListaWifi = new Intent(MainActivity.this, ListActivity.class);
            startActivity(mostrarListaWifi);

        }
    };

}
