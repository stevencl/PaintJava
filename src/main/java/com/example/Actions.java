package com.example;
import javax.swing.*;
import java.awt.event.*;

/**
 * Actions class that uses dependency injection.
 * Accepts dependencies via constructor instead of tight coupling to PaintWindow.
 */
public class Actions {

    public AbstractAction clearAction, undoAction, pencilAction, eraserAction;
    
    private CanvasActions canvasActions;
    private ToolSelector toolSelector;
    private ToolRegistry toolRegistry;
    
    /**
     * Constructor with dependency injection
     * @param canvasActions The canvas actions implementation
     * @param toolSelector The tool selector implementation
     * @param toolRegistry The tool registry for available tools
     */
    public Actions(CanvasActions canvasActions, ToolSelector toolSelector, ToolRegistry toolRegistry) {
    
        this.canvasActions = canvasActions;
        this.toolSelector = toolSelector;
        this.toolRegistry = toolRegistry;
        
        clearAction = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                
                Actions.this.canvasActions.clear();
                
            }
        };
        clearAction.putValue(Action.NAME, "Clear the canvas");
        
        undoAction = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                
                Actions.this.canvasActions.undo();
                
            }
        };
        undoAction.putValue(Action.NAME, "Undo my last stroke");
        
        pencilAction = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                
                Actions.this.toolSelector.setActiveTool("Pencil");
                
            }
        };
        pencilAction.putValue(Action.NAME, "Pencil");
        
        eraserAction = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                
                Actions.this.toolSelector.setActiveTool("Eraser");
                
            }
        };
        eraserAction.putValue(Action.NAME, "Eraser"); 
        
    }
        
}
