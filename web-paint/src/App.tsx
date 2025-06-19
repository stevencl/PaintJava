import { useState, useRef, useEffect, useCallback } from 'react'
import './App.css'

interface Point {
  x: number;
  y: number;
}

interface PaintStroke {
  points: Point[];
  color: string;
  thickness: number;
  tool: 'pencil' | 'eraser';
}

function App() {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [isDrawing, setIsDrawing] = useState(false);
  const [currentTool, setCurrentTool] = useState<'pencil' | 'eraser'>('pencil');
  const [currentColor, setCurrentColor] = useState({ r: 0, g: 255, b: 0 });
  const [thickness] = useState(5);
  const [history, setHistory] = useState<PaintStroke[]>([]);
  const [currentStroke, setCurrentStroke] = useState<Point[]>([]);

  const getColorString = (color: {r: number, g: number, b: number}) => {
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
    
    // Redraw all strokes
    history.forEach(stroke => {
      if (stroke.points.length < 2) return;
      
      ctx.strokeStyle = stroke.tool === 'eraser' ? 'white' : stroke.color;
      ctx.lineWidth = stroke.tool === 'eraser' ? 25 : stroke.thickness;
      ctx.lineCap = 'round';
      ctx.lineJoin = 'round';
      
      ctx.beginPath();
      ctx.moveTo(stroke.points[0].x, stroke.points[0].y);
      
      for (let i = 1; i < stroke.points.length; i++) {
        ctx.lineTo(stroke.points[i].x, stroke.points[i].y);
      }
      
      ctx.stroke();
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
    if (!isDrawing) return;
    
    const canvas = canvasRef.current;
    if (!canvas) return;
    
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;
    
    const newPoint = { x, y };
    setCurrentStroke(prev => [...prev, newPoint]);
    
    // Draw the current line segment
    const ctx = canvas.getContext('2d');
    if (!ctx) return;
    
    ctx.strokeStyle = currentTool === 'eraser' ? 'white' : getColorString(currentColor);
    ctx.lineWidth = currentTool === 'eraser' ? 25 : thickness;
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';
    
    if (currentStroke.length > 0) {
      ctx.beginPath();
      const lastPoint = currentStroke[currentStroke.length - 1];
      ctx.moveTo(lastPoint.x, lastPoint.y);
      ctx.lineTo(x, y);
      ctx.stroke();
    }
  };

  const stopDrawing = () => {
    if (!isDrawing) return;
    
    setIsDrawing(false);
    
    if (currentStroke.length > 1) {
      const newStroke: PaintStroke = {
        points: currentStroke,
        color: getColorString(currentColor),
        thickness: thickness,
        tool: currentTool
      };
      
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
            <label>
              <input
                type="radio"
                name="tool"
                value="pencil"
                checked={currentTool === 'pencil'}
                onChange={(e) => setCurrentTool(e.target.value as 'pencil')}
              />
              Pencil
            </label>
            <label>
              <input
                type="radio"
                name="tool"
                value="eraser"
                checked={currentTool === 'eraser'}
                onChange={(e) => setCurrentTool(e.target.value as 'eraser')}
              />
              Eraser
            </label>
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
