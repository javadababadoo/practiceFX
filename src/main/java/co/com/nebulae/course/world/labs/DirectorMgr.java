/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world.labs;

import java.util.ArrayList;

/**
 *
 * @author Esteban Zapata
 */
public class DirectorMgr {

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private DirectorMgr() {
    }

    public static DirectorMgr getInstance() {
        return ElementMgrHolder.INSTANCE;
    }

    private static class ElementMgrHolder {
        private static final DirectorMgr INSTANCE = new DirectorMgr();
    }
    //</editor-fold>

    //public List<WorldShape>[][][] = new ArrayList<>[][][];
    
}
