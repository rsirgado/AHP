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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rsirgado
 */
@Entity
@Table(name = "caracteristica", schema = "ahpdb")
@NamedQueries({
    @NamedQuery(name = "Caracteristica.findAll", query = "SELECT c FROM Caracteristica c"),
    @NamedQuery(name = "Caracteristica.findByIdCaracteristica", query = "SELECT c FROM Caracteristica c WHERE c.idCaracteristica = :idCaracteristica"),
    @NamedQuery(name = "Caracteristica.findByidCriterio", query = "SELECT ca FROM Criterio c join c.caracteristicas ca WHERE c.idCriterio = :idCriterio"),
    @NamedQuery(name = "Caracteristica.findByidElemento", query = "SELECT ca FROM Elemento e join e.caracteristicas ca WHERE e.idElemento= :idElemento")
})
public class Caracteristica implements Serializable {

    private static final long serialVersionUID = -4832482687189335903L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_Caracteristica")
    private Integer idCaracteristica;

    @JoinColumn(name = "id_Criterio", referencedColumnName = "id_criterio")
    @ManyToOne(optional = false)
    private Criterio criterio;

    @JoinColumn(name = "id_Elemento", referencedColumnName = "id_Elemento")
    @ManyToOne(optional = false)
    private Elemento elemento;

    @JoinColumn(name = "id_CriterioSeleccionado", referencedColumnName = "id_criterio")
    @ManyToOne
    private Criterio criterioSeleccionado;

    public Caracteristica() {
    }

    public Caracteristica(Integer idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public Integer getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(Integer idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        if (this.criterio != null && this.criterio.getCaracteristicas() != null && criterio != this.criterio) {
            this.criterio.getCaracteristicas().remove(this);
        }
        if (criterio != null && criterio != this.criterio) {
            this.criterio = criterio;
            if (criterio.getCaracteristicas() == null) {
                criterio.setCaracteristicas(new ArrayList<Caracteristica>());
            }
            criterio.getCaracteristicas().add(this);
        }
    }
    
    public void setCriterio(Criterio criterio, boolean soloAsignacion) {
        if (!soloAsignacion)
            setCriterio(criterio);
        else
            this.criterio=criterio;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        if (this.elemento != null && this.elemento.getCaracteristicas() != null && elemento!= this.elemento) {
            this.elemento.getCaracteristicas().remove(this);
        }
        if (elemento != null && elemento!=this.elemento) {
            this.elemento = elemento;
            if (elemento.getCaracteristicas() == null) {
                elemento.setCaracteristicas(new ArrayList<Caracteristica>());
            }
            elemento.getCaracteristicas().add(this);
            elemento.calculaPuntuacion();
        }
    }
    
    public void setElemento(Elemento elemento, boolean soloAsignacion) {
        if (!soloAsignacion)
            setElemento(elemento);
        else
            this.elemento=elemento;
    }

    public Criterio getCriterioSeleccionado() {
        return this.criterioSeleccionado;
    }

    public void setCriterioSeleccionado(Criterio criterio) {
        if (this.criterioSeleccionado != null && this.criterioSeleccionado.getDatos() != null && criterio != this.criterioSeleccionado) {
            this.criterioSeleccionado.getDatos().remove(this);
        }
        if (criterio != null && criterio != this.criterioSeleccionado) {
            this.criterioSeleccionado = criterio;
            if (criterio.getDatos() == null) {
                criterio.setDatos(new ArrayList<Caracteristica>());
            }
            criterio.getDatos().add(this);
            if (elemento != null) {
                elemento.calculaPuntuacion();
            }
        }
    }

    public void setCriterioSeleccionado(Criterio criterio, boolean soloAsignacion) {
        if (!soloAsignacion)
            setCriterioSeleccionado(criterio);
        else
            this.criterioSeleccionado=criterio;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaracteristica != null ? idCaracteristica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caracteristica)) {
            return false;
        }
        Caracteristica other = (Caracteristica) object;
        if ((this.idCaracteristica == null && other.idCaracteristica != null) || (this.idCaracteristica != null && !this.idCaracteristica.equals(other.idCaracteristica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahp.domain.Caracteristica[ idCaracteristica=" + idCaracteristica + " ]";
    }

    public void setVal(int valor) {
        for (Criterio hijo : criterio.getHijos()) {
            if (hijo.getIdCriterio() == valor) {
                this.criterioSeleccionado = hijo;
                break;
            }
        }
        elemento.calculaPuntuacion();
    }

    public int getVal() {
        if (this.criterioSeleccionado == null) {
            return -1;
        }
        return this.criterioSeleccionado.getIdCriterio();
    }

}
