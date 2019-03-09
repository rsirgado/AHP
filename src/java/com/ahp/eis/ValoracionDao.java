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
import javax.ejb.Local;
import java.util.List;




/**
 *
 * @author rsirgado
 */
@Local
public interface ValoracionDao {
    
    /**
     *
     * @return
     */
    public List<Valoracion> listarValoraciones();
    
    /**
     *
     * @param valoracion
     * @return
     */
    public Valoracion buscarValoracionesPorId(Valoracion valoracion);
    
    /**
     *
     * @param criterio
     * @return
     */
    public List<Valoracion> buscarValoracionesByCriterioA(Criterio criterio);

    /**
     *
     * @param criterio
     * @return
     */
    public List<Valoracion> buscarValoracionesByCriterioB(Criterio criterio);
    
    /**
     *
     * @param criterio
     * @return
     */
    public List<Valoracion> buscarValoracionesByCriterioPadre(Criterio criterio);
    
    /**
     *
     * @param valoracion
     */
    public void agregarValoracion(Valoracion valoracion);
    
    /**
     *
     * @param valoracion
     */
    public void agregarOActualizaValoracion(Valoracion valoracion);
    
    /**
     *
     * @param valoracion
     */
    public void actualizarValoracion(Valoracion valoracion);
    
    /**
     *
     * @param valoracion
     */
    public void borrarValoracion(Valoracion valoracion);
}
  