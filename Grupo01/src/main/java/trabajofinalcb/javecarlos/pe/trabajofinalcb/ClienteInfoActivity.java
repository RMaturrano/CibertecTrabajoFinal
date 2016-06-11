package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import entities.Cliente;
import utils.Constantes;

/**
 * Created by carlosarmando on 09/05/2016.
 */
public class ClienteInfoActivity extends AppCompatActivity {
    private TextView tvContactoNombreInfo, tvContactoApellidoInfo, tvContactoCorreoInfo, tvContactoTelefonoInfo,
            tvClienteDireccionInfo, tvClienteDistritoInfo, tvClienteReferenciaInfo;
    private Button btnNuevoPedido;
    private ImageView imgClienteLlamar, imgClienteMapa;
    private Toolbar toolbar;
    private Cliente cliente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_info);

        CargarTextView();
        CargarButton();
        CargarImageView();

        /*Cargar Eventos Boton e Iconos*/
        btnNuevoPedido.setOnClickListener(btnNuevoPedidoOnClickListener);
        imgClienteLlamar.setOnClickListener(imgClienteLlamarOnClickListener);
        imgClienteMapa.setOnClickListener(imgClienteMapaOnClickListener);

        cliente = ObtenerCliente();

        if (cliente != null)
            SetDataCliente(cliente);

        //para el toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cliente != null ? cliente.getEmpresaNombre() : "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_cliente_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click del editar
            if (item.getItemId() == R.id.btnEditar) {
                boolean esNuevo;
                Intent intent = new Intent(ClienteInfoActivity.this, ClienteAddEditActivity.class);
                esNuevo = cliente == null;
                intent.putExtra(Constantes.ARG_NUEVO_CLIENTE, esNuevo);
                intent.putExtra(Constantes.ARG_CLIENTE, cliente);
                startActivity(intent);
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkCallPermission() {
        String permission = "android.permission.CALL_PHONE";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    View.OnClickListener btnNuevoPedidoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ClienteInfoActivity.this, PedidoNuevoActivity.class);
            intent.putExtra(Constantes.ARG_CLIENTE, cliente);
            startActivity(intent);
        }
    };

    View.OnClickListener imgClienteLlamarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(ClienteInfoActivity.this, "LLAMANDO", Toast.LENGTH_SHORT);
            toast.show();
            if (checkCallPermission()) {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvContactoTelefonoInfo.getText())));
            }
        }
    };
    View.OnClickListener imgClienteMapaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast toast = Toast.makeText(ClienteInfoActivity.this, "IR AL MAPA", Toast.LENGTH_SHORT);
//            toast.show();
            Intent intent = new Intent(ClienteInfoActivity.this, ClienteMapaActivity.class);
            intent.putExtra(Constantes.ARG_CLIENTE, cliente);
            startActivity(intent);
        }
    };

    private Cliente ObtenerCliente() {
        Cliente clienteInfo;
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constantes.ARG_CLIENTE)) {
            clienteInfo = getIntent().getParcelableExtra(Constantes.ARG_CLIENTE);
        } else {
            clienteInfo = null;
        }

        return clienteInfo;
    }

    private void CargarTextView() {
        tvContactoNombreInfo = (TextView) findViewById(R.id.tvContactoNombreInfo);
        tvContactoApellidoInfo = (TextView) findViewById(R.id.tvContactoApellidoInfo);
        tvContactoCorreoInfo = (TextView) findViewById(R.id.tvContactoCorreoInfo);
        tvContactoTelefonoInfo = (TextView) findViewById(R.id.tvContactoTelefonoInfo);
        tvClienteDireccionInfo = (TextView) findViewById(R.id.tvClienteDireccionInfo);
        tvClienteDistritoInfo = (TextView) findViewById(R.id.tvClienteDistritoInfo);
        tvClienteReferenciaInfo = (TextView) findViewById(R.id.tvClienteReferenciaInfo);
    }

    private void CargarButton(){
        btnNuevoPedido = (Button)findViewById(R.id.btnNuevoPedido);
    }

    private void CargarImageView() {
        imgClienteLlamar = (ImageView) findViewById(R.id.imgClienteLlamar);
        imgClienteMapa = (ImageView) findViewById(R.id.imgClienteMapa);
    }

    private void SetDataCliente(Cliente cliente) {
        tvContactoNombreInfo.setText(cliente.getContactoNombre());
        tvContactoApellidoInfo.setText(cliente.getContactoApellido());
        tvContactoCorreoInfo.setText(cliente.getContactoCorreo());
        tvContactoTelefonoInfo.setText(String.valueOf(cliente.getContactoTelefono()));
        tvClienteDireccionInfo.setText(cliente.getEmpresaDireccion());
        tvClienteDistritoInfo.setText(cliente.getEmpresaDistrito());
        tvClienteReferenciaInfo.setText(cliente.getEmpresaReferencia());
    }
}
