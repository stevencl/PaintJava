export interface Point {
  x: number;
  y: number;
}

export interface Color {
  r: number;
  g: number;
  b: number;
}

export interface PaintStroke {
  points: Point[];
  color: string;
  thickness: number;
  tool: string;
}

export interface ToolSettings {
  color: Color;
  thickness: number;
}

/**
 * Abstract base class for paint tools, inspired by the Java PaintObject pattern.
 * Each tool must implement the drawing logic for live drawing and stroke rendering.
 */
export abstract class BasePaintTool {
  protected name: string;
  protected settings: ToolSettings;

  constructor(name: string) {
    this.name = name;
    this.settings = {
      color: { r: 0, g: 255, b: 0 },
      thickness: 5
    };
  }

  /**
   * Get the name of this tool
   */
  getName(): string {
    return this.name;
  }

  /**
   * Update tool settings
   */
  updateSettings(settings: Partial<ToolSettings>): void {
    this.settings = { ...this.settings, ...settings };
  }

  /**
   * Get current tool settings
   */
  getSettings(): ToolSettings {
    return { ...this.settings };
  }

  /**
   * Convert color object to CSS string representation
   */
  protected getColorString(color: Color): string {
    return `rgb(${color.r}, ${color.g}, ${color.b})`;
  }

  /**
   * Determines if this tool requires full canvas redraws during live drawing.
   * Tools that need to show previews (like line, rectangle) should return true.
   * Tools that draw incrementally (like pencil, eraser) should return false.
   */
  needsFullRedraw(): boolean {
    return false; // Default behavior for most tools
  }

  /**
   * Draw a live stroke segment on the canvas (used during active drawing)
   * @param ctx - Canvas 2D context
   * @param fromPoint - Starting point of the segment
   * @param toPoint - Ending point of the segment
   */
  abstract drawLiveStroke(ctx: CanvasRenderingContext2D, fromPoint: Point, toPoint: Point): void;

  /**
   * Render a complete stroke on the canvas (used for history replay)
   * @param ctx - Canvas 2D context
   * @param stroke - Complete stroke data
   */
  abstract renderStroke(ctx: CanvasRenderingContext2D, stroke: PaintStroke): void;

  /**
   * Create a stroke object for history storage
   * @param points - Array of points in the stroke
   * @returns PaintStroke object for history
   */
  abstract createStroke(points: Point[]): PaintStroke;
}