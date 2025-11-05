package com.example;

/**
 * Interface for canvas actions.
 * Enables dependency injection by decoupling Actions from PaintWindow.
 */
public interface CanvasActions {
    
    /**
     * Clear the canvas
     */
    void clear();
    
    /**
     * Undo the last action
     */
    void undo();
    
}
