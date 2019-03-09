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

import com.ahp.domain.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;

/**
 *
 * @author rsirgado
 */
@Stateless
//@SuppressWarnings("unchecked")
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext(unitName = "com.AHProjectPU")
    EntityManager em;

    /**
     *
     */
    public UsuarioDaoImp() {
    }

    /**
     *
     * @return
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return em.createNamedQuery("Usuario.findAll").getResultList();

    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public Usuario buscarUsuarioPorId(Usuario usuario) {
        return (Usuario) em.createNamedQuery("Usuario.findByIdUsuario").setParameter("idUsuario", usuario.getIdUsuario()).getSingleResult();
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public Usuario buscarUsuarioPorLogin(Usuario usuario) {

        List<Usuario> resultados;

        resultados = em.createNamedQuery("Usuario.findByLogin").setParameter("login", usuario.getLogin()).getResultList();
        if (resultados.size() > 0) {
            return resultados.get(0);
        } else {
            return null;
        }

    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public Usuario buscarUsuarioPorMail(Usuario usuario) {
        return (Usuario) em.createNamedQuery("Usuario.findByMail").setParameter("mail", usuario.getMail()).getSingleResult();
    }

    /**
     *
     * @param usuario
     */
    @Override
    public void agregarUsuario(Usuario usuario) {

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(usuario);
        tx.commit();

    }

    /**
     *
     * @param usuario
     */
    @Override
    public void actualizarUsuario(Usuario usuario) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Usuario user = em.find(Usuario.class, usuario.getIdUsuario());
        if (user != null) {
            user.setLogin(usuario.getLogin());
            user.setNombre(usuario.getNombre());
            user.setApellido1(usuario.getApellido1());
            user.setApellido2(usuario.getApellido2());
            user.setMail(usuario.getMail());
        }
        em.merge(user);
        tx.commit();
    }

    /**
     *
     * @param usuario
     */
    @Override
    public void eliminarUsuario(Usuario usuario) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(Usuario.class, usuario.getIdUsuario()));
        tx.commit();
    }

}
