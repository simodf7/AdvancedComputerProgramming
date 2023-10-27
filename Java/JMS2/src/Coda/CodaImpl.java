package Coda;

public class CodaImpl implements ICoda {

    private String[] coda;
    private int head;
    private int tail;
    private int size;
    private int elem;

    public CodaImpl(int s){
        size = s;
        coda = new String[size];
        elem = head = tail = 0;
    }


    @Override
    public int size() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public void inserisci(String cmd) {
        // TODO Auto-generated method stub
        
        coda[head] = cmd;
        head = (head + 1) % size;
        elem++;

    }

    @Override
    public String preleva() {
        // TODO Auto-generated method stub
        String cmd = null;
        cmd = coda[tail];
        tail = (tail +1) % size;
        elem--;
        return cmd;
    }

    @Override
    public boolean full() {
        // TODO Auto-generated method stub
        return elem == size;
    }

    @Override
    public boolean empty() {
        // TODO Auto-generated method stub
        return elem == 0;
    }

    

}
