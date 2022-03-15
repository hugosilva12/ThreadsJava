package LavagemdeCarros.Model;

public class ValoresMoedeiro {
    private  double saldo;
    private  double troco;

    public ValoresMoedeiro(){
        this.saldo = 0;
        this.troco = 0;
    }
    public synchronized double getTroco() {
        return troco;
    }

    public synchronized double getSaldo() {
        return saldo;
    }

    public synchronized void addSaldo(double saldo) {
        this.troco = 0;
        this.saldo += saldo;
    }
    public synchronized void retirarSaldo() {
        this.troco = saldo;
        this.saldo = 0;
    }
    public  synchronized void pagarLavagem(double value){
        this.troco = saldo-value;
        this.saldo = 0;
    }
    public  synchronized void reset(){
        this.troco = 0;
        this.saldo = 0;
    }
}
