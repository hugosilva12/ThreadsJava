package LavagemdeCarros.Model;

public class EstadoLavandaria {
    private boolean estadoLavandaria;

    public synchronized boolean isEstadoLavandaria() {
        return estadoLavandaria;
    }

    public synchronized void setEstadoLavandaria(boolean estadoLavandaria) {
        this.estadoLavandaria = estadoLavandaria;
    }
}
