package com.example;

/**
 * Interface for tool selection.
 * Enables dependency injection by decoupling Actions from PaintWindow.
 */
public interface ToolSelector {
    
    /**
     * Set the active tool by name
     * @param toolName The name of the tool to activate
     */
    void setActiveTool(String toolName);
    
}
