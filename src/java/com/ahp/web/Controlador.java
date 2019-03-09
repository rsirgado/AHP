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

import com.ahp.domain.Caracteristica;
import com.ahp.domain.Lista;
import com.ahp.domain.Criterio;
import com.ahp.domain.Elemento;
import com.ahp.domain.Usuario;
import com.ahp.service.CaracteristicaService;
import com.ahp.service.CriterioService;
import com.ahp.service.ElementoService;
import com.ahp.service.ListaService;
import java.io.Serializable;
import javax.inject.Inject;
import com.ahp.service.UsuarioService;
import com.ahp.service.ValoracionService;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import com.ahp.util.SessionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.primefaces.PrimeFaces;
import javax.faces.context.FacesContext;
import org.primefaces.model.TreeNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 * Controlador del flujo de trabajo de la aplicación
 * @author rsirgado
 */
@Named
@RequestScoped
public class Controlador implements Serializable {

    private static final long serialVersionUID = -3470027472469482398L;

    private static final Logger log = LogManager.getRootLogger();

    @Inject
    UsuarioService usuarioServicio;
    @Inject
    ListaService listaService;
    @Inject
    CriterioService criterioService;
    @Inject
    ValoracionService valoracionService;
    @Inject
    ControlLoginView controlView;
    @Inject
    ControlListView listView;
    @Inject
    ElementoService elementoService;
    @Inject
    CaracteristicaService caracteristicaService;

    
    @Inject
    public Controlador() {
    }

    // funciones

    /**
     * Ejecuta el login a la aplicación
     * 
     */
    public String login() {
        Usuario usuario = new Usuario(controlView.getLogin(), controlView.getPassword());

        if (usuarioServicio.validarUsuario(usuario)) {

            usuario = usuarioServicio.buscarUsuarioPorLogin(usuario);
            SessionUtils.setUserName(usuario.getLogin());
            SessionUtils.setUserId(usuario.getIdUsuario());

            listView.setListasUsuario(listaService.buscarListaPorUsuario(usuario));

            return "succeed";
        } else {
            listView.addMessage(FacesMessage.SEVERITY_WARN, "Error de inicio", "Usuario o password incorrecto");
            return "";
        }

    }

    /**
     * Ejecuta el logout de la aplicación
     * 
     */
    public String logout() {
        listaService.actualizarLista(listView.getListaSeleccionada());

        SessionUtils.removeUserId();
        SessionUtils.removeUserName();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "/index.xhtml?faces-redirect=true";

    }

    /**
     * Ejecuta la acción de borrar la lista seleccionada
     */
    public void deleteList() {
        if (listView.getListaSeleccionada() != null) {
            int pos = 0, i = 0;
            if (listView.getListaSeleccionada() == null) {
                PrimeFaces.current().executeScript("PF('sidebarListas').hide()");
                listView.addMessage(FacesMessage.SEVERITY_ERROR, "Error de borrado", "No ha lista seleccionada");
            } else {
                for (Lista l : listView.getListasUsuario()) {
                    if (Objects.equals(l.getIdLista(), listView.getListaSeleccionada().getIdLista())) {
                        i = pos;
                    }
                    pos++;
                }
                listaService.borrarLista(listView.getListaSeleccionada());
                listView.setListaSeleccionada(null);
                listView.getListasUsuario().remove(i);
            }
        }

    }

    /**
     * Ejecuta la inserción de una nueva lista
     */
    public void insertaNuevaLista() {
        Lista nueva = new Lista();
        Usuario user = usuarioServicio.buscarUsuarioPorId(new Usuario(SessionUtils.getUserId()));
        nueva.setNombreLista(listView.getNuevaLista());
        nueva.setUsuario(user);

        if (!listaService.buscarListaPorNombreYUsuario(nueva).isEmpty()) {
            PrimeFaces.current().executeScript("PF('sidebarListas').hide()");
            listView.addMessage(FacesMessage.SEVERITY_ERROR, "Error en el registro",
                    "Ya existe una lista con ese nombre");
        } else {
            nueva.setNombreLista(listView.getNuevaLista());
            nueva.setUsuario(user);
            listaService.agregarLista(nueva);
            listView.getListasUsuario().add(nueva);
        }
        listView.limpiaNuevaLista();
    }

