# Cardio Data Simulator

The Cardio Data Simulator is a Java-based application designed to simulate real-time cardiovascular data for multiple patients. This tool is particularly useful for educational purposes, enabling students to interact with real-time data streams of ECG, blood pressure, blood saturation, and other cardiovascular signals.

## Features

- Simulate real-time ECG, blood pressure, blood saturation, and blood levels data.
- Supports multiple output strategies:
  - Console output for direct observation.
  - File output for data persistence.
  - WebSocket and TCP output for networked data streaming.
- Configurable patient count and data generation rate.
- Randomized patient ID assignment for simulated data diversity.

## Getting Started

### Prerequisites

- Java JDK 11 or newer.
- Maven for managing dependencies and compiling the application.

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tpepels/signal_project.git
   ```

2. Navigate to the project directory:

   ```sh
   cd signal_project
   ```

3. Compile and package the application using Maven:
   ```sh
   mvn clean package
   ```
   This step compiles the source code and packages the application into an executable JAR file located in the `target/` directory.

### Running the Simulator

After packaging, you can run the simulator directly from the executable JAR:

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## UML Models
To plan how the components of the different systems in this project link together we created UML diagrams. They include the Alert-generation system, the Patient Identification System, the Data Access Layer and the Data storage system. 
See here:
https://github.com/lillymeravirgi//project_se/tree/master/uml_models

Remark: the final code might slightly differ from structure shown in the UML diagrams. The UML diagramms were created using lucid.app they are a result of both Virginia's and Kira's work. Uploaded as one.

## JUnit Test and Code Coverage
Test results and coverage screenshots:
https://github.com/lillymeravirgi/project_se/tree/master/unit_test_verfication
We provided tests for the AlertGenerator, Alert, FileOutputStrategy, TcpOutputStrategy, DataStorage, DataFileReader, Patient, and PatientRecord classes. 
The Main class was not tested, as it only handles basic routing and control logic. 
We also did not write separate tests for the data generators; instead, we used them to simulate input for testing the alert logic. Since the alerts were triggered correctly during these tests, we considered the generators to be functioning as expected.

## Project Members Group 14
- Student ID Virginia Simonetto: 6394271
- Student ID Kira Frielingsdorf: 6374869
