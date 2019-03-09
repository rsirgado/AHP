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

import com.ahp.domain.Usuario;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.ahp.eis.UsuarioDao;
import java.util.List;
import com.ahp.util.JavaPasswordSecurity;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author rsirgado
 */
@Stateless
public class UsuarioServiceImp implements UsuarioService {

    private final Logger log = LogManager.getRootLogger();

    @Inject
    UsuarioDao usuarioDao;

    /**
     *
     * @return
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioDao.listarUsuarios();
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public Usuario buscarUsuarioPorId(Usuario usuario) {
        return usuarioDao.buscarUsuarioPorId(usuario);
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public Usuario buscarUsuarioPorLogin(Usuario usuario) {
        Usuario user = usuarioDao.buscarUsuarioPorLogin(usuario);
        return user;
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public Usuario buscarUsuarioPorMail(Usuario usuario) {
        return usuarioDao.buscarUsuarioPorMail(usuario);
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public int agregarUsuario(Usuario usuario) {
        eliminaEspacios(usuario);
        Usuario user = usuarioDao.buscarUsuarioPorLogin(usuario);
        if (user != null){
            // El login ya existe
            return 0;
        }
        
        try {
            usuario.setHash(JavaPasswordSecurity.createHash(usuario.getPassword()));
            usuarioDao.agregarUsuario(usuario);
            // agregado con exito
            return 1;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // devuelve error
            return -1;
        }
    }

    /**
     *
     * @param usuario
     */
    @Override
    public void actualizarUsuario(Usuario usuario) {
        eliminaEspacios(usuario);
        
        Usuario user = buscarUsuarioPorLogin(usuario);
        try {
            if (!JavaPasswordSecurity.validatePassword(usuario.getPassword(), user.getHash())) {
                usuario.setHash(JavaPasswordSecurity.createHash(usuario.getPassword()));
            }
            usuarioDao.actualizarUsuario(usuario);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }

    }

    /**
     *
     * @param usuario
     */
    @Override
    public void borrarUsuario(Usuario usuario) {
        eliminaEspacios(usuario);
        usuarioDao.eliminarUsuario(usuario);
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public boolean validarUsuario(Usuario usuario) {
        boolean out;
        eliminaEspacios(usuario);
        
        Usuario user = buscarUsuarioPorLogin(usuario);
        if (user == null) {
            log.info("usuario " + usuario.getLogin() + " no encontrado");
            return false;
        }
        try {
            out = JavaPasswordSecurity.validatePassword(usuario.getPassword(), user.getHash());
            return out;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        }

    }
    
    private void eliminaEspacios(Usuario usuario){
        usuario.setLogin(usuario.getLogin().trim());
        if (usuario.getNombre()!= null)
            usuario.setNombre(usuario.getNombre().trim());
        if(usuario.getApellido1()!= null)
            usuario.setApellido1(usuario.getApellido1().trim());
        if (usuario.getApellido2()!= null)
            usuario.setApellido2(usuario.getApellido2().trim());
        if (usuario.getMail()!=null)
            usuario.setMail(usuario.getMail().trim());
    }
}
