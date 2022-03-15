package LavagemdeCarros.Model;

public class ClientesEmEspera {
    private int count;

    public ClientesEmEspera() {
        this.count = 0;
    }

    public synchronized void addClient() {
        this.count += 1;
    }

    public synchronized void as() {
        this.count = count - 1;
    }

    public synchronized int getNumberClients() {
        return count;
    }

    public synchronized void setCount(int count) {
        this.count = count;
    }

    public synchronized void reset(){
        this.count=0;
    }
}