    /**
     * Ejectua la actualización de la información a mostrar una vez seleccionada una lista
     */
    public void actualizaCriterios() {
        listView.llenaArbol();
        listView.setSelectedTreeNode(listView.getTree().getChildren().get(0));
        if (listView.getCriterios() != null) {
            for (Criterio crit : listView.getCriterios()) {
                if ((crit.getHijos() != null) && (crit.getHijos().size() > 0)) {
                    crit.creaMatriz(true);
                }
            }
        }
        if (listView.getListaElementos() != null) {
            for (Elemento el : listView.getListaElementos()) {
                el.calculaPuntuacion();
            }
        }
    }

    /**
     * Controla cuando se va a mostrar el formulario de nuevo criterio 
     */
    public void nuevoCriterio() {
        if (listView.getSelectedTreeNode().getType().equals("ContenedorCriterio")) {
            if (listView.getSelectedTreeNode().getChildCount() > 0) {
                listView.addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya existe un criterio a evaluar");
            }
        }
        listView.setEditMode(false);
        PrimeFaces.current().executeScript("PF('nuevoCriterioDlg').show()");
    }

    /**
     * Prepara y muestra el formulario de edición de criterios
     */
    public void editaCriterio() {
        Criterio from = listView.getSelectedCriterio();

        listView.setEditMode(true);
        listView.getNuevoCriterio().setNombreCriterio(from.getNombreCriterio());
        listView.getNuevoCriterio().setDescripcion(from.getDescripcion());
        //listView.getNuevoCriterio().setPadre(from.getPadre());
        //listView.getNuevoCriterio().setLista(from.getLista());
        PrimeFaces.current().executeScript("PF('nuevoCriterioDlg').show()");
    }

    /**
     * Borra un criterio
     */
    public void borraCriterio() {
        // Almaceno una lista de todos los elementos a borrar (él mismo y sus hijos)
        Criterio nodoABorrar = (Criterio) listView.getSelectedTreeNode().getData();

        // Lo borro de la memoria
        borraCriterioPadre(nodoABorrar);
        Criterio padre = nodoABorrar.getPadre();
        
        nodoABorrar.setPadre(null);

        criterioService.borrarCriterio(nodoABorrar);

        // Repinto el arbol
        listView.borrarCriterios();
        listView.llenaCriterios();

        if (padre != null) {
            TreeNode out = buscaHoja(padre, listView.getTree().getChildren().get(0));
            if (out != null) {
                listView.getSelectedTreeNode().setSelected(false);
                listView.setSelectedTreeNode(out);
                out.setSelected(true);
                listView.setSelectedCriterio(padre);
            } else {
                listView.setSelectedTreeNode(listView.getTree().getChildren().get(0));
            }
        } else {
            listView.setSelectedTreeNode(listView.getTree().getChildren().get(0));
        }
        PrimeFaces.current().executeScript("PF(':leftForm:leftLayoutTree').update()");

    }

