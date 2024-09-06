# File server
![](https://img.shields.io/badge/Java-Programming-green)
## Description
An educational project written in vanilla Java 17 utilizing some important language concepts. 
It simulates a file server that can work with multiple clients at once to handle crucial file storage operations.
Clients are communicating with server through requests built based on user input from CLI. 
Communication between clients and server relys on requests and responses containing files and data needed to identify them and the operation to perform.
It's possible to store files on the server, download them or remove. File storage is persisted through the server sessions.

## Technologies
* Vanilla Java 17
## Concepts Implemented
The goal of learning projects is to learn, understand and apply certain concepts in practice. 

This project utilizes concepts of:
* Networking using sockets
* Client/server architecture
* File management
* Data streams
* Multithreading and concurrency
* Builder design pattern
* Serialization and deserialization

## Installation

**1. Clone the repository:**

```
$ git clone https://github.com/Tyall/hs-file-server.git
$ cd hs-file-server
```
**2. Compile the code**

Use chosen compiler or built in IDE to compile the source code.

**3. Run the application server:**

```
$ java src/server/Main.java
```
## Usage

Make sure the server instance is running.
Every request needs a new instance of the client application so the first step is to run the client.

```
$ java src/client/Main.java
```

Use command line interface to build the request. You will be guided by prompts.
Below you will find some examples. Note that the > symbol represents the user input. It's not part of the input.


**Transfer and save file on the server**
```
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > 2
Enter name of the file: > my_cat.jpg
Enter name of the file to be saved on server: > my_cat_remote.jpg
The request was sent.
Response says that file is saved! ID = 23
```


**Download file from the server and save it locally**
```
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > 1
Do you want to get the file by name or by id (1 - name, 2 - id): > 2
Enter id: > 23
The request was sent.
The file was downloaded! Specify a name for it: > cat.jpg
File saved on the hard drive!
```


**Delete file on the server**
```
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > 3
Do you want to delete the file by name or by id (1 - name, 2 - id): > 2
Enter id: > 23
The request was sent.
The response says that this file was deleted successfully!
```


**Shutdown the server**
```
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > exit
The request was sent.
```


## Project status
As in every project there are always some improvements to be done.

List of known things ToDo:
* Request validation
* External configuration
* Extended logging and error handling
