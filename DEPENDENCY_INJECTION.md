# Dependency Injection in Java Paint Application

## Overview

This document describes the dependency injection (DI) architecture implemented in the Java version of the Paint application. The implementation follows the **Constructor Injection** pattern and uses the **Factory** and **Registry** patterns to manage tool creation.

## Architecture

### Key Interfaces

#### 1. ToolFactory
Abstracts the creation of paint tools (PaintObject instances).

```java
public interface ToolFactory {
    PaintObject createTool();
    String getName();
}
```

**Purpose:** Enables loose coupling by removing direct class references and reflection-based instantiation.

#### 2. CanvasActions
Defines canvas operations (clear, undo).

```java
public interface CanvasActions {
    void clear();
    void undo();
}
```

**Purpose:** Decouples the Actions class from PaintWindow, allowing Actions to work with any implementation.

#### 3. ToolSelector
Defines tool selection behavior.

```java
public interface ToolSelector {
    void setActiveTool(String toolName);
}
```

**Purpose:** Abstracts tool selection, enabling Actions to change tools without knowing implementation details.

### Key Classes

#### 1. ToolRegistry
Central registry for all available paint tools.

**Responsibilities:**
- Register tool factories
- Retrieve tool factories by name
- Create tool instances

**Example:**
```java
ToolRegistry registry = new ToolRegistry();
registry.registerTool(new PencilToolFactory());
registry.registerTool(new EraserToolFactory());

PaintObject pencil = registry.createTool("Pencil");
```

#### 2. PencilToolFactory & EraserToolFactory
Concrete implementations of ToolFactory.

**Example:**
```java
public class PencilToolFactory implements ToolFactory {
    public PaintObject createTool() {
        return new PencilPaint();
    }
    
    public String getName() {
        return "Pencil";
    }
}
```

## Dependency Flow

### Before (Tight Coupling)

```
PaintWindow
    ├─ creates → Actions (passes 'this')
    │   └─ calls methods on → PaintWindow
    ├─ creates → PaintCanvas
    └─ creates → PaintObjectConstructor
        └─ uses reflection with → PencilPaint.class, EraserPaint.class
```

**Problems:**
- Circular dependencies (PaintWindow ↔ Actions)
- Hard-coded class references
- Difficult to test
- Difficult to extend with new tools

### After (Dependency Injection)

```
main()
    ├─ creates → PaintCanvas
    ├─ creates → ToolRegistry
    │   ├─ registers → PencilToolFactory
    │   └─ registers → EraserToolFactory
    ├─ creates → PaintObjectConstructor
    └─ creates → PaintWindow(canvas, registry, constructor)
        ├─ implements → CanvasActions
        ├─ implements → ToolSelector
        └─ creates → Actions(canvasActions, toolSelector, registry)
```

**Benefits:**
- No circular dependencies
- All dependencies injected through constructors
- Easy to test with mock implementations
- Easy to add new tools by implementing ToolFactory
- Follows SOLID principles

## Adding New Tools

To add a new tool, you need to:

1. **Create the tool class** (extends PaintObject)
```java
public class LinePaint extends PaintObject {
    // Implementation
}
```

2. **Create a factory** (implements ToolFactory)
```java
public class LineToolFactory implements ToolFactory {
    public PaintObject createTool() {
        return new LinePaint();
    }
    
    public String getName() {
        return "Line";
    }
}
```

3. **Register the factory** (in createAndShowGUI)
```java
toolRegistry.registerTool(new LineToolFactory());
```

4. **Add UI button** (in PaintWindow constructor)
```java
lineButton = new JRadioButton("Line");
lineButton.addActionListener(e -> setActiveTool("Line"));
```

No changes needed to existing code!

## Testing

The dependency injection architecture enables easy unit testing:

### Testing Actions
```java
@Test
public void testClearAction() {
    TestCanvasActions canvasActions = new TestCanvasActions();
    Actions actions = new Actions(canvasActions, toolSelector, registry);
    
    actions.clearAction.actionPerformed(null);
    
    assertTrue(canvasActions.clearCalled);
}
```

### Testing ToolRegistry
```java
@Test
public void testRegisterAndGetTool() {
    ToolRegistry registry = new ToolRegistry();
    registry.registerTool(new PencilToolFactory());
    
    ToolFactory retrieved = registry.getTool("Pencil");
    assertNotNull(retrieved);
}
```

## Comparison with Web Version

The web version uses similar patterns:

| Aspect | Java Version | Web Version |
|--------|--------------|-------------|
| Tool Management | ToolRegistry class | ToolRegistry class |
| Tool Creation | ToolFactory interface | Tool classes with factory methods |
| Dependency Injection | Constructor injection | React hooks and props |
| Tool Selection | ToolSelector interface | React state management |

## Benefits of This Approach

1. **Loose Coupling:** Components depend on interfaces, not concrete implementations
2. **Testability:** Easy to create mock implementations for testing
3. **Extensibility:** New tools can be added without modifying existing code
4. **Maintainability:** Clear separation of concerns
5. **SOLID Principles:**
   - Single Responsibility: Each class has one clear purpose
   - Open/Closed: Open for extension, closed for modification
   - Liskov Substitution: Interfaces enable substitutability
   - Interface Segregation: Small, focused interfaces
   - Dependency Inversion: Depend on abstractions, not concretions

## Migration Guide

If you're migrating existing code to use this architecture:

1. **Identify Dependencies:** List all objects a class creates or references
2. **Create Interfaces:** Define interfaces for external dependencies
3. **Implement Interfaces:** Make existing classes implement the interfaces
4. **Add Constructor Parameters:** Accept dependencies via constructor
5. **Wire Dependencies:** Create and inject dependencies in main/factory methods
6. **Remove Direct Instantiation:** Replace 'new' with factory/registry patterns
7. **Add Tests:** Write unit tests for each component

## Future Enhancements

Potential improvements to consider:

1. **Dependency Injection Container:** Use a DI framework like Google Guice or Spring
2. **Configuration:** Externalize tool registration to configuration files
3. **Plugin System:** Load tools dynamically from JAR files
4. **Lifecycle Management:** Add initialization and cleanup hooks
5. **Event System:** Implement observer pattern for tool changes
