package br.com.ufmt.ic.wfd;

public class ItemListView {
    private String texto;
    private String ssid;
    private int iconeRid;

    public ItemListView(String texto, int iconeRid)
    {
        this.texto = texto;
        this.iconeRid = iconeRid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getIconeRid()
    {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid)
    {
        this.iconeRid = iconeRid;
    }

    public String getTexto()
    {
        return texto;
    }

    public void setTexto(String texto)
    {
        this.texto = texto;
    }
}