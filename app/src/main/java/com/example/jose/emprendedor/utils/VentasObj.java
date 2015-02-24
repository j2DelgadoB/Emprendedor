package com.example.jose.emprendedor.utils;

/**
 * Created by jose on 09/10/2014.
 */
public class VentasObj {
    private String Descripcion;
    private String CantSoles;
    private String CantDolares;

    public VentasObj(String descripcion, String cantSoles, String cantDolares) {
        Descripcion = descripcion;
        CantSoles = cantSoles;
        CantDolares = cantDolares;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCantSoles() {
        return CantSoles;
    }

    public void setCantSoles(String cantSoles) {
        CantSoles = cantSoles;
    }

    public String getCantDolares() {
        return CantDolares;
    }

    public void setCantDolares(String cantDolares) {
        CantDolares = cantDolares;
    }
}
