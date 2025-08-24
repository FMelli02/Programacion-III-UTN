package Dominio;

public class Perro extends Animal{
    private String idChip;
    private Raza raza;

    public void ladrar(){
    }

    public Raza getRaza() {
        return raza;
    }

    @Override
    public String getIdentificacion() {
        return idChip;
    }
}
