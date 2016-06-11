package com.project.cibertec.finalproject.cliente.adapter.recyclerview.listeners;

import com.project.cibertec.finalproject.entities.Cliente;

public interface IRVAdapterListaClienteListener {

    void onItemClick(Cliente cliente);
    void onItemMapPinClick(Cliente cliente);
    void onItemPhoneClick(Cliente cliente);

}
