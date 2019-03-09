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
import com.ahp.domain.Criterio;
import com.ahp.domain.Elemento;
import com.ahp.domain.Lista;
import com.ahp.domain.Valoracion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import org.primefaces.model.TreeNode;
import java.util.Map;
import java.util.HashMap;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.model.DefaultTreeNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Ramon
 */
@Named
@SessionScoped
public class ControlListView implements Serializable {

    private static final long serialVersionUID = 4393928896817457523L;

    private static final Logger log = LogManager.getRootLogger();

    private List<Lista> listasUsuario;
    private Lista listaSeleccionada;
    private String nuevaLista;

    private TreeNode tree = null;
    private TreeNode selectedTreeNode = null;
    private Criterio selectedCriterio = null;
    private Criterio nuevoCriterio = new Criterio();
    private Valoracion selectedValoracion = null;
    
    private Elemento selectedElemento = null;
    
    private boolean editMode= false;

    /**
     *
     */
    @PostConstruct
    public void init() {
        
        
    }

    /**
     *
     */
    @PreDestroy
    public void end() {
        
    }

    // getters

    /**
     *
     * @return
     */

    public List<Lista> getListasUsuario() {
        return this.listasUsuario;
    }

    /**
     *
     * @return
     */
    public Lista getListaSeleccionada() {
        return this.listaSeleccionada;
    }

    /**
     *
     * @return
     */
    public TreeNode getTree() {
        return this.tree;
    }

    /**
     *
     * @return
     */
    public TreeNode getSelectedTreeNode() {
        return this.selectedTreeNode;
    }

    /**
     *
     * @return
     */
    public String getNuevaLista() {
        return this.nuevaLista;
    }

    /**
     *
     * @return
     */
    public Criterio getSelectedCriterio() {
        return this.selectedCriterio;
    }

    /**
     *
     * @return
     */
    public Criterio getNuevoCriterio(){
        return this.nuevoCriterio;
    }
    
    /**
     *
     * @return
     */
    public boolean isEditMode(){
        return this.editMode;
    }
    
    /**
     *
     * @return
     */
    public List<Elemento> getListaElementos(){
        if (this.listaSeleccionada!=null)
            return this.listaSeleccionada.getElementos();
        else 
            return null;
    }
    
    /**
     *
     * @return
     */
    public boolean isCriterioSelected(){
        if(null == selectedTreeNode)
            return false;
        return (selectedTreeNode.getType().equals(TipoHojasArbol.CRITERIO.getTipo()));
    }
    
    /**
     *
     * @return
     */
    public boolean isGrupoCriterioSelected(){
        if(selectedTreeNode == null)
            return false;
        return (selectedTreeNode.getType().equals(TipoHojasArbol.GRUPOCRITERIO.getTipo()));
    }
    
    /**
     *
     * @return
     */
    public boolean isContenedorCriterioSelected(){
        if(selectedTreeNode == null)
            return false;
        return selectedTreeNode.getType().equals(TipoHojasArbol.CONTENEDORCRITERIO.getTipo());
    }
    
    /**
     *
     * @return
     */
    public boolean isElementoSelected(){
        if(selectedTreeNode == null)
            return false;
        return selectedTreeNode.getType().equals(TipoHojasArbol.ELEMENTO.getTipo());
    }
    
    /**
     *
     * @return
     */
    public boolean isContenedorElementoSelected(){
        if(selectedTreeNode == null)
            return false;
        return selectedTreeNode.getType().equals(TipoHojasArbol.CONTENEDORELEMENTO.getTipo());
    }
    
    /**
     *
     * @return
     */
    public Valoracion getSelectedValoracion(){
        return this.selectedValoracion;
    }
    
    /**
     *
     * @return
     */
    public List<Criterio> getCriterios(){
        return this.listaSeleccionada.getCriterios();
    }
    
    /**
     *
     * @return
     */
    public Elemento getSelectedElemento(){
        return this.selectedElemento;
    }
    
    // setters

    /**
     *
     * @param listasUsuario
     */
    public void setListasUsuario(List<Lista> listasUsuario) {
        this.listasUsuario = listasUsuario;
    }

    /**
     *
     * @param lista
     */
    public void setListaSeleccionada(Lista lista) {
        this.listaSeleccionada = lista;
    }

    /**
     *
     * @param selectedTreeNode
     */
    public void setSelectedTreeNode(TreeNode selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
        if (selectedTreeNode != null) {
            if (selectedTreeNode.getType().equals(TipoHojasArbol.CRITERIO.getTipo())
                    || selectedTreeNode.getType().equals(TipoHojasArbol.GRUPOCRITERIO.getTipo())) {
                this.selectedCriterio = (Criterio) selectedTreeNode.getData();
            }
        }
        //        else if (selectedTreeNode.getType().equals("Elemento"))

    }

    /**
     *
     * @param nombreLista
     */
    public void setNuevaLista(String nombreLista) {
        this.nuevaLista = nombreLista;
    }

    /**
     *
     * @param tree
     */
    public void setTree(TreeNode tree) {
        this.tree = tree;
    }

    /**
     *
     * @param criterio
     */
    public void setSelectedCriterio(Criterio criterio) {
        this.selectedCriterio = criterio;
    }

    /**
     *
     * @param criterio
     */
    public void setNuevoCriterio(Criterio criterio){
        this.nuevoCriterio= criterio;
    }
    
    /**
     *
     * @param editMode
     */
    public void setEditMode(boolean editMode){
        this.editMode=editMode;
    }
    
    /**
     *
     * @param valoracion
     */
    public void setSelectedValoracion(Valoracion valoracion){
        this.selectedValoracion=valoracion;
    }
    
