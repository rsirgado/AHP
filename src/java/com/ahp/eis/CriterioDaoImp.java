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

import com.ahp.domain.Criterio;
import com.ahp.domain.Lista;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ramon
 */
@Stateless
public class CriterioDaoImp implements CriterioDao {
    @PersistenceContext(unitName="com.AHProjectPU")
    private EntityManager em;
    
    /**
     *
     * @return
     */
    @Override
    public List<Criterio> listarCriterios() {
        return em.createNamedQuery("Criterios.findAll").getResultList();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public Criterio buscarCriterioPorId(Criterio criterio) {
        return (Criterio) em.createNamedQuery("Criterios.findByIdCriterio").setParameter("idCriterio", criterio.getIdCriterio()).getSingleResult();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Criterio> buscarCriterioHijos(Criterio criterio) {
        return em.createNamedQuery("Criterios.findHijos").setParameter("idPadre", criterio.getIdCriterio()).getResultList();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public Criterio buscarCriterioPorNombre(Criterio criterio) {
        return (Criterio) em.createNamedQuery("Criterios.findByNombreCriterio").setParameter("nombreCriterio", criterio.getNombreCriterio()).getSingleResult();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Criterio> buscarCriterioPorLista(Lista lista) {
        return em.createNamedQuery("Criterios.findByidLista").setParameter("idLista", lista.getIdLista()).getResultList();
    }

    /**
     *
     * @param criterio
     */
    @Override
    public void agregarCriterio(Criterio criterio) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(criterio);
        tx.commit();
    }

    /**
     *
     * @param criterio
     */
    @Override
    public void actualizarCriterio(Criterio criterio) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Criterio crit = em.find(Criterio.class,criterio.getIdCriterio());
        if (crit != null){
            crit.setPadre(criterio.getPadre(),true);
            crit.setLista(criterio.getLista(),true);
            crit.setNombreCriterio(criterio.getNombreCriterio());
            crit.setDescripcion(criterio.getDescripcion());
            crit.setValoracionesA(criterio.getValoracionesA());
            crit.setValoracionesB(criterio.getValoracionesB());
            crit.setPeso(criterio.getPeso());
        }
        em.merge(crit);
        tx.commit();
    }

    /**
     *
     * @param criterio
     */
    @Override
    public void borrarCriterio(Criterio criterio) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(Criterio.class,criterio.getIdCriterio()));
        tx.commit();
    }
    
    /**
     *
     * @param criterios
     */
    @Override
    public void borrarCriterio(List<Criterio> criterios){
        EntityTransaction tx= em.getTransaction();
        tx.begin();
        for (Iterator<Criterio> it = criterios.iterator(); it.hasNext();) {
            Criterio c = it.next();
            em.remove(em.find(Criterio.class,c.getIdCriterio()));
        }
        tx.commit();
    }
    
    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Criterio> ultimoNivel(Lista lista){
        if(lista!= null){
            Query query = em.createNamedQuery("Criterios.ultimoNivel");
            query.setParameter("idLista", lista.getIdLista());
            return query.getResultList();
        }
        else
            return new ArrayList<Criterio>();
        
    }
}