    /**
     * Ejectua la acción de insertar un nuevo crierio
     */
    public void insertaNuevoCritrerio() {

        Criterio nuevo;

        if (!listView.isEditMode()) {
            // Estamos agregando un nuevo elemento
            nuevo = new Criterio();
            nuevo.setNombreCriterio(listView.getNuevoCriterio().getNombreCriterio());
            nuevo.setDescripcion(listView.getNuevoCriterio().getDescripcion());
            nuevo.setLista(listView.getListaSeleccionada());
            if (listView.getSelectedTreeNode().getType().equals("Criterio")
                    || listView.getSelectedTreeNode().getType().equals("GrupoCriterio")) {

                nuevo.setPadre(listView.getSelectedCriterio());
                //nuevo.getPadre().creaMatriz();
                //criterioService.actualizarCriterio(nuevo.getPadre());
            } else {
                nuevo.setPadre(null);
            }

            criterioService.agregarCriterio(nuevo);
        } else {
            // Estamos editando un elemento
            nuevo = listView.getSelectedCriterio();
            nuevo.setNombreCriterio(listView.getNuevoCriterio().getNombreCriterio());
            nuevo.setDescripcion(listView.getNuevoCriterio().getDescripcion());

            criterioService.actualizarCriterio(nuevo);
            listView.setEditMode(false);
        }

        listView.limpiaNuevoCriterio();
        listView.borrarCriterios();
        listView.llenaCriterios();

        TreeNode out = buscaHoja(nuevo, listView.getTree().getChildren().get(0));
        if (out != null) {
            listView.getSelectedTreeNode().setSelected(false);
            listView.setSelectedTreeNode(out);
            out.setSelected(true);
            listView.setSelectedCriterio(nuevo);
        } else {
            listView.setSelectedTreeNode(listView.getTree().getChildren().get(0));
        }
    }

    /**
     *  muestra un nuevo elemento y todas las características
     */
    public void nuevoElemento() {

        Elemento nuevo = new Elemento();
        nuevo.setNombreElemento("Nuevo Elemento");
        nuevo.setLista(listView.getListaSeleccionada());
        nuevo.setCaracteristicas(new ArrayList<Caracteristica>());
        for (Criterio crit : getCabecerasCriterios()) {
            nuevo.getCaracteristicaCriterio(crit);
        }

        listaService.actualizarLista(listView.getListaSeleccionada());
        elementoService.agregarElemento(nuevo);

    }

    /**
     * Borra el elemento seleccionado
     */
    public void borraElemento() {
        Elemento el = listView.getSelectedElemento();
        if (!el.getCaracteristicas().isEmpty()) {
            for (Caracteristica car : el.getCaracteristicas()) {
                //car.getCriterio().getCaracteristicas().remove(car);
                car.setCriterioSeleccionado(null);
                car.setCriterio(null);
                car.setElemento(null);
                //el.getCaracteristicas().remove(car);
                caracteristicaService.actualizarCaracteristica(car);
                elementoService.actualizarElemento(el);
            }
        }
        listView.getListaElementos().remove(el);
        listaService.actualizarLista(listView.getListaSeleccionada());
        elementoService.borrarElemento(el);
        
    }

    /**
     * Guarda la información tras la modificación de una celda de valoraciones de los criterios
     * @param event
     */
    public void saveData(CellEditEvent event) {
        if (listView.getSelectedValoracion().getSimetrica() != null) {
            valoracionService.agregarOActualizaValoracion(listView.getSelectedValoracion().getSimetrica());
        }
        listView.getSelectedCriterio().asignaPesos();
        valoracionService.agregarOActualizaValoracion(listView.getSelectedValoracion());

    }

    /**
     * Devuelve los criterios cabeceras. En criterio es cabecera si sus criterios descendientes no tienen descendientes
     * @return
     */
    public List<Criterio> getCabecerasCriterios() {
        List<Criterio> cabecera = new ArrayList<>();
        List<Criterio> out = null;
        if (listView.getListaSeleccionada() != null) {
            if (listView.getListaSeleccionada().getCriterios() != null && !listView.getListaSeleccionada().getCriterios().isEmpty()) {
                cabecera = buscaCabeceras(listView.getListaSeleccionada().getCriterios().get(0));
            }
        }
        return cabecera;

        //return criterioService.ultimoNivel(listView.getListaSeleccionada());
    }

