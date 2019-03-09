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

import com.ahp.domain.Elemento;
import com.ahp.domain.Lista;
import com.ahp.eis.ElementoDao;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 * @author Ramon
 */
@Stateless
public class ElementoServiceImp implements ElementoService{
    @Inject
    ElementoDao elementoDao;

    /**
     *
     * @return
     */
    @Override
    public List<Elemento> listarElementos() {
        return elementoDao.listarElementos();
    }

    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Elemento buscarElementoPorId(Elemento elemento) {
        return elementoDao.buscarElementoPorId(elemento);
    }

    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Elemento buscarElementoPorNombre(Elemento elemento){
        return elementoDao.buscarElementoPorNombre(elemento);
    }

    /**
     *
     * @param lista
     * @return
     */
    @Override
    public List<Elemento>  buscarElementoPorLista(Lista lista) {
        return elementoDao.buscarElementoPorLista(lista);
    }

    /**
     *
     * @param elemento
     */
    @Override
    public void agregarElemento(Elemento elemento) {
        elementoDao.agregarElemento(elemento);
    }

    /**
     *
     * @param elemento
     */
    @Override
    public void actualizarElemento(Elemento elemento){
        elementoDao.actualizarElemento(elemento);
    }

    /**
     *
     * @param elemento
     */
    @Override
    public void borrarElemento(Elemento elemento) {
        elementoDao.borrarElemento(elemento);
    }
    
    /**
     *
     * @param elementos
     */
    @Override
    public void borrarElemento(List<Elemento> elementos){
        elementoDao.borrarElemento(elementos);
    }

    /**
     *
     * @param elemento
     */
    @Override 
    public void guardaActualizaElemento(Elemento elemento){
        if(elemento.getIdElemento()==null)
            elementoDao.agregarElemento(elemento);
        else
            elementoDao.actualizarElemento(elemento);
             
    }
    
    /**
     *
     * @param elementos
     */
    @Override 
    public void guardaActualizaElemento(List<Elemento> elementos){
        for (Elemento el:elementos)
            guardaActualizaElemento(el);
    }
    
    
}
