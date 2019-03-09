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

import com.ahp.domain.Lista;
import javax.ejb.Local;
import java.util.List;
import com.ahp.domain.Usuario;


/**
 *
 * @author rsirgado
 */
@Local
public interface ListaService {
    
    /**
     *
     * @return
     */
    public List<Lista> listarListas();
    
    /**
     *
     * @param lista
     * @return
     */
    public Lista buscarListaPorId(Lista lista);
    
    /**
     *
     * @param lista
     * @return
     */
    public Lista buscarListaPorNombre(Lista lista);
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Lista>  buscarListaPorFechaCreacion(Lista lista);
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Lista>  buscarListaAnteriorFecha(Lista lista);
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Lista>  buscarListaPosteriorFecha(Lista lista);
    
    /**
     *
     * @param usuario
     * @return
     */
    public List<Lista>  buscarListaPorUsuario(Usuario usuario);
    
    /**
     *
     * @param lista
     * @return
     */
    public List<Lista> buscarListaPorNombreYUsuario(Lista lista);
    
    /**
     *
     * @param lista
     */
    public void agregarLista(Lista lista);
    
    /**
     *
     * @param lista
     */
    public void actualizarLista(Lista lista);
    
    /**
     *
     * @param lista
     */
    public void borrarLista(Lista lista);

}
  