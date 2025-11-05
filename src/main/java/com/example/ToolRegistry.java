package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Registry that manages available paint tools.
 * This class implements the Registry pattern to enable dependency injection
 * and loose coupling between the UI and tool implementations.
 */
public class ToolRegistry {
    
    private Map<String, ToolFactory> tools;
    
    public ToolRegistry() {
        tools = new HashMap<String, ToolFactory>();
    }
    
    /**
     * Register a tool factory with the registry
     * @param factory The tool factory to register
     */
    public void registerTool(ToolFactory factory) {
        tools.put(factory.getName(), factory);
    }
    
    /**
     * Get a tool factory by name
     * @param name The name of the tool
     * @return The tool factory, or null if not found
     */
    public ToolFactory getTool(String name) {
        return tools.get(name);
    }
    
    /**
     * Get all available tool names
     * @return A set of tool names
     */
    public Set<String> getToolNames() {
        return tools.keySet();
    }
    
    /**
     * Create a new instance of a tool by name
     * @param name The name of the tool
     * @return A new PaintObject instance, or null if tool not found
     */
    public PaintObject createTool(String name) {
        ToolFactory factory = tools.get(name);
        if (factory != null) {
            return factory.createTool();
        }
        return null;
    }
    
}
