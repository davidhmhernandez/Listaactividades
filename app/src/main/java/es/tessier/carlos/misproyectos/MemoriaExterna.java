package es.tessier.carlos.misproyectos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MemoriaExterna extends Activity {

    private EditText cajaTexto;
    private Button guardar;
    private Button cargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria_interna);

        cajaTexto = (EditText)findViewById(R.id.cajaTexto);
        guardar = (Button)findViewById(R.id.botonGuardar);
        cargar = (Button)findViewById(R.id.botonCargar);
    }

    public void guardar(View v){
        boolean sdDisponible = isExternalStorageWritable();

        if(sdDisponible){
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsolutePath() + "/MisFicheros");
            directory.mkdirs();

            File file = new File(directory, "ficherotexto.txt");

            FileOutputStream fOut = null;
            OutputStreamWriter osw = null;
            try {
                fOut = new FileOutputStream(file);
                osw = new OutputStreamWriter(fOut);

                String texto = cajaTexto.getText().toString();

                osw.write(texto);
                osw.flush();

                Toast.makeText(getApplicationContext(),"Fichero guardado con exito!", Toast.LENGTH_SHORT).show();
                cajaTexto.setText("");


            }catch(FileNotFoundException fnfe){
                Toast.makeText(getApplicationContext(), fnfe.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }finally{
                try {
                    fOut.close();
                    osw.close();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "No se puede escribir en memoria externa", Toast.LENGTH_SHORT).show();
        }


    }

    public void cargar(View v){
        boolean sdDisponible = isExternalStorageReadable();

        if(sdDisponible){
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsolutePath());
            File file = new File(directory, "ficherotexto.txt");

            FileInputStream fIn = null;
            InputStreamReader isr = null;
            char[] inputBuffer = new char[100];
            String s = "";
            try{
                fIn = new FileInputStream(file);
                isr = new InputStreamReader(fIn);

                int charRead;
                while ((charRead = isr.read(inputBuffer))>0){
                    String readString = String.copyValueOf(inputBuffer, 0,charRead);
                    s += readString;
                    inputBuffer = new char[100];
                }

                cajaTexto.setText(s);


            }catch(FileNotFoundException fnfe){
                Toast.makeText(getApplicationContext(), fnfe.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }finally{
                try {
                    fIn.close();
                    isr.close();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "No se puede cargar de memoria externa", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable(){
        String state=Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
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
