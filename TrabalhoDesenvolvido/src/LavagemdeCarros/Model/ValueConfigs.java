package LavagemdeCarros.Model;

public class ValueConfigs {
    private int  customonetario;
    private int duracaoTapete ;
    private int duracaoRolo;
    private int minDuracaoSecador;
    private int maxDuracaoSecador;
    private int duracaoAspirador;
    public ValueConfigs(){

    }

    public int getCustomonetario() {
        return customonetario;
    }

    public void setCustomonetario(int customonetario) {
        this.customonetario = customonetario;
    }

    public int getDuracaoTapete() {
        return duracaoTapete;
    }

    public void setDuracaoTapete(int duracaoTapete) {
        this.duracaoTapete = duracaoTapete;
    }

    public int getDuracaoRolo() {
        return duracaoRolo;
    }

    public void setDuracaoRolo(int duracaoRolo) {
        this.duracaoRolo = duracaoRolo;
    }

    public int getMinDuracaoSecador() {
        return minDuracaoSecador;
    }

    public void setMinDuracaoSecador(int minDuracaoSecador) {
        this.minDuracaoSecador = minDuracaoSecador;
    }

    public int getMaxDuracaoSecador() {
        return maxDuracaoSecador;
    }

    public void setMaxDuracaoSecador(int maxDuracaoSecador) {
        this.maxDuracaoSecador = maxDuracaoSecador;
    }

    public int getDuracaoAspirador() {
        return duracaoAspirador;
    }

    public void setDuracaoAspirador(int duracaoAspirador) {
        this.duracaoAspirador = duracaoAspirador;
    }

    @Override
    public String toString() {
        return "ValueConfigs{" +
                "customonetario=" + customonetario +
                ", duracaoTapete=" + duracaoTapete +
                ", duracaoRolo=" + duracaoRolo +
                ", minDuracaoSecador=" + minDuracaoSecador +
                ", maxDuracaoSecador=" + maxDuracaoSecador +
                ", duracaoAspirador=" + duracaoAspirador +
                '}';
    }
}
