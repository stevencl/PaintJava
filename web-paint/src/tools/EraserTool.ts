import { BasePaintTool, type Point, type PaintStroke } from './BasePaintTool';

/**
 * Eraser tool implementation - draws in white with larger thickness
 */
export class EraserTool extends BasePaintTool {
  private static readonly ERASER_THICKNESS = 25;
  private static readonly ERASER_COLOR = 'white';

  constructor() {
    super('eraser');
  }

  drawLiveStroke(ctx: CanvasRenderingContext2D, fromPoint: Point, toPoint: Point): void {
    ctx.strokeStyle = EraserTool.ERASER_COLOR;
    ctx.lineWidth = EraserTool.ERASER_THICKNESS;
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';
    
    ctx.beginPath();
    ctx.moveTo(fromPoint.x, fromPoint.y);
    ctx.lineTo(toPoint.x, toPoint.y);
    ctx.stroke();
  }

  renderStroke(ctx: CanvasRenderingContext2D, stroke: PaintStroke): void {
    if (stroke.points.length < 2) return;
    
    ctx.strokeStyle = EraserTool.ERASER_COLOR;
    ctx.lineWidth = EraserTool.ERASER_THICKNESS;
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
      color: EraserTool.ERASER_COLOR,
      thickness: EraserTool.ERASER_THICKNESS,
      tool: this.name
    };
  }
}