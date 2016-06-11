package com.cibertec.capturapedido.adapter.recyclerview.interfaces;

import com.cibertec.capturapedido.entities.Cliente;

/**
 * Created by bestrada on 10/05/2016.
 */
public interface IRVClienteAdapter {
    void onSelectItem(Cliente cliente);
    void onPositionMap(Cliente cliente);
}
