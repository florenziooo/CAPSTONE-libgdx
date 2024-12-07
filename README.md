# Wildcat's Den: Explore the Heart of CIT-U
Welcome to Wildcat's Den, an exploration game that transforms CIT-U into a virtual playground for learning and discovery. This interactive experience is designed specifically for students, allowing them to explore the university’s facilities, programs, and organizations in an engaging and fun way.

Whether you're a newcomer eager to familiarize yourself with your new academic home or simply curious about what CIT-U has to offer, Wildcat's Den is your ultimate guide.

## Get Involved
- 🎨 Got a knack for design or storytelling? Help us craft more engaging quests and areas.
- 🐾 Join our team of testers to refine gameplay and improve the experience for all Wildcats.
- 💡 Have a feature idea? We’d love to hear it! Submit your suggestions here.

## Why Wildcat’s Den?
- 🔍 Uncover every corner of CIT-U—from bustling academic halls to hidden gems only true Wildcats know about.
- 🎮 Engage with facilities, learn about programs, and meet organizations in a gamified environment.
- ✨ Follow guided quests or roam freely—your adventure, your pace.
- 🐾 Dive deep into the university’s culture and traditions, and feel a sense of belonging like never before.

## Acknowledgements
This game is a tribute to the vibrant community and culture of Cebu Institute of Technology - University. Special thanks to:

 - CIT-U Staff and Alumni for their invaluable insights.
 - Members of the Supreme Student Government for their support.
 - Players Like You who bring the Wildcat’s Den to life.


## Design Patterns
### Singleton Design Pattern
#### GameManager Class
The Singleton Design Pattern is implemented in the `GameManager` class to ensure that only one instance of the class exists during the game's lifecycle. This pattern provides a single point of access to shared data, such as `playerName`, `npcFound`, `buildingsFound`, and `volume`, making it easy to manage and synchronize game state globally.

### Builder Design Pattern
#### SoundManager Class
The **Builder Design Pattern** is used in the `SoundManager` class to simplify the creation of complex sound objects with multiple configurations. The `Builder` class allows incremental and flexible object construction through method chaining.

- The `Builder` class provides methods like `setbgMusic()` and `setAmbience()` to set specific properties of the `SoundManager` instance.
- The `build()` method creates a new `SoundManager` instance, passing the builder itself for initialization.

### State Design Pattern
#### Screen State Class
The **State Design Pattern** is implemented through the `ScreenState` interface and the `WildCatScreen` abstract class. This pattern allows the game's screen to change dynamically, representing different states of the application (e.g., main menu, gameplay, or pause menu).

- **`ScreenState` Interface**: Defines the behavior for transitioning between screens with the `changeScreen(GameScreen screen)` method.
- **`WildCatScreen` Abstract Class**: Serves as a base class for specific screens, like gameplay or menus, and ensures consistent structure and behavior for screen management.

This pattern simplifies state transitions and promotes better organization by encapsulating screen-specific behavior in separate classes.
### Game Loop Design Pattern

The **Game Loop Design Pattern** is inherently utilized in the `Game` class provided by the libGDX framework. This pattern is foundational in game development, managing the continuous update and rendering cycle that drives a game.

- **Core Concept**: The game loop ensures that the game processes input, updates the game state, and renders frames in a consistent and efficient manner, regardless of the platform or hardware.
- **Implementation in libGDX**:
    - The `render()` method in the `Game` class is called repeatedly, typically synchronized with the frame rate. It invokes the `render()` method of the active `Screen`, passing the time delta since the last frame.
    - Methods like `pause()`, `resume()`, and `resize()` handle transitions between different game states or screen dimensions.
    - The `setScreen()` method allows swapping between different game screens while maintaining a consistent loop.

In the `GameLauncher` class, which extends `Game`, the `create()` method initializes resources and sets the initial game screen (e.g., `MainMenu`). The game loop then manages the flow of the application, invoking the screen's `render()` and other lifecycle methods as needed.

This design pattern ensures that the game's state remains synchronized with the rendering process, providing smooth gameplay and responsiveness.

### Factory Design Pattern
#### NPCFactory Class

The **Factory Design Pattern** is implemented in the `NPCFactory` class to simplify the creation of NPCs with predefined attributes and behaviors.

- **Methods**:
    - createNPCs() method: Generates a list of NPCs with specified positions and movement types.
    - setTargets() method: Configures dynamic movement targets for specific NPCs. 

This pattern ensures consistent NPC initialization and supports easy expansion or modification of NPC types.

## Additional References

This is a [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

## About LibGDX
### Platforms
- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

### Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
