## Overview
I built a function plotter in Java featuring a custom parser for infix and reverse polish notation (RPN), AST-based evaluation, and symbolic differentiation. Includes support for common mathematical functions, parameters, and graph visualization. It is built using LiveViewProgramming for interactive browser-based output. <br>
📌 Credits to D. Herzberg's [*LiveViewProgramming*]("https://github.com/denkspuren/LiveViewProgramming") for providing the
Java-based live coding environment.

## Execution Guide for LVP with the Code
**Steps:**
1. Install at least JDK-24.
2. Extract the ZIP archive into a folder of your choice.
3. Open this folder in an IDE of your choice (e.g., Visual Studio Code) and navigate to the `src` directory.
4. Open a new terminal (inside your IDE) and run the following command:
    `java -jar lvp-0.5.4.jar --log --watch=functionplotter.java` <br>
5. LVP will start with `functionplotter.java` and can be accessed at: `http://localhost:50001/`

**Modifications to LVP: <br>**
Only the [Turtle.java](https://github.com/denkspuren/LiveViewProgramming/blob/main/src/main/java/lvp/views/Turtle.java) from [*LiveViewProgramming*](https://github.com/denkspuren/LiveViewProgramming) was reused and extended with a few additional methods to simplify and improve usability. The original Turtle implementation from the JAR file (`lvp-0.5.4.jar`) is not used in this project.

**Extensions:**
1. Input and visualization of multiple functions with distinct colors
2. Support for functions with a parameter `a`, allowing multiple parameter values to be displayed simultaneously
3. Derivation of functions

**Important Note**: The archive contains important files: <br>
    1. *functionplotter.java* --> The Java program for the function plotter<br>
    2. *lvp-0.5.4.jar* --> The JAR file for LVP <br>
    3. *Turtle.java* --> Self-developed extended Turtle with 3 additional methods, used instead of the Turtle from the JAR file (`lvp-0.5.4.jar`)<br>
    4. *README.md* --> Information about how to run the program<br>

When executing, make sure all files are present and you are in the correct directory!


### Alternative: Docker <br>
1. Install Docker on your computer.
2. Open a terminal and navigate to the folder where the Dockerfile is located.
3. Run the following command to build the Docker image: <br>
    `docker build -t functionplotter .`
4. Once the image is built, you can start the container with the following command: <br>
    `docker run -d -p 50001:50001 functionplotter`
5. LVP will start with `functionplotter.java` and can be accessed at: `http://localhost:50001/`