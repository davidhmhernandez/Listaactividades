package es.tessier.carlos.misproyectos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MemoriaInterna extends Activity {

    private EditText cajaTexto;
    private Button botonGuardar;
    private Button botonCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria_interna);

        cajaTexto = (EditText)findViewById(R.id.cajaTexto);
        botonGuardar = (Button)findViewById(R.id.botonGuardar);
        botonCargar = (Button)findViewById(R.id.botonCargar);
    }

    public void guardar(View v){
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;
        try {
            fOut = openFileOutput("fichero.txt",0);
            osw = new OutputStreamWriter(fOut);

            String texto = cajaTexto.getText().toString();

            osw.write(texto);
            osw.flush();

            Toast.makeText(getApplicationContext(),"Fichero guardado con exito!", Toast.LENGTH_SHORT).show();
            cajaTexto.setText("");

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"Fichero no encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"IOException al guardar", Toast.LENGTH_SHORT).show();
        }finally {
            try {
                osw.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"IOException al cerrar OutputStreamWriter en guardar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cargar(View v){
        FileInputStream fIn = null;
        InputStreamReader isr = null;
        char[] inputBuffer = new char[100];
        String s = "";
        try {
            fIn = openFileInput("fichero.txt");
            isr = new InputStreamReader(fIn);

            int charRead;
            while ((charRead = isr.read(inputBuffer))>0)
            {
                String readString = String.copyValueOf(inputBuffer, 0,charRead);
                s += readString;
                inputBuffer = new char[100];
            }

            cajaTexto.setText(s);

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"Fichero no encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"IOException al guardar", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memoria_externa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
