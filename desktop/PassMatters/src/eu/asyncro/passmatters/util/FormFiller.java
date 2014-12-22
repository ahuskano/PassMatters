/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.util;

import eu.asyncro.passmatters.config.paste.controller.KeyEventRecorder;
import eu.asyncro.passmatters.config.paste.controller.KeyTyper;
import eu.asyncro.passmatters.config.paste.model.PasteShortcut;
import eu.asyncro.passmatters.dao.DAOFactory;
import eu.asyncro.passmatters.main.MainAppListener;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 *
 * @author Milan
 */
public class FormFiller implements ClipboardOwner {

    private MainAppListener mainAppListener;
    private Clipboard clipboard;
    
    public FormFiller(MainAppListener mainAppListener) 
            throws IllegalStateException 
    {
        this.mainAppListener = mainAppListener;
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("lost ownership");
    }
    
    public void fillFocusedForm(String content) throws Exception 
    {
        Transferable clipboardContentBeforePassword = getClipboardContents(); //TODO null
        System.out.println(clipboardContentBeforePassword.toString());
        
        PasteShortcut pasteShortcut = 
                DAOFactory.getFactory(DAOFactory.FILE)
                .getPasteShortcutDAO()
                .getPasteShortcut();
        
        setClipboardContents(content);
        
        KeyTyper typer = new KeyEventRecorder();
        typer.typeKeys(pasteShortcut.getKeyEvents(), true);
        
        returnContentsToClipboard(clipboardContentBeforePassword);
    }
    
    private Transferable getClipboardContents() throws IllegalStateException // if clipboard is not available
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // TODO should it be outside ?
        Transferable contents = clipboard.getContents(null);
        return contents;
    }
    
    private void setClipboardContents(String content) 
            throws IllegalStateException
    {
        StringSelection stringSelection = new StringSelection(content);
        //Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }
    
    private void returnContentsToClipboard(Transferable contents) 
            throws IllegalStateException
    {
         //Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         clipboard.setContents(contents, this);
    }
    
    
}