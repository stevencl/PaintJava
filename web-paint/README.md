# Paint - Web Version

A modern web-based paint application built with React and TypeScript. This is a web reimplementation of the Java Paint application with the same core functionality.

## Features

- **Drawing Tools**: 
  - Pencil tool for free-form drawing
  - Eraser tool for removing strokes
- **Color Selection**: RGB sliders for precise color control with live preview
- **Canvas Operations**: 
  - Clear entire canvas
  - Undo last stroke (with full history support)
- **Modern UI**: Clean, responsive interface that works on desktop and mobile
- **No Backend Required**: Runs entirely in the browser as a single page application

## Getting Started

### Prerequisites

- Node.js (version 14 or higher)
- npm (comes with Node.js)

### Installation

1. Navigate to the web-paint directory:
   ```bash
   cd web-paint
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

### Running the Application

#### Development Mode
```bash
npm run dev
```
This will start the development server at `http://localhost:5173/`

#### Production Build
```bash
npm run build
```
This creates an optimized production build in the `dist/` folder.

#### Preview Production Build
```bash
npm run preview
```
This serves the production build locally for testing.

## Usage

1. **Select a Tool**: Choose between Pencil and Eraser using the radio buttons
2. **Choose Colors**: Use the RGB sliders to select your drawing color
   - Red, Green, and Blue values can be adjusted from 0-255
   - Live color preview shows your current selection
3. **Draw**: Click and drag on the canvas to draw
4. **Undo**: Click "Undo my last stroke" to remove the most recent stroke
5. **Clear**: Click "Clear the canvas" to remove all drawings

## Technical Details

- **Framework**: React 18 with TypeScript
- **Build Tool**: Vite
- **Drawing**: HTML5 Canvas API
- **Styling**: Modern CSS with responsive design
- **State Management**: React hooks (useState, useRef, useEffect)
- **No Dependencies**: Uses only standard web APIs and React

## Browser Compatibility

This application works in all modern browsers that support:
- HTML5 Canvas
- ES6+ JavaScript features
- CSS Grid and Flexbox

## Architecture

The application follows React best practices:
- Functional components with hooks
- Proper state management for drawing history
- Canvas API integration for high-performance drawing
- Responsive design for various screen sizes

## Comparison with Java Version

This web version provides the same core functionality as the original Java Swing application:
- ✅ Pencil drawing tool
- ✅ Eraser tool  
- ✅ RGB color selection
- ✅ Undo functionality
- ✅ Clear canvas
- ✅ Drawing history management

Additional modern web features:
- ✅ Responsive design for mobile/tablet
- ✅ Modern, clean UI
- ✅ Runs in browser without installation
- ✅ Fast loading and performance
