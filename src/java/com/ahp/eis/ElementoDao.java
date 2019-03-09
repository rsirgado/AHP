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
import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author Ramon
 */
@Local
public interface ElementoDao {

    /**
     *
     * @return
     */
    public List<Elemento> listarElementos();
    
    /**
     *
     * @param elemento
     * @return
     */
    public Elemento buscarElementoPorId(Elemento elemento);
    
    /**
     *
     * @param elemento
     * @return
     */
    public Elemento buscarElementoPorNombre(Elemento elemento);
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Elemento>  buscarElementoPorLista(Lista lista);
    
    /**
     *
     * @param elemento
     */
    public void agregarElemento(Elemento elemento);
    
    /**
     *
     * @param elemento
     */
    public void actualizarElemento(Elemento elemento);
    
    /**
     *
     * @param elemento
     */
    public void borrarElemento(Elemento elemento);
    
    /**
     *
     * @param elementos
     */
    public void borrarElemento(List<Elemento> elementos);
}
  