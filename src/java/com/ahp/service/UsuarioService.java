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

import javax.ejb.Local;
import java.util.List;
import com.ahp.domain.Usuario;


/**
 *
 * @author rsirgado
 */
@Local
public interface UsuarioService {
    
    /**
     *
     * @return
     */
    public List<Usuario> listarUsuarios();
    
    /**
     *
     * @param usuario
     * @return
     */
    public Usuario buscarUsuarioPorId(Usuario usuario);
    
    /**
     *
     * @param usuario
     * @return
     */
    public Usuario buscarUsuarioPorLogin(Usuario usuario);
    
    /**
     *
     * @param usuario
     * @return
     */
    public Usuario buscarUsuarioPorMail(Usuario usuario);
    
    /**
     *
     * @param usuario
     * @return
     */
    public int agregarUsuario(Usuario usuario);
    
    /**
     *
     * @param usuario
     */
    public void actualizarUsuario(Usuario usuario);
    
    /**
     *
     * @param usuario
     */
    public void borrarUsuario(Usuario usuario);
    
    /**
     *
     * @param usuario
     * @return
     */
    public boolean validarUsuario(Usuario usuario);
}
