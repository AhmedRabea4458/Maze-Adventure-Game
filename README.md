<h1 align="center">Maze Adventure Game — Java OOP Project</h1>

<p align="center">
  A desktop maze game built with <strong>Java Swing</strong> applying core
  <strong>Object-Oriented Programming (OOP)</strong> concepts.<br/>
  Generate random mazes, move the player with smooth animation, solve the maze using DFS,
  track time, score, difficulty, and enjoy Dark Mode support.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-ED8B00?logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/GUI-Java%20Swing-007396" />
  <img src="https://img.shields.io/badge/Paradigm-OOP-blueviolet" />
  <img src="https://img.shields.io/badge/Algorithm-DFS-green" />
</p>

<p align="center">
  <a href="#features">Features</a> •
  <a href="#gameplay">Gameplay</a> •
  <a href="#algorithms-used">Algorithms</a> •
  <a href="#oop-concepts-used">OOP Concepts</a> •
  <a href="#project-structure">Project Structure</a>
</p>

---

## Features

- **Random Maze Generation** using DFS
- **Player Movement** with collision detection
- **Smooth Animation** using `Swing Timer`
- **Maze Solver** (DFS + Backtracking)
- **Timer & Score System**
- **Combo System** for consecutive correct moves
- **Difficulty Levels** (Easy / Medium / Hard)
- **Automatic Difficulty Analyzer**
- **High Score System** per level
- **Dark / Light Mode Toggle**
- **Sound Effects** (move, win, loss, error)
- **Custom Dialogs** (Win / Lose / Confirm)
- **Game State Management** (Start, Play, Solve, Finish)

---

## Gameplay

- Use **Arrow Keys** to move the player
- Reach the **exit (goal)** to win the game
- Time decreases the score gradually
- Extra bonuses:
  - Fast completion
  - No solver usage
  - Combos
- Available actions:
  - Start a new game
  - Change difficulty level
  - Toggle Dark Mode 🌙
  - Let DFS solve the maze automatically

---

## Algorithms Used

### DFS (Depth First Search)

- Used in **Maze Generation**
- Used in **Maze Solving**
- Implemented manually without external libraries
- Includes backtracking logic

---

## OOP Concepts Used

- **Encapsulation**
  - Player state, score, timer, and movement logic
- **Abstraction**
  - Separation between maze generation, solving, and rendering
- **Inheritance**
  - Custom UI components extend `JPanel` and `JDialog`
- **Polymorphism**
  - Event handling through listeners
- **Interfaces**
  - `GameOverListener` for loose coupling
- **Enums**
  - `GameState`, `Difficulty`, `Sound`
- **Separation of Concerns**
  - Clear separation between UI, logic, and models

---

## Academic Notes

- Focus on clean **OOP design**
- Each class has a single responsibility
- Loose coupling between components
- Manual implementation of algorithms
- No game engines used

---

## Team Members

- Ahmed Rabee Mohamed  
- Ammar Abdalkber Abdelkader  
- Mohamed Khalaf  
- Ahmed Mohamed Khalaf  
- John Hany  
- Mohamed Ali Foda
---

## Project Structure

```bash
MazeGame/
├─ Main.java                    # Application entry point
├─ MazePanel.java               # Core game rendering & input handling
├─ MazeGenerator.java           # Random maze generation (DFS)
├─ MazeSolver.java              # Maze solving (DFS)
├─ MazeDifficultyAnalyzer.java  # Analyze maze difficulty
├─ RightSidePanel.java          # Score, timer, level, combo UI
├─ navPanel.java                # Top navigation bar
├─ GameSummaryDialog.java       # Win/Lose summary dialog
├─ CustomMessage.java           # Custom confirmation/info dialogs
├─ Theme.java                   # Dark / Light mode handling
├─ AppColors.java               # Centralized theme-aware colors
├─ HighScoreManager.java        # Save & load best scores
├─ GameState.java               # Enum for game states
├─ GameOverListener.java        # Game over callback interface
├─ SoundManager.java            # Sound control & playback
├─ Sound.java                   # Enum for sound effects
├─ model/
│   ├─ Player.java              # Player movement & animation
│   ├─ Level.java               # Level configuration
│   └─ Difficulty.java          # Difficulty enum
├─ resources/
│   ├─ icons/                   # UI icons
│   └─ sounds/                  # Game sounds
└─ README.md