    /**
     * Guarda las modificaiones realizadas a las celdas de elemento
     * @param evento
     */
    public void guardarCaracteristicas(RowEditEvent evento) {
        Elemento el = (Elemento) evento.getObject();
        if ((el != null)) {
            elementoService.actualizarElemento(el);
            //caracteristicaService.guardaActualizaCaracteristica(el.getCaracteristicas());
        }
    }

    /**
     * inserta un nuevo usuario
     */
    public void insertaNuevoUsuario() {
        int out = usuarioServicio.agregarUsuario(controlView.getUsuario());
        switch (out) {
            case -1:
                FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(java.util.ResourceBundle.getBundle("mensajes").getString("loginForm.mensajeError")));
                break;
            case 0:
                FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(java.util.ResourceBundle.getBundle("mensajes").getString("loginForm.mensajeUsuarioExiste")));
                break;

            default:
                FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(java.util.ResourceBundle.getBundle("mensajes").getString("loginForm.mensajeUsuarioAgregado")));
                controlView.setUsuario(new Usuario());
                PrimeFaces.current().executeScript("PF('newUserDialog').hide()");
                break;
        }
    }

    /**
     * Cancela la insercion de usuarios
     */
    public void cancelaNuevoUsuario() {
        Usuario user = controlView.getUsuario();
        user.setNombre("aa");
        user.setPassword("aa");
        user.setLogin("aa");
        user.setApellido1("aa");
        user.setMail("a@a.com");
        FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(java.util.ResourceBundle.getBundle("mensajes").getString("loginForm.mensajeCancelNuevoUsuario")));
        PrimeFaces.current().executeScript("PF('newUserDialog').hide()");
        controlView.setUsuario(new Usuario());
        PrimeFaces.current().executeScript("PF('newUserDialog').update()");

    }

    // funciones privadas
    private List<Criterio> buscaCabeceras(Criterio padre) {
        List<Criterio> salida = new ArrayList();
        List<Criterio> out = null;
        if (padre.isCabecera()) {
            salida.add(padre);
        } else {
            if (!padre.getHijos().isEmpty()) {
                for (Criterio crit : padre.getHijos()) {
                    out = buscaCabeceras(crit);
                    if (!out.isEmpty()) {
                        salida.addAll(out);
                    }
                }
            }
        }
        return salida;

    }

    private void borraCriterioPadre(Criterio padre) {

        // Revisamos los hijos los hijos
        if (!padre.getHijos().isEmpty()) {
            for (Criterio hijo : padre.getHijos()) {
                borraCriterioPadre(hijo);
            }
        }
        // eliminamos las referencias a el en las características
        if (padre.getCaracteristicas() != null && !padre.getCaracteristicas().isEmpty()) {
            // eliminamos las caracteristicas
            int fin = padre.getCaracteristicas().size();
            for (int i = 0; i < fin; i++) {
                Caracteristica car = padre.getCaracteristicas().get(0);
                car.setCriterio(null);
                car.setCriterioSeleccionado(null);
                car.setElemento(null);
            }
        }
        
        if(padre.getDatos()!=null && !padre.getDatos().isEmpty()){
            int fin = padre.getDatos().size();
            for (int i = 0; i < fin; i++) {
                Caracteristica car = padre.getDatos().get(0);
                caracteristicaService.borrarCaracteristica(car);
                car.setCriterioSeleccionado(null);
                car.setCriterio(null);
                car.setElemento(null);
                
            }
        }
        // Lo borramos de la lista de criterios
        listView.getCriterios().remove(padre);

    }

    private TreeNode buscaHoja(Criterio criterio, TreeNode inicio) {
        if ((inicio.getData() instanceof Criterio) && ((Criterio) inicio.getData() == criterio)) {
            return inicio;
        }
        if (inicio.getChildCount() > 0) {
            for (TreeNode hijo : inicio.getChildren()) {
                TreeNode out = buscaHoja(criterio, hijo);
                if (out != null) {
                    return out;
                }
            }
        }
        return null;
    }

}
