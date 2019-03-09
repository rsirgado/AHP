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
package com.ahp.web;

import com.ahp.domain.Usuario;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Gestiona la visualación del formulario de registro
 * @author Ramon
 */
@Named
@RequestScoped
public class ControlLoginView implements Serializable{

    private static final long serialVersionUID = -5370843046034825735L;
    
    private String login;
    private String password;
    
    Usuario usuario;
    
    /**
     *
     */
    public ControlLoginView(){
        usuario = new Usuario();
    }
    
    // getters

    /**
     *
     * @return
     */
    public String getLogin(){return this.login;}

    /**
     *
     * @return
     */
    public String getPassword(){return this.password;}

    /**
     *
     * @return
     */
    public Usuario getUsuario(){return this.usuario;}
    
    // setters

    /**
     *
     * @param login
     */
    public void setLogin(String login){this.login = login;}

    /**
     *
     * @param password
     */
    public void setPassword(String password){this.password=password;}

    /**
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario){this.usuario = usuario;}

}
