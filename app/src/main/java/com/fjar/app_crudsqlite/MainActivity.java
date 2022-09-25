package com.fjar.app_crudsqlite;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.MenuProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.widget.Toolbar;

import com.fjar.app_crudsqlite.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_eliminar, btn_actualizar;
    private TextView tv_resultado;
    private FABToolbarLayout morph;

    boolean inputEt=false;
    boolean inputEd=false;
    boolean input1=false;

    int resultadoInsert=0;

    Modal ventanas = new Modal();
    ConexionSQLite conexion = new ConexionSQLite(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_delete)
                    .setTitle("Warning")
                    .setMessage("Realmente deseas salir")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finishAffinity();
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple_200));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD SQLite");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.purple_500));
        toolbar.setTitle("Alexia.M");
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmacion();
            }
        });
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.search(MainActivity.this);
            }
        });*/

        FloatingActionButton fab = findViewById(R.id.fab);
        morph = findViewById(R.id.fabtoolbar);

        View uno, dos, tres, cuatro, cinco, seis;

        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        cuatro = findViewById(R.id.cuatro);
        tres = findViewById(R.id.tres);
        cinco = findViewById(R.id.cinco);
        seis = findViewById(R.id.seis);

        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
        et_codigo = (EditText) findViewById(R.id.et_codigo);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);
        et_precio = (EditText) findViewById(R.id.et_precio);
        btn_guardar=(Button) findViewById(R.id.btn_guardar);
        btn_consultar1=(Button) findViewById(R.id.btn_consultar1);
        btn_consultar2=(Button) findViewById(R.id.btn_consultar2);
        btn_eliminar=(Button) findViewById(R.id.btn_eliminar);
        btn_actualizar=(Button) findViewById(R.id.btn_actualizar);

        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

        try{
            Intent intent=getIntent();
            Bundle bundle=intent.getExtras();
            if(bundle !=null){
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                descripcion = bundle.getString("descripcion");
                precio = bundle.getString("precio");
                if(senal.equals("1")){
                    et_codigo.setText(codigo);
                    et_descripcion.setText(descripcion);
                    et_precio.setText(precio);
                }

            }
        }catch (Exception e){

        }

    }
    private void confirmacion(){
        String mensaje = "¿Realmente desea salir?";

        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_delete);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int id) {

            }
        });
        dialogo.show();
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_limpiar) {
            et_codigo.setText(null);
            et_descripcion.setText(null);
            et_precio.setText(null);
            return true;
        }else if(id==R.id.action_listaArticulos){
            Intent spinnerActivity = new Intent(MainActivity.this, Activity_consulta_spinner.class);
            startActivity(spinnerActivity);
            return true;
        }else if(id==R.id.action_listaArticulos1){
            Intent listViewActivity = new Intent(MainActivity.this, Activity_list_view_articulos.class);
            startActivity(listViewActivity);
            return true;
        }else if(id==R.id.action_card){
            Intent recyclerView= new Intent(MainActivity.this, consulta_recyclerView.class);
            startActivity(recyclerView);
            return true;
        }else if(id==R.id.action_acerca){
            Intent AcercaDe = new Intent(MainActivity.this, AcercaDe.class);
            startActivity(AcercaDe);
        }

        return super.onOptionsItemSelected(item);
    }
    public void alta (View v){
    if(et_codigo.getText().toString().length()==0){
        et_codigo.setError("Campo Obligatorio");
        inputEt = false;
    }else{
        inputEt=true;
    }
        if(et_descripcion.getText().toString().length()==0){
            et_descripcion.setError("Campo Obligatorio");
            inputEd = false;
        }else{
            inputEd=true;
        }if(et_precio.getText().toString().length()==0){
            et_precio.setError("Campo Obligatorio");
            input1 = false;
        }else{
            input1=true;
        }
           if(inputEt && inputEd && input1){
               try{
                   datos.setCodigo(Integer.parseInt(et_codigo.getText().toString()));
                   datos.setDescripcion(et_descripcion.getText().toString());
                   datos.setPrecio(Double.parseDouble(et_precio.getText().toString()));
                   if(conexion.InsertarTradicional(datos)){
                       Toast.makeText(this, "Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                       limpiarDatos();
                   }else{
                       Toast.makeText(getApplicationContext(), "Error. Ya exite un registro\n"+"Codigo: "+et_codigo.getText().toString(), Toast.LENGTH_LONG).show();
                       limpiarDatos();

                   }
               }catch (Exception e){
                   Toast.makeText(this, "ERROR. Ya existe. ", Toast.LENGTH_SHORT).show();

               }
           }
    }
    public void mensaje (String mensaje){
        Toast.makeText(this, ""+mensaje, Toast.LENGTH_SHORT).show();

    }


    public void limpiarDatos(){
        et_codigo.setText(null);
        et_descripcion.setText(null);
        et_precio.setText(null);
        et_codigo.requestFocus();
    }
    public void consultaporcodigo (View v){
        if(et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo Obligatorio");
            inputEt=false;
        }else{
            inputEt=true;
        }
        if(inputEt){
            String codigo = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(codigo));
            if(conexion.consultaArticulos(datos)){
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText(""+datos.getPrecio());
            }else{
                Toast.makeText(this, "No existe un articulo con dicho código", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else{
            Toast.makeText(this, "Ingrese el código del articulo a buscar", Toast.LENGTH_SHORT).show();

        }
    }

    public void consultapordescripcion(View v){
        if(et_descripcion.getText().toString().length()==0){
            et_descripcion.setError("Campo Obligatorio");
            inputEd = false;
        }else{
            inputEd = true;
        }
        if(inputEd){

            String descripcion = et_descripcion.getText().toString();
            datos.setDescripcion(descripcion);
            if(conexion.consultarDescripcion(datos)){
                et_codigo.setText(""+datos.getCodigo());
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText(""+datos.getPrecio());
            }else{
                Toast.makeText(this, "No existe un articulo con dicha descripción",
                        Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }

    }else{
            Toast.makeText(this, "Ingrese la descripción del articulo a buscar",
                    Toast.LENGTH_SHORT).show();

        }

    }
    public void bajaporcodigo(View v){
        if(et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo Obligatorio");
            inputEt=false;
        }else{
            inputEt=true;
        }
        if(inputEt){
            String cod = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(cod));
            if(conexion.bajaCodigo(MainActivity.this,datos)){
                limpiarDatos();
            }else{
                Toast.makeText(this, "No existe un articulo con dicho código", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }
    public void modificacion(View v){
        if(et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo Obligatorio");
            inputEt=false;
        }else{
            inputEt=true;
        }
        if(inputEt){
            String cod = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            double precio = Double.parseDouble(et_precio.getText().toString());

            datos.setCodigo(Integer.parseInt(cod));
            datos.setDescripcion(descripcion);
            datos.setPrecio(precio);
            if(conexion.modificar(datos)){
               Toast.makeText(this, "Registro Modificado Correctamente.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No se han encontrado resultados para la busqueda especificada", Toast.LENGTH_SHORT).show();

            }
        }
    }
     public void showToast(int opcion,String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);
        toastText.setText(message);

        if(opcion == 1){
            toastImage.setImageResource(R.drawable.save);
        }else if(opcion == 2){
            toastImage.setImageResource(R.drawable.ic_baseline_search_24);
        }else if(opcion == 3){
            toastImage.setImageResource(R.drawable.ic_baseline_search_24);

        }else if(opcion == 4){
            toastImage.setImageResource(R.drawable.ic_delete);

        }else if(opcion == 5){
            toastImage.setImageResource(R.drawable.edit);
        }

        toastImage.setImageResource(R.drawable.ic_delete);

        Toast toast = new Toast( (getApplicationContext()));
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab){
            morph.show();
        }
        switch (v.getId()){
            case R.id.uno:
                showToast(1, "Clic boton 1");

                morph.hide();
                break;
            case R.id.dos:
                //showToast("Clic boton 2");
                ventanas.search(MainActivity.this);
                morph.hide();
                break;
            case R.id.tres:
                showToast(3,"Clic boton 3");

                morph.hide();
                break;
            case R.id.cuatro:
                showToast(4,"Clic boton 4");
                morph.hide();
                break;
            case R.id.cinco:
                showToast(5,"Clic boton 5");
                morph.hide();
                break;
            case R.id.seis:
                showToast(6,"Clic boton 6");
                morph.hide();
                break;
        }
    }


}