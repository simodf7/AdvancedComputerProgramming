package sensorsManager;

import java.io.Serializable;

public interface IReading extends Serializable{

    String getType();
    int getValue();

}
