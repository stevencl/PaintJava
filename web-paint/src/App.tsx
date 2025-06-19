import { useState, useRef, useEffect, useCallback } from 'react'
import './App.css'
import { toolRegistry, type PaintStroke, type Point, type Color } from './tools'

function App() {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [isDrawing, setIsDrawing] = useState(false);
  const [currentToolName, setCurrentToolName] = useState<string>('pencil');
  const [currentColor, setCurrentColor] = useState<Color>({ r: 0, g: 255, b: 0 });
  const [thickness] = useState(5);
  const [history, setHistory] = useState<PaintStroke[]>([]);
  const [currentStroke, setCurrentStroke] = useState<Point[]>([]);

  // Get the current tool instance
  const currentTool = toolRegistry.getTool(currentToolName);

  // Update tool settings when color or thickness changes
  useEffect(() => {
    if (currentTool) {
      currentTool.updateSettings({ color: currentColor, thickness });
    }
  }, [currentTool, currentColor, thickness]);

  const getColorString = (color: Color) => {
    return `rgb(${color.r}, ${color.g}, ${color.b})`;
  };

  const clearCanvas = () => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    
    const ctx = canvas.getContext('2d');
    if (!ctx) return;
    
    ctx.fillStyle = 'white';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
  };

  const redrawCanvas = useCallback(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    
    const ctx = canvas.getContext('2d');
    if (!ctx) return;
    
    // Clear and fill with white background
    ctx.fillStyle = 'white';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    
    // Redraw all strokes using their respective tools
    history.forEach(stroke => {
      const tool = toolRegistry.getTool(stroke.tool);
      if (tool) {
        tool.renderStroke(ctx, stroke);
      }
    });
  }, [history]);

  const startDrawing = (e: React.MouseEvent<HTMLCanvasElement>) => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;
    
    setIsDrawing(true);
    setCurrentStroke([{ x, y }]);
  };

  const draw = (e: React.MouseEvent<HTMLCanvasElement>) => {
    if (!isDrawing || !currentTool) return;
    
    const canvas = canvasRef.current;
    if (!canvas) return;
    
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;
    
    const newPoint = { x, y };
    setCurrentStroke(prev => [...prev, newPoint]);
    
    // Draw the current line segment using the current tool
    const ctx = canvas.getContext('2d');
    if (!ctx) return;
    
    if (currentStroke.length > 0) {
      const lastPoint = currentStroke[currentStroke.length - 1];
      currentTool.drawLiveStroke(ctx, lastPoint, newPoint);
    }
  };

  const stopDrawing = () => {
    if (!isDrawing || !currentTool) return;
    
    setIsDrawing(false);
    
    if (currentStroke.length > 1) {
      const newStroke = currentTool.createStroke(currentStroke);
      setHistory(prev => [...prev, newStroke]);
    }
    
    setCurrentStroke([]);
  };

  const undo = () => {
    if (history.length === 0) return;
    
    setHistory(prev => prev.slice(0, -1));
  };

  const clear = () => {
    setHistory([]);
    clearCanvas();
  };

  // Initialize canvas
  useEffect(() => {
    clearCanvas();
  }, []);

  // Redraw when history changes
  useEffect(() => {
    redrawCanvas();
  }, [history, redrawCanvas]);

  return (
    <div className="paint-app">
      <h1>Paint - Web Version</h1>
      
      <div className="main-container">
        <div className="toolbar">
          <div className="tool-section">
            <h3>Tools</h3>
            {toolRegistry.getToolNames().map(toolName => (
              <label key={toolName}>
                <input
                  type="radio"
                  name="tool"
                  value={toolName}
                  checked={currentToolName === toolName}
                  onChange={(e) => setCurrentToolName(e.target.value)}
                />
                {toolName.charAt(0).toUpperCase() + toolName.slice(1)}
              </label>
            ))}
          </div>

          <div className="color-section">
            <h3>Colors</h3>
            <div className="color-slider">
              <label>Red</label>
              <input
                type="range"
                min="0"
                max="255"
                value={currentColor.r}
                onChange={(e) => setCurrentColor(prev => ({ ...prev, r: parseInt(e.target.value) }))}
              />
              <span>{currentColor.r}</span>
            </div>
            <div className="color-slider">
              <label>Green</label>
              <input
                type="range"
                min="0"
                max="255"
                value={currentColor.g}
                onChange={(e) => setCurrentColor(prev => ({ ...prev, g: parseInt(e.target.value) }))}
              />
              <span>{currentColor.g}</span>
            </div>
            <div className="color-slider">
              <label>Blue</label>
              <input
                type="range"
                min="0"
                max="255"
                value={currentColor.b}
                onChange={(e) => setCurrentColor(prev => ({ ...prev, b: parseInt(e.target.value) }))}
              />
              <span>{currentColor.b}</span>
            </div>
            <div 
              className="color-preview" 
              style={{ backgroundColor: getColorString(currentColor) }}
            ></div>
          </div>

          <div className="action-section">
            <button onClick={clear}>Clear the canvas</button>
            <button onClick={undo} disabled={history.length === 0}>
              Undo my last stroke
            </button>
          </div>
        </div>

        <div className="canvas-container">
          <canvas
            ref={canvasRef}
            width="1024"
            height="768"
            onMouseDown={startDrawing}
            onMouseMove={draw}
            onMouseUp={stopDrawing}
            onMouseLeave={stopDrawing}
            className="paint-canvas"
          />
        </div>
      </div>
    </div>
  );
}

export default App
