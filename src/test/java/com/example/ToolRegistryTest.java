package com.example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 * Unit tests for ToolRegistry
 */
public class ToolRegistryTest {
    
    private ToolRegistry registry;
    
    @Before
    public void setUp() {
        registry = new ToolRegistry();
    }
    
    @Test
    public void testRegisterAndGetTool() {
        ToolFactory pencilFactory = new PencilToolFactory();
        registry.registerTool(pencilFactory);
        
        ToolFactory retrieved = registry.getTool("Pencil");
        assertNotNull("Tool should be retrievable after registration", retrieved);
        assertEquals("Retrieved tool should match registered tool", pencilFactory, retrieved);
    }
    
    @Test
    public void testGetNonExistentTool() {
        ToolFactory result = registry.getTool("NonExistent");
        assertNull("Non-existent tool should return null", result);
    }
    
    @Test
    public void testRegisterMultipleTools() {
        registry.registerTool(new PencilToolFactory());
        registry.registerTool(new EraserToolFactory());
        
        assertEquals("Registry should contain 2 tools", 2, registry.getToolNames().size());
        assertNotNull("Pencil tool should be available", registry.getTool("Pencil"));
        assertNotNull("Eraser tool should be available", registry.getTool("Eraser"));
    }
    
    @Test
    public void testCreateToolByName() {
        registry.registerTool(new PencilToolFactory());
        
        PaintObject tool = registry.createTool("Pencil");
        assertNotNull("Created tool should not be null", tool);
        assertTrue("Created tool should be instance of PencilPaint", tool instanceof PencilPaint);
    }
    
    @Test
    public void testCreateNonExistentTool() {
        PaintObject tool = registry.createTool("NonExistent");
        assertNull("Creating non-existent tool should return null", tool);
    }
    
    @Test
    public void testGetToolNames() {
        registry.registerTool(new PencilToolFactory());
        registry.registerTool(new EraserToolFactory());
        
        assertTrue("Tool names should contain Pencil", registry.getToolNames().contains("Pencil"));
        assertTrue("Tool names should contain Eraser", registry.getToolNames().contains("Eraser"));
    }
    
}
