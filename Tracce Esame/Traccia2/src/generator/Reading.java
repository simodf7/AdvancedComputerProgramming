package generator;

import java.util.Random;

public class Reading implements IReading{

    private String tipo; 
    private int valore; 
    private final static long SerialVersionUID=10; 
    private final static String[] tipi = {"temperatura", "pressione"};

    public Reading(){
        Random random = new Random(); 
        this.tipo = tipi[random.nextInt(2)]; 
        this.valore = random.nextInt(51);
    }


    @Override
    public void setTipo(String s) {
        tipo = s; 
    }

    @Override
    public void setValore(int v) {
        valore = v; 
    }

    @Override
    public String getTipo() {
        return tipo; 
    }

    @Override
    public int getValore() {
        return valore; 
    }
    
}
