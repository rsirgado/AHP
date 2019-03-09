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

import com.ahp.domain.Elemento;
import com.ahp.domain.Lista;
import java.util.Iterator;
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
public class ElementoDaoImp implements ElementoDao {
    @PersistenceContext(unitName="com.AHProjectPU")
    private EntityManager em;
    
    /**
     *
     * @return
     */
    @Override
    public List<Elemento> listarElementos(){
        return em.createNamedQuery("Elemento.findAll").getResultList();
    }
    
    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Elemento buscarElementoPorId(Elemento elemento) {
        return (Elemento) em.createNamedQuery("Elemento.findByIdElemento").setParameter("idElemento", elemento.getIdElemento()).getSingleResult();
    }

    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Elemento buscarElementoPorNombre(Elemento elemento){
        return (Elemento) em.createNamedQuery("Elemento.findByNombreElemento").setParameter("nombreElemento", elemento.getNombreElemento()).getSingleResult();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Elemento>  buscarElementoPorLista(Lista lista){
        return em.createNamedQuery("Elemento.findByidLista").setParameter("idLista", lista.getIdLista()).getResultList();
    }

    /**
     *
     * @param elemento
     */
    @Override
    public void agregarElemento(Elemento elemento) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(elemento);
        tx.commit();
    }

    /**
     *
     * @param elemento
     */
    @Override
    public void actualizarElemento(Elemento elemento) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Elemento el = em.find(Elemento.class,elemento.getIdElemento());
        if (el != null){
            el.setLista(elemento.getLista(),true);
            el.setNombreElemento(elemento.getNombreElemento());
            el.setCaracteristicas(elemento.getCaracteristicas());
        }
        em.merge(el);
        tx.commit();
    }
    
    /**
     *
     * @param elemento
     */
    @Override
    public void borrarElemento(Elemento elemento) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(Elemento.class,elemento.getIdElemento()));
        tx.commit();
    }
    
    /**
     *
     * @param elementos
     */
    @Override
    public void borrarElemento(List<Elemento> elementos){
        EntityTransaction tx= em.getTransaction();
        tx.begin();
        for (Iterator<Elemento> it = elementos.iterator(); it.hasNext();) {
            Elemento el = it.next();
            em.remove(em.find(Elemento.class,el.getIdElemento()));
        }
        tx.commit();
    }
    

}
