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
import javax.persistence.Transient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ramon
 */
@Entity
@Table(name = "valoraciones", schema = "ahpdb")
@NamedQueries({
    @NamedQuery(name = "Valoraciones.findAll", query = "SELECT v FROM Valoracion v"),
    @NamedQuery(name = "Valoraciones.findByIdValoracion", query = "SELECT v FROM Valoracion v WHERE v.idValoracion = :idValoracion"),
    @NamedQuery(name = "Valoraciones.findByValoracion", query = "SELECT v FROM Valoracion v WHERE v.valoracion = :valoracion"),
    @NamedQuery(name = "Valoraciones.findByDirecta", query = "SELECT v FROM Valoracion v WHERE v.directa = :directa"),
    @NamedQuery(name = "Valoraciones.findByCriterioPadre", query = "SELECT v FROM Criterio c join c.valoraciones v WHERE c.idCriterio =:idCriterio"),
    @NamedQuery(name = "Valoraciones.findByCriterioA", query = "SELECT v FROM Criterio c join c.valoracionesA v WHERE c.idCriterio =:idCriterio"),
    @NamedQuery(name = "Valoraciones.findByCriterioB", query = "SELECT v FROM Criterio c join c.valoracionesB v WHERE c.idCriterio =:idCriterio")})
public class Valoracion implements Serializable {

    private static final long serialVersionUID = 4355731229570324140L;

