import { BasePaintTool, type Point, type PaintStroke } from './BasePaintTool';

/**
 * Pencil tool implementation - draws colored strokes
 */
export class PencilTool extends BasePaintTool {
  constructor() {
    super('pencil');
  }

  drawLiveStroke(ctx: CanvasRenderingContext2D, fromPoint: Point, toPoint: Point): void {
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
    
    ctx.beginPath();
    ctx.moveTo(stroke.points[0].x, stroke.points[0].y);
    
    for (let i = 1; i < stroke.points.length; i++) {
      ctx.lineTo(stroke.points[i].x, stroke.points[i].y);
    }
    
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