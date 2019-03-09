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
import com.ahp.domain.Valoracion;
import com.ahp.eis.ValoracionDao;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ramon
 */
@Stateless
public class ValoracionServiceImp implements ValoracionService {

    @Inject
    ValoracionDao valoracionDao;

    /**
     *
     * @return
     */
    @Override
    public List<Valoracion> listarValoraciones() {
        return valoracionDao.listarValoraciones();
    }

    /**
     *
     * @param valoracion
     * @return
     */
    @Override
    public Valoracion buscarValoracionesPorId(Valoracion valoracion) {
        return valoracionDao.buscarValoracionesPorId(valoracion);
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Valoracion> buscarValoracionesByCriterioA(Criterio criterio) {
        return valoracionDao.buscarValoracionesByCriterioA(criterio);
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Valoracion> buscarValoracionesByCriterioB(Criterio criterio) {
        return valoracionDao.buscarValoracionesByCriterioB(criterio);
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public List<Valoracion> buscarValoracionesByCriterioPadre(Criterio criterio) {
        return valoracionDao.buscarValoracionesByCriterioPadre(criterio);
    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void agregarValoracion(Valoracion valoracion) {
        valoracionDao.agregarValoracion(valoracion);
    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void agregarOActualizaValoracion(Valoracion valoracion) {

        valoracionDao.agregarOActualizaValoracion(valoracion);
    }

    /**
     *
     * @param valoraciones
     */
    @Override
    public void agregarOActualizaValoracion(List<Valoracion> valoraciones) {
        for (Valoracion val : valoraciones) {
            agregarOActualizaValoracion(val);
        }
    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void actualizarValoracion(Valoracion valoracion) {
        valoracionDao.actualizarValoracion(valoracion);
    }

    /**
     *
     * @param valoracion
     */
    @Override
    public void borrarValoracion(Valoracion valoracion) {
        valoracionDao.borrarValoracion(valoracion);
    }

}