    /**
     *
     * @param elementos
     */
    public void setListaElementos(List<Elemento> elementos){
        this.listaSeleccionada.setElementos(elementos);
    }
    
    /**
     *
     * @param elemento
     */
    public void setSelectedElemento(Elemento elemento){
        this.selectedElemento = elemento;
    }
    
    // funciones

    /**
     *
     * @param tipo
     * @param title
     * @param message
     */
    public void addMessage(FacesMessage.Severity tipo, String title, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, title, message));
        PrimeFaces.current().executeScript("PF('growl').update();");
    }

    /**
     *
     */
    public void limpiaNuevaLista() {
        nuevaLista = "";
    }

    /**
     *
     */
    public void limpiaNuevoCriterio() {
        this.editMode= false;
        this.nuevoCriterio = new Criterio(); 
    }
    
    /**
     *
     */
    public void llenaArbol() {
        // Verificamos si tiene la estructura cargada
        if (tree == null) {
            creaEstructura();
        } else {
            borrarCriterios();
            borrarElementos();
        }
        llenaCriterios();
    }

    /**
     *
     */
    public void corrigeIconoCriterio() {
        TreeNode criterioNode = tree.getChildren().get(0);
        if (criterioNode.getChildCount() > 0) {
            corrigeIconoCriterioRecursivo(criterioNode.getChildren().get(0));
        }
    }

    /**
     *
     */
    public void llenaCriterios() {
        TreeNode root = null;
        Map<Criterio, TreeNode> mapa = new HashMap<>();
        if (listaSeleccionada.getCriterios()!=null && !listaSeleccionada.getCriterios().isEmpty()) {
            for (Criterio hoja : listaSeleccionada.getCriterios()) {
                if (root == null) {
                    root = new DefaultTreeNode(TipoHojasArbol.CRITERIO.getTipo(),
                            hoja, tree.getChildren().get(0));
                    mapa.put(hoja, root);
                    root.setExpanded(true);
                } else {
                    TreeNode nodo = new DefaultTreeNode(TipoHojasArbol.CRITERIO.getTipo(),
                            hoja, mapa.get(hoja.getPadre()));
                    mapa.put(hoja, nodo);
                    nodo.setExpanded(true);
                }
            }
        }
        corrigeIconoCriterio();
    }

    /**
     *
     */
    public void borrarCriterios(){
        if (tree != null)
            borraHojas(tree.getChildren().get(0));
    }
    
    /**
     *
     */
    public void borrarElementos(){
        if (tree != null)
            borraHojas(tree.getChildren().get(1));
    }
    
    /**
     *
     * @param padre
     * @param hijos
     */
    public void seleccionaNodosHijos(TreeNode padre, List<Criterio> hijos){
        for(TreeNode hijo:padre.getChildren()){
            hijos.add((Criterio)hijo.getData());
            if(hijo.getChildCount()>0)
                seleccionaNodosHijos(hijo, hijos);
        }
    }
    
    /**
     *
     * @param padre
     */
    public void borraHojas(TreeNode padre) {
        List<TreeNode> hijos = padre.getChildren();
        if (!hijos.isEmpty()) {
            for (TreeNode hoja : hijos) {
                if (hoja.getChildCount() > 0) {
                    borraHojas(hoja);
                }
            }
            padre.getChildren().clear();
        }
    }
    
    /**
     *
     * @param event
     */
    public void cellChanged(AjaxBehaviorEvent event) {
        if (event != null) {
            String celdaModificada = event.getComponent().getClientId();
            String simetricCellArray[] = celdaModificada.split(":");
            Criterio critFila = selectedCriterio.getHijos().get(Integer.parseInt(simetricCellArray[2]));
            Criterio critColumn = selectedCriterio.getHijos().get(Integer.parseInt(simetricCellArray[4]));
            for(Valoracion val:critFila.getValoracionesA()){
                if(val.getCriterioB()==critColumn){
                    selectedValoracion = val;
                    break;
                }
            }
            for(Elemento el:listaSeleccionada.getElementos())
                el.calculaPuntuacion();
        } 
    }
   
    /**
     *
     * @param fila
     * @param columna
     * @return
     */
    public Caracteristica getCaracteristicaCriterio(Elemento fila, Criterio columna){
        
        return fila.getCaracteristicaCriterio(columna);
    }

    /**
     *
     * @param event
     */
    public void onCellEditEvent(CellEditEvent event){

    }
// funciones privadas
    
    private void creaEstructura() {
        tree = new DefaultTreeNode("Root", null);
        TreeNode nodo = new DefaultTreeNode(TipoHojasArbol.CONTENEDORCRITERIO.getTipo(), "Criterios", tree);
        nodo.setExpanded(true);
        nodo = new DefaultTreeNode(TipoHojasArbol.CONTENEDORELEMENTO.getTipo(), "Elementos", tree);
        nodo.setExpanded(true);
    }

    private void corrigeIconoCriterioRecursivo(TreeNode primerCriterio) {
        if (primerCriterio.getChildCount() > 0) {
            primerCriterio.setType(TipoHojasArbol.GRUPOCRITERIO.getTipo());
            for (TreeNode hijo : primerCriterio.getChildren()) {
                corrigeIconoCriterioRecursivo(hijo);
            }
        }

    }

    // tipos
    private enum TipoHojasArbol {
        CONTENEDORCRITERIO("ContenedorCriterio"),
        CONTENEDORELEMENTO("ContenedorElemento"),
        GRUPOCRITERIO("GrupoCriterio"),
        CRITERIO("Criterio"),
        ELEMENTO("Elemento");

        String tipo;

        private TipoHojasArbol(String tipo) {
            this.tipo = tipo;
        }

        public String getTipo() {
            return this.tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }
}
