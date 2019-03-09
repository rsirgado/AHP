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

import com.ahp.domain.Caracteristica;
import com.ahp.domain.Criterio;
import com.ahp.domain.Elemento;
import com.ahp.eis.CaracteristicaDao;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ramon
 */
@Stateless
public class CaracteristicaServiceImp implements CaracteristicaService{
    @Inject
    CaracteristicaDao caracteristicaDao;

    /**
     *
     * @return
     */
    @Override
    public List<Caracteristica> listarCaracteristicas(){
        return caracteristicaDao.listarCaracteristicas();
    }

    /**
     *
     * @param caracteristica
     * @return
     */
    @Override
    public Caracteristica buscarCaracteristicaPorId(Caracteristica caracteristica) {
        return caracteristicaDao.buscarCaracteristicaPorId(caracteristica);
    }

    /**
     *
     * @param criterio
     * @return
     */
    @Override
    public Caracteristica buscarCaracteristicaPorCriterio(Criterio criterio){
        return caracteristicaDao.buscarCaracteristicaPorCriterio(criterio);
    }

    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Caracteristica buscarCaracteristicaPorElemento(Elemento elemento){
        return caracteristicaDao.buscarCaracteristicaPorElemento(elemento);
    }

    /**
     *
     * @param caracteristica
     */
    @Override
    public void agregarCaracteristica(Caracteristica caracteristica){
        caracteristicaDao.agregarCaracteristica(caracteristica);
    }

    /**
     *
     * @param caracteristica
     */
    @Override
    public void actualizarCaracteristica(Caracteristica caracteristica){
        caracteristicaDao.actualizarCaracteristica(caracteristica);
    }

    /**
     *
     * @param caracteristica
     */
    @Override
    public void borrarCaracteristica(Caracteristica caracteristica){
        caracteristicaDao.borrarCaracteristica(caracteristica);
    }
    
    /**
     *
     * @param caracteristicas
     */
    @Override
    public void borrarCaracteristica(List<Caracteristica> caracteristicas){
        caracteristicaDao.borrarCaracteristica(caracteristicas);
    }
    
    /**
     *
     * @param caracteristica
     */
    @Override
    public void guardaActualizaCaracteristica(Caracteristica caracteristica){
        if(caracteristica.getIdCaracteristica()==null)
            agregarCaracteristica(caracteristica);
        else
            actualizarCaracteristica(caracteristica);
    }
    
    /**
     *
     * @param caracteristicas
     */
    @Override
    public void guardaActualizaCaracteristica(List<Caracteristica> caracteristicas){
        for(Caracteristica car:caracteristicas)
            guardaActualizaCaracteristica(car);
    }
    
}
