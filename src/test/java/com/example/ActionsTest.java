package com.example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 * Unit tests for Actions with dependency injection
 */
public class ActionsTest {
    
    private TestCanvasActions canvasActions;
    private TestToolSelector toolSelector;
    private ToolRegistry toolRegistry;
    private Actions actions;
    
    @Before
    public void setUp() {
        canvasActions = new TestCanvasActions();
        toolSelector = new TestToolSelector();
        toolRegistry = new ToolRegistry();
        toolRegistry.registerTool(new PencilToolFactory());
        toolRegistry.registerTool(new EraserToolFactory());
        
        actions = new Actions(canvasActions, toolSelector, toolRegistry);
    }
    
    @Test
    public void testClearAction() {
        assertFalse("Clear should not be called initially", canvasActions.clearCalled);
        
        actions.clearAction.actionPerformed(null);
        
        assertTrue("Clear action should call canvasActions.clear()", canvasActions.clearCalled);
    }
    
    @Test
    public void testUndoAction() {
        assertFalse("Undo should not be called initially", canvasActions.undoCalled);
        
        actions.undoAction.actionPerformed(null);
        
        assertTrue("Undo action should call canvasActions.undo()", canvasActions.undoCalled);
    }
    
    @Test
    public void testPencilAction() {
        assertNull("Tool should not be set initially", toolSelector.selectedTool);
        
        actions.pencilAction.actionPerformed(null);
        
        assertEquals("Pencil action should set tool to Pencil", "Pencil", toolSelector.selectedTool);
    }
    
    @Test
    public void testEraserAction() {
        assertNull("Tool should not be set initially", toolSelector.selectedTool);
        
        actions.eraserAction.actionPerformed(null);
        
        assertEquals("Eraser action should set tool to Eraser", "Eraser", toolSelector.selectedTool);
    }
    
    // Test implementations of interfaces
    private static class TestCanvasActions implements CanvasActions {
        boolean clearCalled = false;
        boolean undoCalled = false;
        
        public void clear() {
            clearCalled = true;
        }
        
        public void undo() {
            undoCalled = true;
        }
    }
    
    private static class TestToolSelector implements ToolSelector {
        String selectedTool = null;
        
        public void setActiveTool(String toolName) {
            selectedTool = toolName;
        }
    }
    
}
