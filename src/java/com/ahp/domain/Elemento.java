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
 * @author rsirgado
 */
@Entity
@Table(name = "elemento", schema = "ahpdb")
@NamedQueries({
    @NamedQuery(name = "Elemento.findAll", query = "SELECT e FROM Elemento e"),
    @NamedQuery(name = "Elemento.findByIdElemento", query = "SELECT e FROM Elemento e WHERE e.idElemento = :idElemento"),
    @NamedQuery(name = "Elemento.findByNombreElemento", query = "SELECT e FROM Elemento e WHERE e.nombreElemento = :nombreElemento"),
    @NamedQuery(name = "Elemento.findByidLista", query = "SELECT e FROM Lista l JOIN l.elementos e WHERE l.idLista = :idLista")})
public class Elemento implements Serializable {

    private static final long serialVersionUID = 1947785824531015892L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_Elemento")
    private Integer idElemento;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreElemento")
    private String nombreElemento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "elemento")
    private List<Caracteristica> caracteristicas;

    @JoinColumn(name = "id_Lista", referencedColumnName = "id_listas")
    @ManyToOne(optional = false)
    private Lista lista;

    @Transient
    private double puntuacion;

    public Elemento() {
    }

    public Elemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public Elemento(Integer idElemento, String nombreElemento) {
        this.idElemento = idElemento;
        this.nombreElemento = nombreElemento;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public Caracteristica getCaracteristicaCriterio(Criterio crit) {
        if (crit == null) {
            return null;
        }
        for (Caracteristica car : caracteristicas) {
            if (car.getCriterio() == crit) {
                return car;
            }
        }
        Caracteristica nueva = new Caracteristica();
        nueva.setElemento(this);
        caracteristicas.add(nueva);
        nueva.setCriterio(crit);
        nueva.getCriterio().getCaracteristicas().add(nueva);
        nueva.setCriterioSeleccionado(null);

        return nueva;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        if (this.lista != null && this.lista.getElementos() != null && lista != this.lista) {
            this.lista.getElementos().remove(this);
        }        
        if (lista != null && lista != this.lista) {
            this.lista = lista;
            if (lista.getElementos() == null) {
                lista.setElementos(new ArrayList<Elemento>());
            }
            lista.getElementos().add(this);
        }
    }

    public void setLista(Lista lista, boolean soloAsignacion) {
        if (!soloAsignacion)
            setLista(lista);
        else
            this.lista=lista;
    }
    
    public double getPuntuacion() {
        return this.puntuacion;
    }

    public String getPuntuacionS() {
        return String.format("%4.3f", puntuacion);
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void calculaPuntuacion() {
        double pto = 0;
        for (Caracteristica car : caracteristicas) {
            if (car.getCriterio()!= null && car.getCriterio().isCabecera()) {
                if (car.getCriterioSeleccionado() == null) {
                    pto = 0;
                    break;
                } else {
                    pto += car.getCriterioSeleccionado().getPesoPonderado();
                }
            }
        }
        puntuacion = pto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idElemento != null ? idElemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Elemento)) {
            return false;
        }
        Elemento other = (Elemento) object;
        if ((this.idElemento == null && other.idElemento != null) || (this.idElemento != null && !this.idElemento.equals(other.idElemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahp.domain.Elemento[ idElemento=" + idElemento + " ]";
    }

}
