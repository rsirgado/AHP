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
import com.ahp.domain.Valoracion;
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
public class ValoracionDaoImp implements ValoracionDao {

    @PersistenceContext(unitName = "com.AHProjectPU")
    EntityManager em;

    /**
     *
     * @return
     */
    @Override
    public List<Valoracion> listarValoraciones() {
        return em.createNamedQuery("Valoraciones.findAll").getResultList();
    }

    /**
     *
     * @param valoracion
     * @return
     */
    @Override
    public Valoracion buscarValoracionesPorId(Valoracion valoracion) {
        return (Valoracion) em.createNamedQuery("Valoraciones.findByIdValoracion").setParameter("idValoracion", valoracion.getIdValoracion()).getSingleResult();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Valoracion> buscarValoracionesByCriterioA(Criterio criterio) {
        return em.createNamedQuery("Valoraciones.findByCriterioA").setParameter("idCriterio", criterio.getIdCriterio()).getResultList();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Valoracion> buscarValoracionesByCriterioB(Criterio criterio) {
        return em.createNamedQuery("Valoraciones.findByCriterioB").setParameter("idCriterio", criterio.getIdCriterio()).getResultList();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Valoracion> buscarValoracionesByCriterioPadre(Criterio criterio) {
        return em.createNamedQuery("Valoraciones.findByCriterioPadre").setParameter("idCriterio", criterio.getIdCriterio()).getResultList();
    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void agregarValoracion(Valoracion valoracion) {

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(valoracion);
        tx.commit();

    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void agregarOActualizaValoracion(Valoracion valoracion) {

        if (valoracion.getIdValoracion() != null) {
            actualizarValoracion(valoracion);

        } else {

            agregarValoracion(valoracion);
        }

    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void actualizarValoracion(Valoracion valoracion) {
        if (valoracion.getIdValoracion() != null) {

            EntityTransaction tx = em.getTransaction();

            tx.begin();
            Valoracion val = em.find(Valoracion.class, valoracion.getIdValoracion());
            if (val != null) {

                val.setValoracion(valoracion.getValoracion());
                val.setDirecta(valoracion.getDirecta());
                val.setCriterioA(valoracion.getCriterioA(),true);
                val.setCriterioB(valoracion.getCriterioB(),true);

            }
            em.merge(val);
            tx.commit();

        }

    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void borrarValoracion(Valoracion valoracion) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(Valoracion.class, valoracion.getIdValoracion()));
        tx.commit();
    }

}
