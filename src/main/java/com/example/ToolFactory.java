package com.example;

/**
 * Factory interface for creating PaintObject instances.
 * This interface enables dependency injection by abstracting tool creation.
 */
public interface ToolFactory {
    
    /**
     * Creates a new instance of a PaintObject tool
     * @return A new PaintObject instance
     */
    PaintObject createTool();
    
    /**
     * Gets the name of this tool
     * @return The tool name
     */
    String getName();
    
}
