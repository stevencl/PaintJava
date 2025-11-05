package com.example;

/**
 * Factory for creating EraserPaint tool instances.
 */
public class EraserToolFactory implements ToolFactory {
    
    public PaintObject createTool() {
        return new EraserPaint();
    }
    
    public String getName() {
        return "Eraser";
    }
    
}
