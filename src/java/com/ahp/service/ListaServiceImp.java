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
import com.ahp.domain.Usuario;
import com.ahp.eis.ListaDao;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ramon
 */
@Stateless
public class ListaServiceImp implements ListaService {

    @Inject
    ListaDao listaDao;
    
    /**
     *
     * @return
     */
    @Override
    public List<Lista> listarListas() {
        return listaDao.listarListas();
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public Lista buscarListaPorId(Lista lista) {
        return listaDao.buscarListaPorId(lista);
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public Lista buscarListaPorNombre(Lista lista) {
        return listaDao.buscarListaPorNombre(lista);
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaPorFechaCreacion(Lista lista) {
        return listaDao.buscarListaPorFechaCreacion(lista);
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaAnteriorFecha(Lista lista) {
        return listaDao.buscarListaAnteriorFecha(lista);
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaPosteriorFecha(Lista lista) {
        return listaDao.buscarListaPosteriorFecha(lista);
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public List<Lista> buscarListaPorUsuario(Usuario usuario) {
        return listaDao.buscarListaPorUsuario(usuario);
    }
    
    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Lista> buscarListaPorNombreYUsuario(Lista lista){
        return listaDao.buscarListaPorNombreYUsuario(lista);
    }

    /**
     *
     * @param lista
     */
    @Override
    public void agregarLista(Lista lista) {
        listaDao.agregarLista(lista);
    }

    /**
     *
     * @param lista
     */
    @Override
    public void actualizarLista(Lista lista) {
        if (lista != null)
            listaDao.actualizarLista(lista);
    }

    /**
     *
     * @param lista
     */
    @Override
    public void borrarLista(Lista lista) {
        listaDao.borrarLista(lista);
    }

    
}
