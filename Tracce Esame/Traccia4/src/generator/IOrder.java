package generator;

import java.io.Serializable;

public interface IOrder extends Serializable {

    int getLocation();
    int getID();
    String getAddress();

}
