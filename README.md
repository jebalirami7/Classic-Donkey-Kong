<!--
## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
-->

# Classic Donkey Kong 2D

Welcome to the Classic Donkey Kong 2D repository! This repository hosts a 2D recreation of the classic Donkey Kong arcade game, built using Java and JavaFX for graphics in Visual Studio Code (VSCode). The game is a homage to the original Donkey Kong game released by Nintendo in 1981, featuring the iconic characters Mario and Donkey Kong in a platforming adventure.

<div align="center">
  <img src="https://github.com/jebalirami7/Classic-Donkey-Kong/assets/138411253/6860505b-afbd-436d-9366-02625c9e6cfa" alt="Demo">
</div>

## Demo Video

Check out the gameplay demo video below:

[![Demo Video](demo_thumbnail.png)](https://github.com/jebalirami7/Classic-Donkey-Kong/assets/138411253/5923a2a7-d9f9-44f3-b6c2-c3cdb9e75b31)

## Gameplay

In Classic Donkey Kong 2D, players control Mario as they navigate through a series of increasingly challenging levels to rescue Princess Peach from the clutches of Donkey Kong. Dodge barrels, climb ladders, and leap over obstacles to reach the top of each level and confront Donkey Kong himself.

## Features

- Faithful recreation of the original Donkey Kong gameplay experience.
- Multiple levels of increasing difficulty.
- Classic pixel art style reminiscent of the arcade era.
- Intuitive controls for smooth platforming action.

## Installation

To play Classic Donkey Kong 2D, follow these steps:

1. Clone or download the repository to your local machine.
2. Make sure you have Java Development Kit (JDK) installed on your machine.
3. Install Visual Studio Code (VSCode) and the "Java Extension Pack" extension.
4. Set up the JavaFX libraries and configurations in VSCode (see below).
5. Open the project folder in VSCode.
6. Compile the Java source files using `javac *.java` in the terminal.
7. Run the game using `java App` in the terminal.

## Setting up JavaFX Libraries in VSCode

1. Download the JavaFX SDK from the official website.
2. Extract the downloaded zip file to a location on your computer.
3. Inside your project folder, create a `.vscode` folder if it doesn't exist.
4. Inside the `.vscode` folder, create a `settings.json` file if it doesn't exist.
5. Add the following configuration to `settings.json`, replacing `"path/to/javafx-sdk"` with the actual path to your JavaFX SDK:

```json
{
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-11",
            "path": "path/to/jdk",
            "sources": [
                "path/to/javafx-sdk/lib/src.zip"
            ],
            "javadoc": "path/to/javafx-sdk/docs/api"
        }
    ]
}
```

## Contributing

Contributions to Classic Donkey Kong 2D are welcome! If you'd like to contribute, please follow these guidelines:

- Fork the repository.
- Create a new branch for your feature or bug fix.
- Make your changes and test thoroughly.
- Submit a pull request with a clear description of your changes.

<!--
## License

This project is licensed under the [MIT License](LICENSE).
-->

## Acknowledgments

- Original Donkey Kong game created by Nintendo.
- Graphics built using Java and JavaFX.

---

Thank you for checking out Classic Donkey Kong 2D! We hope you enjoy playing this nostalgic tribute to a classic arcade favorite. If you have any questions or feedback, feel free to reach out. Happy gaming! 🎮🍌