    private static Logger log = LogManager.getRootLogger();
    private static final String[] colores = {"#005dba", "#3fff99", "#93ffc2", "#c1ffde", "#ffffff", "#b5ebff", "#75daff", "#38c9ff", "#00bc58"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_valoracion")
    private Integer idValoracion;

    @Basic(optional = false)
    @Column(name = "valoracion")
    private Integer valoracion;

    @Basic(optional = false)
    @Column(name = "directa")
    private Short directa;

    @JoinColumn(name = "id_criterioPadre", referencedColumnName = "id_criterio")
    @ManyToOne
    private Criterio criterioPadre;

    @JoinColumn(name = "id_criterioA", referencedColumnName = "id_criterio")
    @ManyToOne
    private Criterio criterioA;

    @JoinColumn(name = "id_criterioB", referencedColumnName = "id_criterio")
    @ManyToOne
    private Criterio criterioB;

    @Transient
    int x;
    @Transient
    int y;
    @Transient
    Valoracion simetrica = null;

    public Valoracion() {
        this.valoracion = 1;
        this.directa = (short) 1;
    }

    public Valoracion(Integer idValoracion) {
        this.idValoracion = idValoracion;
    }

    public Integer getIdValoracion() {
        return idValoracion;
    }

    public void setIdValoracion(Integer idValoracion) {
        this.idValoracion = idValoracion;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public Short getDirecta() {
        return directa;
    }

    public void setDirecta(Short directa) {
        this.directa = directa;
    }

    public Criterio getCriterioPadre() {
        return criterioPadre;
    }

    public void setCriterioPadre(Criterio criterio) {
        if (this.criterioPadre != null && this.criterioPadre.getValoraciones() != null && criterio != this.criterioPadre) {
            this.criterioPadre.getValoraciones().remove(this);
        }
        
        if (criterio != null && criterio != this.criterioPadre) {
            this.criterioPadre = criterio; 
            if (criterio.getValoraciones() == null) {
                criterio.setValoraciones(new ArrayList<Valoracion>());
            }
            criterio.getValoraciones().add(this);
        }
    }

    public void setCriterioPadre(Criterio criterio, boolean soloAsignacion) {
        if (!soloAsignacion)
            setCriterioPadre(criterio);
        else
            this.criterioPadre=criterio;
    }
    
    public Criterio getCriterioA() {
        return criterioA;
    }

    public void setCriterioA(Criterio criterioA) {
        if (this.criterioA != null && this.criterioA.getValoracionesA() != null && criterioA != this.criterioA) {
            this.criterioA.getValoracionesA().remove(this);
        }
        
        if (criterioA != null && this.criterioA != criterioA) {
            this.criterioA = criterioA;
            if (criterioA.getValoracionesA() == null) {
                criterioA.setValoracionesA(new ArrayList<Valoracion>());
            }
            criterioA.getValoracionesA().add(this);
        }
    }
    
    public void setCriterioA(Criterio criterioA, boolean soloAsignacion) {
        if (!soloAsignacion)
            setCriterioA(criterioA);
        else
            this.criterioA = criterioA;
    }

    public Criterio getCriterioB() {
        return criterioB;
    }

    public void setCriterioB(Criterio criterioB) {
        if (this.criterioB != null && this.criterioB.getValoracionesB() != null && criterioB != this.criterioB) {
            this.criterioB.getValoracionesB().remove(this);
        }
        
        if (criterioB != null && this.criterioB != criterioB) {
            this.criterioB = criterioB;
            if (criterioB.getValoracionesB() == null) {
                criterioB.setValoracionesB(new ArrayList<Valoracion>());
            }
            criterioB.getValoracionesB().add(this);
        }
    }

    public void setCriterioB(Criterio criterioB, boolean soloAsignacion) {
        if (!soloAsignacion)
            setCriterioB(criterioB);
        else
            this.criterioB=criterioB;
    }
    
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getPos() {
        return "f" + x + "c" + y;
    }

    public void setPos(String pos) {

    }

    public Valoracion getSimetrica() {
        return this.simetrica;
    }

    public void setSimetrica(Valoracion simetrica) {
        this.simetrica = simetrica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValoracion != null ? idValoracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracion)) {
            return false;
        }
        Valoracion other = (Valoracion) object;
        if ((this.idValoracion == null && other.idValoracion != null) || (this.idValoracion != null && !this.idValoracion.equals(other.idValoracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahp.domain.Valoraciones[ idValoracion=" + idValoracion + ", valoracion= " + getVal() + ",idPadre= " + criterioPadre.getIdCriterio()
                + ", criterioA= " + criterioA.getIdCriterio() + ", criterioB= " + criterioB.getIdCriterio() + " ]";
    }

    public String getVal() {
        return (this.directa == 0 ? "1/" : "") + this.valoracion.toString();
    }

    public int getSelectVal() {
        if (this.directa == 1) {
            return ((this.valoracion - 1) / 2 + 4);
        } else {
            return ((9 - this.valoracion) / 2);
        }
    }

    public void setSelectVal(int val) {

        if (this.criterioA != this.criterioB) {
            if (val < 4) {
                this.directa = (short) 0;
                this.valoracion = 9 - val * 2;

                if (this.simetrica != null) {
                    this.simetrica.setDirecta((short) 1);
                    this.simetrica.setValoracion(9 - val * 2);
                }
            } else {
                this.directa = (short) 1;
                this.valoracion = 2 * (val - 4) + 1;
                if (this.simetrica != null) {
                    this.simetrica.directa = this.valoracion == 1 ? (short) 1 : (short) 0;
                    this.simetrica.valoracion = 2 * (val - 4) + 1;
                }
            }
        }
        this.criterioPadre.asignaPesos();

    }

    public void setVal(String val) {

        this.valoracion = Integer.parseInt(val.split("/").length > 1 ? val.split("/")[1] : val);
        this.directa = (short) (val.split("/").length > 1 ? 0 : 1);
        if (this.simetrica != null) {
            this.simetrica.valoracion = Integer.parseInt(val.split("/").length > 1 ? val : val.split("/")[1]);
            this.simetrica.directa = (short) (val.split("/").length > 1 ? 1 : 0);
        }

    }

    public double getNumber() {
        if (directa == (short)1)
            return (double) valoracion;
        else
            return 1.0 / (double) valoracion;
    }

    public String getColor() {
        return colores[getSelectVal()];
    }
}
