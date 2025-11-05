package com.example;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class PaintWindow extends JFrame implements PaintObjectConstructorListener, CanvasActions, ToolSelector {

    private PaintCanvas canvas;
    private JButton clearButton, undoButton;
    private JPanel clearUndoPanel;
    private JRadioButton pencilButton, eraserButton, lineButton;
    private JPanel toolPanel;
    private JPanel rPanel, gPanel, bPanel;
    private JSlider rSlider, bSlider, gSlider;
    private JPanel colorPanel;
    private JPanel controlPanel;
	private JScrollPane canvasPane;
    private Actions actions;
    
    private ButtonGroup toolButtonGroup;
    
    private PaintObjectConstructor objectConstructor;
    private ToolRegistry toolRegistry;    
    
    private ChangeListener colorChangeListener = new ChangeListener() {
        
        public void stateChanged(ChangeEvent changeEvent) {
            
	        objectConstructor.setColor(new Color(rSlider.getValue(), gSlider.getValue(), gSlider.getValue()));
            repaint();
            
        }
    };
    
    private JComponent currentColorComponent = new JComponent() {
        public void paintComponent(Graphics g) {
            
            Color oldColor = g.getColor();
            g.setColor(objectConstructor.getColor());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(oldColor);                                            
            
        }
    };
    
    
    /**
     * Constructor with dependency injection
     * @param initialWidth Initial width of the canvas
     * @param initialHeight Initial height of the canvas
     * @param canvas The canvas to use for painting
     * @param toolRegistry The tool registry with available tools
     * @param objectConstructor The paint object constructor
     */
    public PaintWindow(int initialWidth, int initialHeight, PaintCanvas canvas, 
                      ToolRegistry toolRegistry, PaintObjectConstructor objectConstructor) {
        
        super("Paint");
        
        this.canvas = canvas;
        this.toolRegistry = toolRegistry;
        this.objectConstructor = objectConstructor;
        
        // Create actions with dependency injection
        actions = new Actions(this, this, toolRegistry);
        
        setResizable(true);
        
        setBackground(new Color(128, 10, 160));
        clearButton = new JButton(actions.clearAction);
        clearButton.setOpaque(false);
        undoButton = new JButton(actions.undoAction);
        undoButton.setOpaque(false);
        
        clearUndoPanel = new JPanel();
        clearUndoPanel.setOpaque(false);
        clearUndoPanel.setLayout(new BoxLayout(clearUndoPanel, BoxLayout.Y_AXIS));
        clearUndoPanel.add(clearButton);
        clearUndoPanel.add(undoButton);
        
        pencilButton = new JRadioButton(actions.pencilAction);
        pencilButton.setOpaque(false);
        pencilButton.setSelected(true);
        eraserButton = new JRadioButton(actions.eraserAction);
        eraserButton.setOpaque(false);
        lineButton = new JRadioButton("Line");
        lineButton.setOpaque(false);
        
        toolButtonGroup = new ButtonGroup();
        toolButtonGroup.add(pencilButton);
        toolButtonGroup.add(eraserButton);
        toolButtonGroup.add(lineButton);
        
        toolPanel = new JPanel();
        toolPanel.setOpaque(false);
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
        toolPanel.add(pencilButton);
        toolPanel.add(eraserButton);
        toolPanel.add(lineButton);
        
        rPanel = new JPanel(new FlowLayout());
        rPanel.setOpaque(false);
        rPanel.add(new JLabel("Red"));
        rSlider = new JSlider(0, 255, 0);
        rSlider.setOpaque(false);
        rSlider.addChangeListener(colorChangeListener);
        rPanel.add(rSlider);
        
        gPanel = new JPanel(new FlowLayout());
        gPanel.setOpaque(false);
        gPanel.add(new JLabel("Green"));
        gSlider = new JSlider(0, 255, 255);
        gSlider.setOpaque(false);
        gSlider.addChangeListener(colorChangeListener);
        gPanel.add(gSlider);
        
        bPanel = new JPanel(new FlowLayout());
        bPanel.setOpaque(false);
        bPanel.add(new JLabel("Blue"));
        bSlider = new JSlider(0, 255, 0);
        bSlider.setOpaque(false);
        bSlider.addChangeListener(colorChangeListener);
        bPanel.add(bSlider);
        
        colorPanel = new JPanel();
        colorPanel.setOpaque(false);
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.add(rPanel);
        colorPanel.add(gPanel);
        colorPanel.add(bPanel);
        currentColorComponent.setPreferredSize(new Dimension(100, 50));
        colorPanel.add(currentColorComponent);
                
        controlPanel = new JPanel();
        GridBagLayout controlPanelGridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.weighty = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        controlPanelGridBag.setConstraints(toolPanel, constraints);        
        controlPanelGridBag.setConstraints(colorPanel, constraints);        
        controlPanelGridBag.setConstraints(clearUndoPanel, constraints);        
        controlPanel.setLayout(controlPanelGridBag);
        controlPanel.setOpaque(false);
        controlPanel.add(toolPanel);
        controlPanel.add(colorPanel);
        controlPanel.add(clearUndoPanel);
        
        canvasPane = new JScrollPane(canvas);
                
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(canvasPane, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.WEST);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        
        
        objectConstructor.setColor(new Color(0, 255, 0));
        objectConstructor.setThickness(5);
        
        // Set initial tool from registry
        ToolFactory initialTool = toolRegistry.getTool("Pencil");
        if (initialTool != null) {
            objectConstructor.setToolFactory(initialTool);
        }
        
        canvas.addMouseListener(objectConstructor);
        canvas.addMouseMotionListener(objectConstructor);
        
        pack();
        setVisible(true);
        
    }
    
    /**
     * Set the active tool by name (implements ToolSelector interface)
     * @param toolName The name of the tool to activate
     */
    public void setActiveTool(String toolName) {
        
        ToolFactory factory = toolRegistry.getTool(toolName);
        if (factory != null) {
            objectConstructor.setToolFactory(factory);
        }
                
    }
    
    /**
     * Set the paint object class (deprecated - use setActiveTool instead)
     * Kept for backward compatibility
     * @param paintObjectClass The class of paint object to use
     * @deprecated Use setActiveTool(String) instead
     */
    @Deprecated
    public void setPaintObjectClass(Class paintObjectClass) {
        
        // This method is kept for backward compatibility but should not be used
        // with the new dependency injection approach
                
    }

    /**
     * Undo the last paint action (implements CanvasActions interface)
     */
    public void undo() { 
        
        canvas.undo(); 
        if(canvas.sizeOfHistory() == 0) actions.undoAction.setEnabled(false);
    
    }
    
    /**
     * Clear the canvas (implements CanvasActions interface)
     */
    public void clear() { 
        
        canvas.clear(); 
    
    }
    
    public void constructionBeginning(PaintObject temporaryObject) {
        
        canvas.setTemporaryObject(temporaryObject);   
        
    }
    
    public void constructionContinuing(PaintObject temporaryObject) {
        
        canvas.setTemporaryObject(temporaryObject);   
        
    }
    
    public void constructionComplete(PaintObject finalObject) {
        
        canvas.setTemporaryObject(null);   
        canvas.addPaintObject(finalObject);
        actions.undoAction.setEnabled(true);
        
    }
    
	public void hoveringOverConstructionArea(PaintObject hoverObject) {
		
		canvas.setHoveringObject(hoverObject);
		
	}
    
    private static void createAndShowGUI() {
        // Create dependencies
        PaintCanvas canvas = new PaintCanvas(1024, 768);
        
        // Create and configure tool registry
        ToolRegistry toolRegistry = new ToolRegistry();
        toolRegistry.registerTool(new PencilToolFactory());
        toolRegistry.registerTool(new EraserToolFactory());
        
        // Create paint object constructor
        PaintObjectConstructor objectConstructor = new PaintObjectConstructor(null); // listener set later
        
        // Create and set up the window with dependency injection
        PaintWindow frame = new PaintWindow(1024, 768, canvas, toolRegistry, objectConstructor);
        
        // Set the listener now that frame is created
        objectConstructor = new PaintObjectConstructor(frame);
        objectConstructor.setColor(new Color(0, 255, 0));
        objectConstructor.setThickness(5);
        ToolFactory initialTool = toolRegistry.getTool("Pencil");
        if (initialTool != null) {
            objectConstructor.setToolFactory(initialTool);
        }
        canvas.addMouseListener(objectConstructor);
        canvas.addMouseMotionListener(objectConstructor);
 
        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
