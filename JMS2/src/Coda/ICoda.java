package Coda;

public interface ICoda {
    int size();
    void inserisci(String cmd);
    String preleva();
    boolean full();
    boolean empty();
}
