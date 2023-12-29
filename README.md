# liteconfig
Light weight configuration management system in Java and Kotlin

> Liteconfig uses java 11

## What is it
Liteconfig is a configuration library for Java applications, you can use it to 
read and write data to and from a txt. This shortens the need for your own 
seperate configuration class which you might have to update everytime you add 
a new element within your code, with liteconfig that is simplified to you just 
needing to use a simple `get()` method to retrieve values that you have set.

## How to use
### Local Maven
1. Git clone `https://www.github.com/woz07/liteconfig` into your local machine.
2. Run the maven command `clean install`, which will install the jar to your local maven repository.
3. In your `pom.xml` add the dependency:
   ```xml
        <dependency>
            <groupId>dev.woz07.liteconfig</groupId>
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
To get started you need to create an object of `Configuration.java`, this is the class 
you will be interacting with the most, there is another class called `Pair.java` which 
you wil lbe using as well however it's mainly oriented towards how you will have to send/ receive data.
You must also ensure that `file` and `delimiter` within `Configuration.java` have been initialized.
Heres how you can typically start out
```java
import dev.woz07.liteconfig.Configuration;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration configuration1 = new Configuration();
    }
    
    public static void main2(String[] args) throws Exception {
        File file = new File("data.txt");
        char delimiter = ':';
        Configuration configuration2 = new Configuration(file, delimiter);
    }
}
```
They both handle data differently and so will throw their own exceptions, but mainly the 
exceptions are related to the files reading/ writing

The structure of how data is written to the file is that its done via a list.
You just add data to the list and then eventually you call a certain method and it will 
write out the data for you in the file, you do not need to worry about calling a method to update data 
from a list that will be handled by the functions themselves but if you want then you can call the `update()` function, 
to access the values you must do it through the `get(String key)` method which just expects a String key.

Heres a basic example storing the apps name and then getting it
```java
import dev.woz07.liteconfig.Configuration;
import dev.woz07.liteconfig.customs.Pair;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("data.txt");
        char delimiter = '=';
        Configuration config = new Configuration(file, delimiter);
        Pair pair = new Pair("app.name", "testing");
        config.push(pair);
        config.write();
        
        //...
        
        String name = config.get("app.name");
    }
}
```
> You can push multiple pieces of data before you actually end up calling the `write()` function.
> Also you can space out your keys like `app name` or `app_name` but I just decided to use `app.name` as it
> seems more better to me

> You should only call 'get()' once you are certain that the key is there in the file
> else an error would be thrown

Once you have written a piece of data to the file, it will throw an error if you try to write it again 
as that data is within in the file already and cannot be written again

You can also update the file/ delimiter using their according `set..()` methods.

There will be a time when you want to perhaps update the key/value of your data.
You can easily achieve this by either calling `overrideKey(String o, String n)` or `overrideValue(String k, String nv)`
depending on what exactly you are trying to do
Heres an example use case
```java
import dev.woz07.liteconfig.Configuration;
import dev.woz07.liteconfig.customs.Pair;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("data.txt");
        char delimiter = '=';
        Configuration config = new Configuration(file, delimiter);
        Pair pair = new Pair("app.name", "testing");
        config.push(pair);
        config.write();
        
        config.overrideKey("app.name", "app_name");
        // once you override something it changes immediately
        config.overrideValue("app_name", "this_is_testing");
    }
}
```

There are a lot of other cool methods that have been provided for use within `Configuration.java`, please do 
make sure to check them out and if you encounter any issues please do let me know so I can fix them. Thanks.
