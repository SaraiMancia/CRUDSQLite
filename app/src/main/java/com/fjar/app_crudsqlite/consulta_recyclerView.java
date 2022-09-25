package com.fjar.app_crudsqlite;

    import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

    public class consulta_recyclerView extends AppCompatActivity {
        private RecyclerView recycler;
        private AdaptadorArticulos adaptadorArticulos;
        ConexionSQLite datos = new ConexionSQLite(consulta_recyclerView.this);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recycler_view);
            recycler = (RecyclerView)findViewById(R.id.rview);
            // Esta línea mejora el rendimiento, si sabemos que el contenido
            // no va a afectar al tamaño del RecyclerView
            recycler.setHasFixedSize(true);
            // Nuestro RecyclerView usará un linear layout manager
            recycler.setLayoutManager(new LinearLayoutManager(this));
            //adaptadorArticulos = new AdaptadorArticulos(consulta_recyclerView.this obtenerArticulos();
            adaptadorArticulos = new AdaptadorArticulos(consulta_recyclerView.this,
                    datos.mostrarArticulos());
            recycler.setAdapter(adaptadorArticulos);
        }

}
