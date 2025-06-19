# PaintJava

A simple paint application available in both Java Swing and modern web versions.

## Versions

This repository contains two implementations of the same paint application:

### 1. Java Version (Original)
- Located in the root directory
- Built with Java Swing
- Requires Java Runtime Environment
- Desktop application

### 2. Web Version (Modern)
- Located in the `web-paint/` directory  
- Built with React and TypeScript
- Runs in web browsers
- Single Page Application (SPA)

## Features

Both versions include:
- **Drawing Tools**: Pencil and eraser tools
- **Color Selection**: RGB color picker with sliders
- **Canvas Operations**: Clear canvas and undo functionality
- **Drawing History**: Full undo support

## Quick Start

### Java Version
```bash
# Build and run
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.PaintWindow"

# Or build jar and run
mvn clean package
java -jar target/paint-1.0-SNAPSHOT.jar
```

### Web Version
```bash
# Navigate to web version
cd web-paint

# Install dependencies
npm install

# Run development server
npm run dev
```

## Project Structure

```
PaintJava/
├── src/main/java/com/example/     # Java version source code
│   ├── PaintWindow.java           # Main window
│   ├── PaintCanvas.java           # Drawing canvas
│   ├── PaintObject.java           # Drawing objects
│   └── ...
├── web-paint/                     # Web version
│   ├── src/
│   │   ├── App.tsx               # Main React component
│   │   └── ...
│   └── package.json
└── pom.xml                       # Java build configuration
```

## Requirements

### Java Version
- Java 7 or higher
- Maven 3.x

### Web Version  
- Node.js 14 or higher
- npm (comes with Node.js)
- Modern web browser

## License

This project is open source and available under the MIT License.