package magazzino;

public interface IMagazzino {
    public void deposita(String articolo, int id);
    public int preleva(String articolo);
}
