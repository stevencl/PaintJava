import { BasePaintTool, type Point, type PaintStroke } from './BasePaintTool';

/**
 * Line tool implementation - draws straight lines between start and end points
 */
export class LineTool extends BasePaintTool {
  constructor() {
    super('line');
  }

  needsFullRedraw(): boolean {
    return true; // Line tool needs full redraws to show proper preview
  }

  drawLiveStroke(ctx: CanvasRenderingContext2D, fromPoint: Point, toPoint: Point): void {
    // For live drawing, we'll show a preview line from the first point to current point
    // This requires the stroke to be redrawn each time, which is handled by the app
    ctx.strokeStyle = this.getColorString(this.settings.color);
    ctx.lineWidth = this.settings.thickness;
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';
    
    ctx.beginPath();
    ctx.moveTo(fromPoint.x, fromPoint.y);
    ctx.lineTo(toPoint.x, toPoint.y);
    ctx.stroke();
  }

  renderStroke(ctx: CanvasRenderingContext2D, stroke: PaintStroke): void {
    if (stroke.points.length < 2) return;
    
    ctx.strokeStyle = stroke.color;
    ctx.lineWidth = stroke.thickness;
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';
    
    // For line tool, we only draw from first point to last point
    const startPoint = stroke.points[0];
    const endPoint = stroke.points[stroke.points.length - 1];
    
    ctx.beginPath();
    ctx.moveTo(startPoint.x, startPoint.y);
    ctx.lineTo(endPoint.x, endPoint.y);
    ctx.stroke();
  }

  createStroke(points: Point[]): PaintStroke {
    return {
      points: [...points],
      color: this.getColorString(this.settings.color),
      thickness: this.settings.thickness,
      tool: this.name
    };
  }
}