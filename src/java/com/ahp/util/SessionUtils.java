package com.ahp.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    /**
     *
     * @return
     */
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
    }

    /**
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    /**
     *
     * @return
     */
    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    /**
     *
     */
    public static void removeUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.removeAttribute("username");
    }
    
    /**
     *
     */
    public static void removeUserId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.removeAttribute("userid");
    }
    
    /**
     *
     * @param username
     */
    public static void setUserName(String username){
        ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false)).setAttribute("username", username);
    }
    
    /**
     *
     * @return
     */
    public static Integer getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (Integer) session.getAttribute("userid");
        } else {
            return null;
        }
    }
    
    /**
     *
     * @param userId
     */
    public static void setUserId(int userId){
        ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false)).setAttribute("userid", userId);
    }
}
