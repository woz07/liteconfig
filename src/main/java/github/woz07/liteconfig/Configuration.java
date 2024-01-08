package github.woz07.liteconfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author woz07
 *
 * Configuration.java
 * This is the base class of liteconfig.
 * It is also the class that must be initialized and used.
 */

public class Configuration {
    private File target;
    private final char delimiter = '=';
    public Configuration(File target) {
        setTarget(target);
    }
    
    /**
     * Add data to the file
     * @param key   The key you want added
     * @param value The value you want added paired to the key
     */
    public synchronized void set(final String key, final String value) {
        // Confirm key doesn't already exist to prevent multiple similar keys
        if (get(key) != null) {
            throw new IllegalArgumentException("Key already exists within " + target.getName());
        }
        // Write out data to file
        try (FileWriter writer = new FileWriter(target, true)) {
            writer.write(key + delimiter + value + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get `value` based on key
     * @param key   The key you would like to use to get the value
     * @return      The value associated to the provided key
     */
    public synchronized String get(final String key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(String.valueOf(delimiter));
                // Check if parts equals to
                if (parts[0].equals(key)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get rid of key and value from file
     * @param key   The key you want removed from file (and it's value)
     */
    public synchronized void remove(final String key) {
        List<String> storage = new ArrayList<>();
        // Add whatever isn't equal to key into storage
        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(String.valueOf(delimiter));
                if (!parts[0].equals(key)) {
                    storage.add(parts[0] + delimiter + parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Write out all the contents of storage to file
        write(storage);
    }
    
    /**
     * Update a key from current to a new key
     * @param old           The old key that is within the file
     * @param replacement   The old keys replacement
     */
    public synchronized void updateKey(final String old, final String replacement) {
        List<String> storage = new ArrayList<>();
        // Update old key with replacement key and add it all to storage
        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(String.valueOf(delimiter));
                // Put the replacement, and it's correct value in if target equals parts[0] (key)
                if (parts[0].equals(old)) {
                    storage.add(replacement + delimiter + parts[1]);
                } else {
                    storage.add(parts[0] + delimiter + parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Write out all the contents of storage to file
        write(storage);
    }
    
    /**
     * Update a value from current to a new value
     * @param key           The key associated to the old value
     * @param replacement   The old values replacement
     */
    public synchronized void updateValue(final String key, final String replacement) {
        List<String> storage = new ArrayList<>();
        // Update old value with replacement and add it all to storage
        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(String.valueOf(delimiter));
                if (parts[0].equals(key)) {
                    storage.add(parts[0] + delimiter + replacement);
                } else {
                    storage.add(parts[0] + delimiter + parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Write out all the contents of storage to file
        write(storage);
    }

    /**
     * Clear data within file
     */
    public synchronized void clear() {
        try (FileWriter writer = new FileWriter(target)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get method for target
     * @return  `target`
     */
    public synchronized File getTarget() {
        return target;
    }

    /**
     * Set method for target
     * @param file  The new target file you want
     */
    public synchronized void setTarget(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        target = file;
    }

    /**
     * Private method to write data to file (no appending)
     * @param source    Everything within source gets written out
     */
    private synchronized void write(List<String> source) {
        try (FileWriter writer = new FileWriter(target)) {
            for (String line : source) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
