package adapters.recyclerview.interfaces;

import entities.Cliente;

/**
 * Created by carlosarmando on 09/05/2016.
 */
public interface IRVClienteAdapter {
    void onItemClick(Cliente cliente);
    void onItemMapaClick(Cliente cliente);
}
