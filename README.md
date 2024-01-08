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
This is the object which you will be interacting the most.

> Note: It doesn't matter what you call your file so long as it's a txt file

```java
import github.woz07.liteconfig.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(new File("config.txt"));
    }
}
```
`Configuration.java` has a constructor which requires a file, this file also cannot 
be null either. If you want to change the file later on you can do that by calling 
`setFile(File f)` later on.

### Set

To actually write data to the file you need to utilize the `set(String k, String v)` 
method, which writes out the data to the file for you.

> Note: If you try to call `set()` and pass a String key that already exists within the file then an exception will be thrown

Here is an example of how you can write some basic data to a file:
```java
import github.woz07.liteconfig.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(new File("config.txt"));
        configuration.set("app.name", "testing123");
        configuration.set("app.width", "800");
        configuration.set("app.height", "600");
        
        // this would throw an exception as "app.name" already exists within the file
        configuration.set("app.name", "exception123");
    }
}
```

### Get

To get values from the file you need to use the `get(String k)` method.
Which either returns the value or null if it can't find the key within the 
file.

Here is an example of how it can be used
```java
import github.woz07.liteconfig.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(new File("config.txt"));
        configuration.set("app.name", "testing123");
        
        System.out.println(configuration.get("app.name")); // prints out "testing123"
        
        // creating a gui with "testing123" as title
        JFrame frame = new JFrame(configuration.get("app.name"));
        frame.setPreferredSize(new Dimension(100, 100));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
```

### updateKey

If you want to update a key you can update it via this method.
The `updateKey(String o, String r)` requires 2 strings, one 
the current key and then the replacement key you want.

> Note: If you pass through an invalid `key` nothing happens, it just ignores it

Heres an example of how it's used
```java
import github.woz07.liteconfig.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(new File("config.txt"));
        configuration.set("app.name", "testing123");

        System.out.println(configuration.get("app.name")); // prints out "testing123"

        configuration.updateKey("app.name", "app.title");

        System.out.println(configuration.get("app.title")); // prints out "testing123"
    }
}
```

### updateValue

The `updateValue(String k, String r)` method allows you to update the value of a key.
It just requires you to pass through the key you want to override the value of along with 
the new value

> Note: If you pass through an invalid `key` nothing happens, it just ignores it

Heres an example of how its used
```java
import github.woz07.liteconfig.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(new File("config.txt"));
        configuration.set("app.name", "testing123");

        System.out.println(configuration.get("app.name")); // prints out "testing123"

        configuration.updateValue("app.name", "replaced");

        System.out.println(configuration.get("app.title")); // prints out "replaced"
    }
}
```

### Set/Get for file
There is a set and get method for the file so you can the file anytime you would 
like to a valid file and you can also get the file anytime you want.
- `setTarget(File f)`
- `getTarget()`

> Note: For `setTarget(File f)` you cannot pass through null

Here they are in example:
```java
import github.woz07.liteconfig.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file1 = new File("config.txt");
        File file2 = new File("app.txt");
        
        Configuration configuration = new Configuration(file1);
        
        if (configuration.getTarget() == file1) {
            configuration.setTarget(file2);
        }
    }
}
```

### Remove

The `remove(String k)` method expects a String key which it will look for 
in the file and then remove from the file, this is useful for getting rid of 
data that you don't want in the file anymore

```java
import github.woz07.liteconfig.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(new File("config.txt"));
        configuration.set("app.name", "testing123");
        configuration.set("app.width", "200");
        configuration.set("app.height", "400");
        configuration.set("user.id", "user_id");
        configuration.set("to_remove", "lorem ipsum text goes here");
        
        configuration.remove("to_remove");
    }
}
```

### Clear

The `clear()` method just clears the file out for you, should only realy, 
call when you don't want to use the data within the file as theres no way to get the data back once 
it's gone unless you yourself have setup a method for that.

Here is an example
```java
import github.woz07.liteconfig.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(new File("config.txt"));
        configuration.set("app.name", "testing123");
        configuration.set("app.width", "200");
        configuration.set("app.height", "400");
        configuration.set("user.id", "user_id");
        configuration.set("example", "lorem ipsum");
        
        // ...
        
        configuration.clear();
        // Now the data is gone and cant be retrieved
    }
}
```
