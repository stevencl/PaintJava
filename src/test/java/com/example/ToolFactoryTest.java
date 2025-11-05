package com.example;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for ToolFactory implementations
 */
public class ToolFactoryTest {
    
    @Test
    public void testPencilToolFactory() {
        ToolFactory factory = new PencilToolFactory();
        
        assertEquals("Factory name should be Pencil", "Pencil", factory.getName());
        
        PaintObject tool = factory.createTool();
        assertNotNull("Created tool should not be null", tool);
        assertTrue("Created tool should be instance of PencilPaint", tool instanceof PencilPaint);
    }
    
    @Test
    public void testEraserToolFactory() {
        ToolFactory factory = new EraserToolFactory();
        
        assertEquals("Factory name should be Eraser", "Eraser", factory.getName());
        
        PaintObject tool = factory.createTool();
        assertNotNull("Created tool should not be null", tool);
        assertTrue("Created tool should be instance of EraserPaint", tool instanceof EraserPaint);
    }
    
    @Test
    public void testFactoryCreatesNewInstances() {
        ToolFactory factory = new PencilToolFactory();
        
        PaintObject tool1 = factory.createTool();
        PaintObject tool2 = factory.createTool();
        
        assertNotNull("First tool should not be null", tool1);
        assertNotNull("Second tool should not be null", tool2);
        assertNotSame("Each call should create a new instance", tool1, tool2);
    }
    
}
