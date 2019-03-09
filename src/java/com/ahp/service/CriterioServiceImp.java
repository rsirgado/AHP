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
package com.ahp.service;

import com.ahp.domain.Criterio;
import com.ahp.domain.Lista;
import com.ahp.eis.CriterioDao;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ramon
 */
@Stateless
public class CriterioServiceImp implements CriterioService{
    @Inject
    CriterioDao criterioDao;

    /**
     *
     * @return
     */
    @Override
    public List<Criterio> listarCriterios() {
        return criterioDao.listarCriterios();
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public Criterio buscarCriterioPorId(Criterio criterio) {
        return criterioDao.buscarCriterioPorId(criterio);
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Criterio> buscarCriterioHijos(Criterio criterio) {
        return criterioDao.buscarCriterioHijos(criterio);
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public Criterio buscarCriterioPorNombre(Criterio criterio) {
        return criterioDao.buscarCriterioPorNombre(criterio);
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Criterio> buscarCriterioPorLista(Lista lista) {
        return criterioDao.buscarCriterioPorLista(lista);
    }

    /**
     *
     * @param criterio
     */
    @Override
    public void agregarCriterio(Criterio criterio) {
        criterioDao.agregarCriterio(criterio);
    }

    /**
     *
     * @param criterio
     */
    @Override
    public void actualizarCriterio(Criterio criterio) {
        criterioDao.actualizarCriterio(criterio);
    }

    /**
     *
     * @param criterio
     */
    @Override
    public void borrarCriterio(Criterio criterio) {
        criterioDao.borrarCriterio(criterio);
    }
    
    /**
     *
     * @param criterios
     */
    @Override
    public void borrarCriterio(List<Criterio> criterios){
        criterioDao.borrarCriterio(criterios);
    }
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Criterio> ultimoNivel(Lista lista){
        return criterioDao.ultimoNivel(lista);
    }
    
}
