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
import javax.ejb.Local;
import java.util.List;


/**
 *
 * @author rsirgado
 */
@Local
public interface CriterioDao {

    /**
     *
     * @return
     */
    public List<Criterio> listarCriterios();
    
    /**
     *
     * @param criterio
     * @return
     */
    public Criterio buscarCriterioPorId(Criterio criterio);
    
    /**
     *
     * @param criterio
     * @return
     */
    public List<Criterio> buscarCriterioHijos(Criterio criterio);

    /**
     *
     * @param criterio
     * @return
     */
    public Criterio buscarCriterioPorNombre(Criterio criterio);
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Criterio>  buscarCriterioPorLista(Lista lista);
    
    /**
     *
     * @param criterio
     */
    public void agregarCriterio(Criterio criterio);
    
    /**
     *
     * @param criterio
     */
    public void actualizarCriterio(Criterio criterio);
    
    /**
     *
     * @param criterio
     */
    public void borrarCriterio(Criterio criterio);
    
    /**
     *
     * @param criterios
     */
    public void borrarCriterio(List<Criterio> criterios);
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Criterio> ultimoNivel(Lista lista);
}
  