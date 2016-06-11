package com.cibertec.capturapedido;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.capturapedido.entities.Cliente;

/**
 * Created by bestrada on 12/05/2016.
 */
public class ClienteDetalleActivity extends AppCompatActivity {
    final public static String ARG_CLIENTE = "arg_cliente";
    Toolbar toolbar_item;
    Cliente cliente = null;

    TextView tvClienteDetalleNombre, tvClienteDetalleApellido, tvClienteDetalleCorreo, tvClienteDetalleTelefono,
            tvClienteDetalleDireccion, tvClienteDetalleDistrito, tvClienteDetalleReferencia;

    ImageView ivClienteDetalleTelefono, ivClienteDetalleMap;
    Button btClienteDetalleNuevoPedido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_detalle_activity);

        tvClienteDetalleNombre = (TextView) findViewById(R.id.tvClienteDetalleNombre);
        tvClienteDetalleApellido = (TextView) findViewById(R.id.tvClienteDetalleApellido);
        tvClienteDetalleCorreo = (TextView) findViewById(R.id.tvClienteDetalleCorreo);
        tvClienteDetalleTelefono = (TextView) findViewById(R.id.tvClienteDetalleTelefono);
        tvClienteDetalleDireccion = (TextView) findViewById(R.id.tvClienteDetalleDireccion);
        tvClienteDetalleDistrito = (TextView) findViewById(R.id.tvClienteDetalleDistrito);
        tvClienteDetalleReferencia = (TextView) findViewById(R.id.tvClienteDetalleReferencia);

        ivClienteDetalleTelefono = (ImageView) findViewById(R.id.ivClienteDetalleTelefono);
        ivClienteDetalleMap = (ImageView) findViewById(R.id.ivClienteDetalleMap);
        ivClienteDetalleTelefono.setOnClickListener(ivClienteDetalleTelefonoOnClickListener);
        ivClienteDetalleMap.setOnClickListener(ivClienteDetalleMapOnClickListener);

        btClienteDetalleNuevoPedido = (Button) findViewById(R.id.btClienteDetalleNuevoPedido);
        btClienteDetalleNuevoPedido.setOnClickListener(btClienteDetalleNuevoPedidoOnClickListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cliente = getIntent().getParcelableExtra(ARG_CLIENTE);
        setData(cliente);
    }

    View.OnClickListener ivClienteDetalleTelefonoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + tvClienteDetalleTelefono.getText()));
            if (ActivityCompat.checkSelfPermission(ClienteDetalleActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        }
    };

    View.OnClickListener ivClienteDetalleMapOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ClienteDetalleActivity.this, ClientePosicionActivity.class);
            intent.putExtra(ClientePosicionActivity.ARG_CLIENTE, cliente);
            startActivity(intent);
        }
    };

    View.OnClickListener btClienteDetalleNuevoPedidoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ClienteDetalleActivity.this, PedidoNuevoActivity.class);
            intent.putExtra(PedidoNuevoActivity.ARG_CLIENTE, cliente);
            startActivity(intent);
        }
    };

    private void setData(Cliente cliente){
        getSupportActionBar().setTitle(cliente.getEmpresa());

        tvClienteDetalleNombre.setText(cliente.getNombre());
        tvClienteDetalleApellido.setText(cliente.getApellido());
        tvClienteDetalleCorreo.setText(cliente.getCorreo());
        tvClienteDetalleTelefono.setText(cliente.getTelefono());

        tvClienteDetalleDireccion.setText(cliente.getDireccion());
        tvClienteDetalleDistrito.setText(cliente.getDistrito());
        tvClienteDetalleReferencia.setText(cliente.getReferencia());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente_editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        else if (item.getItemId()==R.id.ab_save){
           Intent intent = new Intent(ClienteDetalleActivity.this, ClienteGrabarActivity.class);
            intent.putExtra(ClienteGrabarActivity.ARG_CLIENTE, cliente);
            startActivityForResult(intent, 99);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 99){
                cliente = data.<Cliente>getParcelableExtra(ClienteGrabarActivity.ARG_CLIENTE);
                setData(cliente);
            }
        }
    }
}
