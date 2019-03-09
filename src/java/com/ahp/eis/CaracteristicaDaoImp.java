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

import com.ahp.domain.Caracteristica;
import com.ahp.domain.Criterio;
import com.ahp.domain.Elemento;
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
public class CaracteristicaDaoImp implements CaracteristicaDao {
    @PersistenceContext(unitName="com.AHProjectPU")
    private EntityManager em;
    
    /**
     *
     * @return
     */
    @Override
    public List<Caracteristica> listarCaracteristicas() {
        return em.createNamedQuery("Caracteristica.findAll").getResultList();
    }

    /**
     *
     * @param caracteristica
     * @return
     */
    @Override
    public Caracteristica buscarCaracteristicaPorId(Caracteristica caracteristica) {
        return (Caracteristica) em.createNamedQuery("Caracteristica.findByIdCaracteristica").setParameter("idCaracteristica", caracteristica.getIdCaracteristica()).getSingleResult();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public Caracteristica buscarCaracteristicaPorCriterio(Criterio criterio) {
        return (Caracteristica) em.createNamedQuery("Caracteristica.findByidCriterio").setParameter("idCriterio", criterio.getIdCriterio()).getSingleResult();
    }

    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Caracteristica buscarCaracteristicaPorElemento(Elemento elemento) {
        return (Caracteristica) em.createNamedQuery("Caracteristica.findByidElemento").setParameter("idElemento", elemento.getIdElemento()).getSingleResult();
    }

    /**
     *
     * @param caracteristica
     */
    @Override
    public void agregarCaracteristica(Caracteristica caracteristica) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(caracteristica);
        tx.commit();
    }

    /**
     *
     * @param caracteristica
     */
    @Override
    public void actualizarCaracteristica(Caracteristica caracteristica) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Caracteristica car = em.find(Caracteristica.class,caracteristica.getIdCaracteristica());
        if (car != null){
            car.setCriterio(caracteristica.getCriterio(),true);
            car.setCriterioSeleccionado(caracteristica.getCriterioSeleccionado(),true);
            car.setElemento(caracteristica.getElemento(),true);
        }
        em.merge(car);
        tx.commit();
    }

    /**
     *
     * @param caracteristica
     */
    @Override
    public void borrarCaracteristica(Caracteristica caracteristica) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(Caracteristica.class,caracteristica.getIdCaracteristica()));
        tx.commit();
    }
    
    /**
     *
     * @param caracteristicas
     */
    @Override
    public void borrarCaracteristica(List<Caracteristica> caracteristicas){
        EntityTransaction tx= em.getTransaction();
        tx.begin();
        for (Iterator<Caracteristica> it = caracteristicas.iterator(); it.hasNext();) {
            Caracteristica c = it.next();
            em.remove(em.find(Criterio.class,c.getIdCaracteristica()));
        }
        tx.commit();
    }
}
