package com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces;

import com.trabajo.capturapedido.capturapedido.entities.BECliente;

/**
 * Created by cechenique on 18/05/2016.
 */
public interface IRVClienteAdapter {
    void onSelectItem (BECliente cliente);
    //void onItemClick (BECliente cliente);
    void onItemMapClick(BECliente cliente);
}

