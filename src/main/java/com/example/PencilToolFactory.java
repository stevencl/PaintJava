package com.example;

/**
 * Factory for creating PencilPaint tool instances.
 */
public class PencilToolFactory implements ToolFactory {
    
    public PaintObject createTool() {
        return new PencilPaint();
    }
    
    public String getName() {
        return "Pencil";
    }
    
}
