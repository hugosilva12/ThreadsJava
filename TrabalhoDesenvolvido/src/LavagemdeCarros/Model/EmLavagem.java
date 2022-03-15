package LavagemdeCarros.Model;

public class EmLavagem {

 public EmLavagem(){

 }
    public synchronized void acorda() {
        this.notify();
    }
    public synchronized void espera() {
        try{
            this.wait();
        }catch (InterruptedException ie){}
    }
}
