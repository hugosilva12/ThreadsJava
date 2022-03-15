package LavagemdeCarros.Model;

public class MensagemLog {

    private String mensagem;
    public synchronized String getMensagem() {
        return mensagem;
    }

    public synchronized void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


}
