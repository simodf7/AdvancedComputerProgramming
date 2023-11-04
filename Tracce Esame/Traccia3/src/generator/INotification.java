package generator;

import java.io.Serializable;

public interface INotification extends Serializable{

    void setId(int id); 
    void setCriticality(int critic);
    int getId(); 
    int getCriticality(); 
    
}
