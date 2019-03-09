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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ramon
 */
@Entity
@Table(name = "listas", schema = "ahpdb")
@NamedQueries({
    @NamedQuery(name = "Lista.findAll", query = "SELECT l FROM Lista l"),
    @NamedQuery(name = "Lista.findByIdLista", query = "SELECT l FROM Lista l WHERE l.idLista = :idLista"),
    @NamedQuery(name = "Lista.findByNombreLista", query = "SELECT l FROM Lista l WHERE l.nombreLista = :nombreLista"),
    @NamedQuery(name = "Lista.findByFechaCreacion", query = "SELECT l FROM Lista l WHERE l.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Lista.findOlderThanFechaCreacion", query = "SELECT l FROM Lista l WHERE l.fechaCreacion <= :fechaCreacion"),
    @NamedQuery(name = "Lista.findYoungerThanFechaCreacion", query = "SELECT l FROM Lista l WHERE l.fechaCreacion >= :fechaCreacion"),
    @NamedQuery(name = "Lista.findByIdUsuario", query = "SELECT l FROM Usuario u join u.listas l WHERE u.idUsuario =:idUsuario"),
    @NamedQuery(name = "Lista.findByNombreListaAndIdUsuario", query = "SELECT l FROM Usuario u join u.listas l WHERE l.nombreLista = :nombreLista and u.idUsuario =:idUsuario")})
public class Lista implements Serializable {

    private static final long serialVersionUID = 100822137291828106L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_listas")
    private Integer idLista;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreLista")
    private String nombreLista;

    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lista", fetch = FetchType.EAGER)
    private List<Criterio> criterios;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lista", fetch = FetchType.EAGER)
    private List<Elemento> elementos;

    public Lista() {
        fechaCreacion = new Date();
    }

    public Lista(Integer idLista) {
        this.idLista = idLista;
    }

    public Lista(Integer idLista, String nombreLista, Date fechaCreacion) {
        this.idLista = idLista;
        this.nombreLista = nombreLista;
        this.fechaCreacion = (Date)fechaCreacion.clone();
    }

    public Integer getIdLista() {
        return idLista;
    }

    public void setIdLista(Integer idLista) {
        this.idLista = idLista;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public Date getFechaCreacion() {
        return (Date)fechaCreacion.clone();
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = (Date)fechaCreacion.clone();
    }

    public List<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<Criterio> criterios) {
        this.criterios = criterios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != null && this.usuario.getListas() != null) {
            this.usuario.getListas().remove(this);
        }
        this.usuario = usuario;
        if (usuario != null) {
            if (usuario.getListas() == null) {
                usuario.setListas(new ArrayList<Lista>());
            }
            usuario.getListas().add(this);
        }
    }
    
    public void setUsuario(Usuario usuario, boolean soloAsignacion) {
        if(!soloAsignacion)
            setUsuario(usuario);
        else
            this.usuario=usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLista != null ? idLista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lista)) {
            return false;
        }
        Lista other = (Lista) object;
        if ((this.idLista == null && other.idLista != null) || (this.idLista != null && !this.idLista.equals(other.idLista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ahp.domain.Listas[ idListas=" + idLista + ", nombreLista= " + nombreLista + " ]";
    }

    public List<Elemento> getElementos() {
        return elementos;
    }

    public void setElementos(List<Elemento> elementos) {
        this.elementos = elementos;
    }

}
