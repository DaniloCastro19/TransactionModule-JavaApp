# Transaction-module

## Getting started

This README.md file provides instructions for setting up and running the Java project using Maven. Follow the steps below to get started.

## Prerequisites

Before you begin, ensure you have the following software installed on your system:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

## Installation Steps

### 1. Java Installation

If you don't have Java installed, follow these steps to install it:

1. Visit the [Oracle JDK Download Page](https://www.oracle.com/java/technologies/javase-downloads.html).

2. Download the appropriate version of the JDK for your operating system.

3. Follow the installation instructions provided for your platform.

4. Verify your installation by running the following command in your terminal or command prompt:

   ```bash
   java -version
   ```

### 2. Maven Installation

If you don't have Maven installed, follow these steps to install it:

1. Visit the Maven Download Page.

2. Download the latest version of Maven.

3. Follow the installation instructions provided for your platform.

4. Verify your installation by running the following command in your terminal or command prompt:

```bash
mvn -version
```
You should see the installed Maven version.

## Step 3: Clone the Project Repository

To get a copy of the project on your local machine, follow these steps:

1. Open your terminal or command prompt.

2. Navigate to the directory where you want to clone the project repository. You can use the `cd` command to change directories.

   ```bash
   cd /path/to/your/desired/directory
   ```
3. Clone the project repository using Git by running the following command:

   ```bash
   git clone git@gitlab.com:jala-university1/cohort-1/software-development-2-es/secci-n-c/capstone/transaction-module.git
   ```
    
   Replace your username with your GitHub username and your-java-project with the name of the Java project repository you want to clone.

4. Git will download the project files to your local directory. Once the cloning process is complete, you will have a local copy of the project on your machine.

Now you have successfully cloned the project repository to your local environment and can proceed with setting up and running the Java project.

### Step 4. Build and Run the Project

1. After cloning the repository, navigate to the project directory:

    ```bash
    cd transaction-module
   ```

#### Maven Goals

The project may have various Maven goals defined in its pom.xml file. Here are some common goals you can use:

**Clean and Build:** This goal cleans the project and then builds it, compiling the source code and creating the output artifact.

```bash
mvn clean install
```
**Run the Application:** If the project is an application with a main method, you can use the following command to run it:

```bash
mvn exec:java
```
**Run Tests:** To execute the project's unit tests, use the following command:

```bash
mvn test
```

Refer to the project's documentation or pom.xml file for additional Maven goals and configuration options specific to the project.


## Project Structure

The project follows a standard Maven directory structure:

```
transactio-module/
│
├── src/
│ ├── main/
│ │ ├── java/ # Java source code files
│ │ │ └── org.jala.university
│ │ │  ├── dao/ # Package to add custom Dao for the module
│ │ │  ├── domain/ # Package to add all the code with business logic for the module
│ │ │  └── presentation # Package to include all the UI code
│ │ └── resources/ # Resources (e.g., configuration files)
│ │
│ └── test/
│  ├── java/ # Test source code files
│  └── resources/ # Test resources
│
├── target/ # Compiled bytecode and packaged JARs
│
├── pom.xml # Project Object Model (POM) configuration
│
└── README.md # Project documentation (this file)
```
- **src/main/java**: This directory contains your main Java source code files. This is where you write the application code.

- **src/main/resources**: Resources necessary for the application, such as configuration files, properties, and templates, are placed here.

- **src/test/java**: Test source code files using JUnit or other testing frameworks reside here.

- **src/test/resources**: Test-specific resources, such as test configuration files or sample input data, are located in this directory.

- **target**: This directory is automatically generated by Maven. It contains compiled bytecode, JAR files, and other build artifacts.

- **pom.xml**: The Project Object Model (POM) file is used for configuring the project's dependencies, plugins, and other settings. It is the heart of the Maven project.


- **src/main/java/org/jala/university/domain/** All the functionality should be implemented on this module

- **src/main/java/org/jala/university/presentation/** Use this section to include all the UI functionality (the main view is *TransactionView*)
- For database connection to tables please use the generic *AccountDAO* or *TransactionDAO*, or provide your own Dao that extend *AbstractDAO*

