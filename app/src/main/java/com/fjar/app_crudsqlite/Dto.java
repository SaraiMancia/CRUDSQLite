package com.fjar.app_crudsqlite;

import java.io.Serializable;

public class Dto implements Serializable {
    int Codigo;
    String Descripcion;
    double precio;
    int idCategoria;

    public Dto() {
    }

    public Dto(int codigo, String descripcion, double precio, int idCategoria) {
        Codigo = codigo;
        Descripcion = descripcion;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }



    public int getIdCategoria(){
        return idCategoria;
    }
    public void setIdCategoria(int idCategoria){
        this.idCategoria = idCategoria;
    }
    public int getCodigo()
    {
        return Codigo;
    }

    public void setCodigo(int codigo)
    {
        this.Codigo = codigo;
    }

    public String getDescripcion()
    {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.Descripcion = descripcion;
    }

    public double getPrecio() {

        return precio;
    }

    public void setPrecio(double precio) {

        this.precio = precio;
    }


}
