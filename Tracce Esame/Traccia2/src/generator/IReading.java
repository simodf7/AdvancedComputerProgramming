package generator;

import java.io.Serializable;

public interface IReading extends Serializable{

    void setTipo(String s); 
    void setValore(int v); 
    String getTipo(); 
    int getValore(); 
    

}
