import { BasePaintTool } from './BasePaintTool';
import { PencilTool } from './PencilTool';
import { EraserTool } from './EraserTool';

/**
 * Tool registry that manages available paint tools
 */
export class ToolRegistry {
  private tools: Map<string, BasePaintTool> = new Map();

  constructor() {
    this.registerTool(new PencilTool());
    this.registerTool(new EraserTool());
  }

  /**
   * Register a new tool
   */
  registerTool(tool: BasePaintTool): void {
    this.tools.set(tool.getName(), tool);
  }

  /**
   * Get a tool by name
   */
  getTool(name: string): BasePaintTool | undefined {
    return this.tools.get(name);
  }

  /**
   * Get all available tool names
   */
  getToolNames(): string[] {
    return Array.from(this.tools.keys());
  }

  /**
   * Get all available tools
   */
  getAllTools(): BasePaintTool[] {
    return Array.from(this.tools.values());
  }
}

// Export singleton instance
export const toolRegistry = new ToolRegistry();