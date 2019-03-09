/*
 * Copyright 2019 Ramon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ahp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ramon
 */
@Entity
@Table(name = "criterios", schema = "ahpdb")
@NamedQueries({
    @NamedQuery(name = "Criterios.findAll", query = "SELECT c FROM Criterio c"),
    @NamedQuery(name = "Criterios.findByIdCriterio", query = "SELECT c FROM Criterio c WHERE c.idCriterio = :idCriterio"),
    @NamedQuery(name = "Criterios.findByNombreCriterio", query = "SELECT c FROM Criterio c WHERE c.nombreCriterio = :nombreCriterio"),
    @NamedQuery(name = "Criterios.findByidLista", query = "SELECT c FROM Lista l join l.criterios c WHERE l.idLista = :idLista"),
    @NamedQuery(name = "Criterios.findHijos", query = "SELECT h from Criterio c JOIN c.hijos h WHERE c.idCriterio = :idPadre"),
    @NamedQuery(name = "Criterios.ultimoNivel", query = "select distinct c.padre from Lista l join l.criterios c where (c not in (select d.padre from Criterio d where d.padre != null)) and (l.idLista=:idLista)")
})
public class Criterio implements Serializable {

    private static final long serialVersionUID = -8074807680145070639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_criterio")
    private Integer idCriterio;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreCriterio")
    private String nombreCriterio;

    @Lob
    @Size(max = 16777215)
    @Column(name = "descripcion")
    private String descripcion;

