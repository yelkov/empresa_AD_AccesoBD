package edu.badpals.empresa;

public class Proxecto {
    private int num_proxecto;
    private String nome_proxecto;
    private String lugar;
    private int num_departamento;

    public Proxecto(String nome_proxecto, String lugar, int num_departamento) {
        this.nome_proxecto = nome_proxecto;
        this.lugar = lugar;
        this.num_departamento = num_departamento;
    }

    public Proxecto(int num_proxecto, String nome_proxecto, String lugar, int num_departamento) {
        this.num_proxecto = num_proxecto;
        this.nome_proxecto = nome_proxecto;
        this.lugar = lugar;
        this.num_departamento = num_departamento;
    }

    public int getNum_proxecto() {
        return num_proxecto;
    }

    public void setNum_proxecto(int num_proxecto) {
        this.num_proxecto = num_proxecto;
    }

    public String getNome_proxecto() {
        return nome_proxecto;
    }

    public void setNome_proxecto(String nome_proxecto) {
        this.nome_proxecto = nome_proxecto;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getNum_departamento() {
        return num_departamento;
    }

    public void setNum_departamento(int num_departamento) {
        this.num_departamento = num_departamento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Proxecto: ")
                .append(nome_proxecto)
                .append(" | Id: ")
                .append(num_proxecto)
                .append(" | Lugar: ")
                .append(lugar)
                .append(" | Departamento: ")
                .append(num_departamento);
        return sb.toString();
    }
}
