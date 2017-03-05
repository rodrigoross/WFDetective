package br.com.ufmt.ic.wfd;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    //Variaveis
    private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;
    private AlertDialog info;

    WifiManager wifi;
    WifiScanReceiver wifiReciever;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Carrega o layout onde contem o ListView
        setContentView(R.layout.main);
        //Pega a referencia do ListView

        listView = (ListView) findViewById(R.id.list);

        wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        wifi.startScan();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onPause() {
        unregisterReceiver(wifiReciever);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu. Isso adiciona item na action bar se presente
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Trata dos cliques na action bar aqui. A action bar irá automaticamente cuidar dos
        // clicks do botão Home/Up, desde que seja especificado a activity pai no android manifest.xml
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = wifi.getScanResults();

            itens = new ArrayList<ItemListView>();
            ItemListView item = null;

            for (int i = 0; i < wifiScanList.size(); i++) {
                ScanResult result = wifiScanList.get(i);
                String ssid = result.SSID;

                //Salvando as informações da WIFI no ItemListView
                String mensagem = ("SSID: " + result.SSID + "\n" +
                        "BSSID: " + result.BSSID + "\n" +
                        "Nivel: " + result.level + "\n" +
                        "Capacidade: " + result.capabilities + "\n" +
                        "Frequencia: " + result.frequency + "\n");

                //Atribui uma imagem correspondente a força do sinal
                if (result.level > -50) {
                    item = new ItemListView(mensagem, R.drawable.intensidade4);
                    item.setSsid(ssid);
                    item.setIconeRid(R.drawable.intensidade4);
                    itens.add(item);
                } else if (result.level <= -50 && result.level > -60) {
                    item = new ItemListView(mensagem, R.drawable.intensidade3);
                    item.setSsid(ssid);
                    item.setIconeRid(R.drawable.intensidade3);
                    itens.add(item);
                } else if (result.level <= -60 && result.level > -70) {
                    item = new ItemListView(mensagem, R.drawable.intensidade2);
                    item.setSsid(ssid);
                    item.setIconeRid(R.drawable.intensidade2);
                    itens.add(item);
                } else {
                    item = new ItemListView(mensagem, R.drawable.intensidade1);
                    item.setSsid(ssid);
                    item.setIconeRid(R.drawable.intensidade1);
                    itens.add(item);
                }

                //Cria o adapter
                adapterListView = new AdapterListView(c, itens);

                //Define o Adapter
                listView.setAdapter(adapterListView);
                //Cor quando a lista é selecionada para ralagem.
                listView.setCacheColorHint(Color.TRANSPARENT);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ItemListView itemWifi = (ItemListView) listView.getItemAtPosition(i);
                        mostrarInfo(itemWifi);
                    }
                });


            }
        }
    }

    private void mostrarInfo(ItemListView infoAdicional){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder construtor = new AlertDialog.Builder(this);

        //Definição do titulo
        construtor.setTitle(getString(R.string.InfoAdicional));

        //Definir o icone
        construtor.setIcon(infoAdicional.getIconeRid());

        //Definir a informação adicional.
        construtor.setMessage(infoAdicional.getTexto());

        //Botão positivo
        construtor.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //Cria o AlertDialog
        info = construtor.create();
        //Exibe o Dialog
        info.show();
    }
}