    @JoinColumn(name = "id_lista", referencedColumnName = "id_listas")
    @ManyToOne(optional = false)
    private Lista lista;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criterioPadre")
    private List<Valoracion> valoraciones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criterioA")
    private List<Valoracion> valoracionesA;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criterioB")
    private List<Valoracion> valoracionesB;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criterio")
    private List<Caracteristica> caracteristicas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "padre")
    private List<Criterio> hijos;

    @JoinColumn(name = "id_padre", referencedColumnName = "id_criterio")
    @ManyToOne
    private Criterio padre;

    @OneToMany(mappedBy = "criterioSeleccionado", cascade = CascadeType.DETACH)
    private List<Caracteristica> datos;

    @Transient
    double peso = 1;

    public Criterio() {
    }

    public Criterio(Integer idCriterio) {
        this.idCriterio = idCriterio;
    }

    public Criterio(Integer idCriterio, int idPadre, String nombreCriterio) {
        this.idCriterio = idCriterio;

        this.nombreCriterio = nombreCriterio;
    }

    public Integer getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(Integer idCriterio) {
        this.idCriterio = idCriterio;
    }

    public Criterio getPadre() {
        return padre;
    }

    public void setPadre(Criterio padre) {
        if (this.padre != null && this.padre.getHijos() != null && this.padre != padre) {
            this.padre.getHijos().remove(this);
        }
        if (padre != null && padre != this.padre) {
            this.padre = padre;
            if (padre.getHijos() == null) {
                padre.setHijos(new ArrayList<Criterio>());
            }
            padre.getHijos().add(this);
            padre.creaMatriz(false);
        }
    }

    public void setPadre(Criterio padre, boolean soloAsignacion){
        if (!soloAsignacion){
            setPadre(padre);
        }else{
            this.padre = padre;
        }
    }
    
    public String getNombreCriterio() {
        return nombreCriterio;
    }

    public void setNombreCriterio(String nombreCriterio) {
        this.nombreCriterio = nombreCriterio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        if (this.lista != null && this.lista.getCriterios() != null && this.lista != lista) {
            this.lista.getCriterios().remove(this);
        }
        
        if (lista != null && lista!= this.lista) {
            this.lista = lista;
            if (lista.getCriterios() == null) {
                lista.setCriterios(new ArrayList<Criterio>());
            }
            lista.getCriterios().add(this);
        }
    }
    
    public void setLista(Lista lista, boolean soloAsignacion) {
        if (!soloAsignacion)
            setLista(lista);
        else
            this.lista=lista;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public Valoracion getValoracionCriterioB(Criterio criterioB) {
        for (Valoracion val : valoracionesA) {
            if (val.getCriterioB() == criterioB) {
                return val;
            }
        }
        return null;
    }

    public void creaMatriz(boolean inicial) {
        int rowPos = 0;
        if (!inicial) {
            if (valoraciones.size() == (hijos.size() * hijos.size())) {
                return;
            }
        }
        for (Criterio critFila : this.getHijos()) {
            int pos = 0;

            if (critFila.getValoracionesA() == null) {
                critFila.setValoracionesA(new ArrayList<Valoracion>());
            }
            if (critFila.getValoracionesB() == null) {
                critFila.setValoracionesB(new ArrayList<Valoracion>());
            }

            boolean encontrado = false;
            for (Criterio critCol : this.getHijos()) {

                if (critCol.getValoracionesA() == null) {
                    critCol.setValoracionesA(new ArrayList<Valoracion>());
                }
                if (critCol.getValoracionesB() == null) {
                    critCol.setValoracionesB(new ArrayList<Valoracion>());
                }

                if (critFila == critCol) {
                    encontrado = true;
                }
                Valoracion val = null;

                for (Valoracion v : critFila.getValoracionesA()) {
                    if (v.getCriterioB() == critCol) {
                        val = v;
                        break;
                    }
                }

                if (val == null) {
                    val = new Valoracion();
                    val.setCriterioPadre(this);
                    val.setCriterioA(critFila);
                    val.setCriterioB(critCol);
                    val.setValoracion(1);
                    val.setDirecta((short) 1);

                    this.getValoraciones().add(val);
                    critFila.getValoracionesA().add(pos, val);
                    critCol.getValoracionesB().add(rowPos, val);

                }

                if (!encontrado && critFila != critCol) {

                    for (Valoracion valS : critFila.getValoracionesB()) {
                        if (valS.getCriterioA() == critCol) {
                            val.setSimetrica(valS);
                            val.getSimetrica().setSimetrica(val);
                            val.setValoracion(valS.getValoracion());
                            val.setDirecta((short) ((valS.getDirecta() == (short) 1) && valS.getValoracion() != 1 ? 0 : 1));
                        }
                    }
                }
                pos++;
            }
            rowPos++;
        }

        asignaPesos();

    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public List<Valoracion> getValoracionesA() {
        return valoracionesA;
    }

    public void setValoracionesA(List<Valoracion> valoracionesA) {
        this.valoracionesA = valoracionesA;
    }

    public List<Valoracion> getValoracionesB() {
        return valoracionesB;
    }

    public void setValoracionesB(List<Valoracion> valoracionesB) {
        this.valoracionesB = valoracionesB;
    }

    public double getPeso() {
        if (this.peso != 0) {
            return this.peso;
        } else if (padre != null) {
            return 1 /  (padre.valoraciones.size() == 0 ? (double) 1 : (double) padre.valoraciones.size());
        } else {
            return (double) 1;
        }
    }

    public double getPesoPonderado() {
        return getPeso() * ((padre == null) ? 1 : padre.getPesoPonderado());
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCriterio != null ? idCriterio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criterio)) {
            return false;
        }
        Criterio other = (Criterio) object;
        if ((this.idCriterio == null && other.idCriterio != null) || (this.idCriterio != null && !this.idCriterio.equals(other.idCriterio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahp.domain.Criterios[ idCriterio=" + idCriterio
                + " Nombre: " + nombreCriterio
                + " Peso: " + peso
                + " Nº hijos: " + hijos.size()
                + " Nº valoraciones: " + valoraciones.size()
                + " Nº valoracionesA: " + valoracionesA.size()
                + " Nº valoracionesB: " + valoracionesB.size()
                + " ]";
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<Criterio> getHijos() {
        return hijos;
    }

    public void setHijos(List<Criterio> hijos) {
        this.hijos = hijos;
    }

    public void asignaPesos() {
        double[] sumas = new double[hijos.size()];
        double[] pesosHijos = new double[hijos.size()];

        int i = 0;
        // suma por columnas
        for (Criterio hijo : hijos) {
            for (Criterio hijoFila : hijos) {
                for (Valoracion val : hijo.getValoracionesB()) {
                    if (val.getCriterioA() == hijoFila) {
                        sumas[i] += val.getNumber();
                        break;
                    }
                }
            }
            i++;
        }
        i = 0;
        // promedio por fila
        for (Criterio hijo : hijos) {
            int j = 0;
            for (Criterio hijoColumna : hijos) {
                for (Valoracion val : hijo.getValoracionesA()) {
                    if (val.getCriterioB() == hijoColumna) {
                        pesosHijos[i] += (val.getNumber() / sumas[j]);
                        j++;
                        break;
                    }
                }
            }
            hijo.setPeso(pesosHijos[i] / hijos.size());
            i++;
        }
    }

    public String getPesoS() {
        return String.format("%4.3f", getPeso());
    }

    public String getPesoPonderadoS() {
        return String.format("%4.3f", getPesoPonderado());
    }

    public List<Caracteristica> getDatos() {
        return this.datos;
    }

    public void setDatos(List<Caracteristica> datos) {
        this.datos = datos;
    }

    public int getNietos() {
        int nietos = 0;
        if (hijos.isEmpty()) {
            return -1;
        }
        for (Criterio hijo : hijos) {
            nietos += hijo.getHijos()==null?0:hijo.getHijos().size();
        }
        return nietos;
    }

    public boolean isCabecera() {
        if (this.hijos.isEmpty()) {
            return false;
        } else {
            return this.getNietos() == 0;
        }
    }
}
