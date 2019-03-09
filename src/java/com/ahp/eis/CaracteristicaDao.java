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
import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author Ramon
 */
@Local
public interface CaracteristicaDao {

    /**
     *
     * @return
     */
    public List<Caracteristica> listarCaracteristicas();
    
    /**
     *
     * @param caracteristica
     * @return
     */
    public Caracteristica buscarCaracteristicaPorId(Caracteristica caracteristica);
    
    /**
     *
     * @param criterio
     * @return
     */
    public Caracteristica buscarCaracteristicaPorCriterio(Criterio criterio);
    
    /**
     *
     * @param elemento
     * @return
     */
    public Caracteristica buscarCaracteristicaPorElemento(Elemento elemento);
    
    /**
     *
     * @param caracteristica
     */
    public void agregarCaracteristica(Caracteristica caracteristica);
    
    /**
     *
     * @param caracteristica
     */
    public void actualizarCaracteristica(Caracteristica caracteristica);
    
    /**
     *
     * @param caracteristica
     */
    public void borrarCaracteristica(Caracteristica caracteristica);
    
    /**
     *
     * @param caracteristica
     */
    public void borrarCaracteristica(List<Caracteristica> caracteristica);
}
  