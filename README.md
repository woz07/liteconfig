# NOTE: UNDER HEAVY DEVELOPMENT STILL

# liteconfig
Light weight configuration management system in Java and Kotlin

> Liteconfig uses java 11

## What is it
Liteconfig is an easy-to-use and lightweight configuration library in Java.
The purpose of liteconfig is to allow you to store key value pairs within 
a file, essentially you can treat liteconfig as your configuration system.

## How to use
### Local Maven
1. Git clone `https://www.github.com/woz07/liteconfig` into your local machine.
2. Run the maven command `clean install`, which will install the jar to your local maven repository.
3. In your `pom.xml` add the dependency:
   ```xml
        <dependency>
            <groupId>github.woz07.liteconfig</groupId>
            <artifactId>liteconfig</artifactId>
            <version>1.0</version>
        </dependency>
   ```
---

### Default Java
1. Create a `lib` folder within your project.
2. Install the release of your choice.
3. Drag and drop the `.jar` file into your `lib` folder.
4. Configure your project to use the `.jar` file.

## Documentation
### Getting started
Liteconfig is very simple and easy to use, so getting started in it is quite simple.
To begin you need to initialize an object of `Configuration.java`.
This is the object which you will be interacting with the configuration 
system with.