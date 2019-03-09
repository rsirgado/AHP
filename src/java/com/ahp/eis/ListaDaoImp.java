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

package com.ahp.eis;

import com.ahp.domain.Lista;
import com.ahp.domain.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ramon
 */
@Stateless
public class ListaDaoImp implements ListaDao{

    @PersistenceContext(unitName = "com.AHProjectPU")
    private EntityManager em;
    
    /**
     *
     * @return
     */
    @Override
    public List<Lista> listarListas() {
        return em.createNamedQuery("Lista.findAll").getResultList();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public Lista buscarListaPorId(Lista lista) {
        return (Lista) em.createNamedQuery("Listas.findByIdListas").setParameter("idLista",lista.getIdLista()).getSingleResult();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public Lista buscarListaPorNombre(Lista lista) {
        return (Lista) em.createNamedQuery("Lista.findByNombreLista").setParameter("nombreLista",lista.getNombreLista()).getSingleResult();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaPorFechaCreacion(Lista lista) {
        return em.createNamedQuery("Lista.findByFechaCreacion").setParameter("fechaCreacion",lista.getFechaCreacion()).getResultList();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaAnteriorFecha(Lista lista) {
        return em.createNamedQuery("Lista.findOlderThanFechaCreacion").setParameter("fechaCreacion",lista.getFechaCreacion()).getResultList();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaPosteriorFecha(Lista lista) {
        return em.createNamedQuery("Lista.findYoungerThanFechaCreacion").setParameter("fechaCreacion",lista.getFechaCreacion()).getResultList();
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public List<Lista> buscarListaPorUsuario(Usuario usuario) {
        return em.createNamedQuery("Lista.findByIdUsuario").setParameter("idUsuario",usuario.getIdUsuario()).getResultList();
    }
    
    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaPorNombreYUsuario(Lista lista){
        return em.createNamedQuery("Lista.findByNombreListaAndIdUsuario")
                .setParameter("idUsuario",lista.getUsuario().getIdUsuario())
                .setParameter("nombreLista", lista.getNombreLista())
                .getResultList();
    }

    /**
     *
     * @param lista
     */
    @Override
    public void agregarLista(Lista lista) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(lista);
        tx.commit();
    }

    /**
     *
     * @param lista
     */
    @Override
    public void actualizarLista(Lista lista) {
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Lista l = em.find(Lista.class,lista.getIdLista());
        if (l != null){
            l.setNombreLista(lista.getNombreLista());
            l.setFechaCreacion(lista.getFechaCreacion());
            l.setUsuario(lista.getUsuario(),true);
        }
        em.merge(l);
        tx.commit();
    }

    /**
     *
     * @param lista
     */
    @Override
    public void borrarLista(Lista lista) {
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        em.remove(em.find(Lista.class, lista.getIdLista()));
        tx.commit();
    }
    
    

}
