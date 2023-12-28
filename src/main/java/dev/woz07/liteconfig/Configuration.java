package dev.woz07.liteconfig;

import com.sun.jdi.request.DuplicateRequestException;
import dev.woz07.liteconfig.customs.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Configuration {
    private File file;                  // The file used for reading/writing
    private char delimiter;             // The delimiter that splits up the data
    private final List<Pair> storage;   // Contains all the data from file
    
    /**
     * Default constructor
     */
    public Configuration() throws IOException {
        storage = new ArrayList<>();
        file = null;                    // Default value
        delimiter = '=';                // Default value
    }
    
    /**
     * Secondary constructor
     * @param target    The target file which contains the data you want
     * @param delimiter The delimiter which splits the data up
     */
    public Configuration(File target, char delimiter) throws Exception {
        setFile(target);
        setDelimiter(delimiter);
        storage = new ArrayList<>();
        read();
    }
    
    /**
     * Add a pair into `storage`
     * @param pair The pair you would like to add to `storage`
     */
    public void push(Pair pair) {
        if (exists(pair)) {
            throw new DuplicateRequestException("Pair already exists, cannot push duplicate");
        }
        storage.add(pair);
    }
    
    /**
     * Remove the last element within `storage`
     */
    public void pop() {
        if (!storage.isEmpty()) {
            storage.remove(storage.size() - 1);
        } else {
            throw new NoSuchElementException("Cannot pop from an empty list");
        }
    }
    
    /**
     * Get an element within `storage`
     * @param index The index of the element you want
     * @return Pair of the storage index
     */
    public Pair peek(int index) {
        if (index >= 0 && index < storage.size()) {
            return storage.get(index);
        } else {
            throw new NoSuchElementException("Cannot peek at index " + index + " in storage");
        }
    }
    
    /**
     * Get all of storage
     * @return `storage`
     */
    public List<Pair> getAll() throws IOException {
        read();
        return storage;
    }
    
    /**
     * Override a value in `storage`
     * @param key      The key of the value you want to override
     * @param newValue The new value you want
     */
    public void overrideValue(String key, String newValue) {
        for (Pair p : storage) {
            if (p.getKey().equals(key)) {
                p.setValue(newValue);
                return;
            }
        }
        throw new NoSuchElementException("No such key " + key + " exists within storage");
    }
    
    /**
     * Override a key in `storage`
     * @param oldKey The old key you would like to replace
     * @param newKey The new key you want instead
     */
    public void overrideKey(String oldKey, String newKey) {
        for (Pair p : storage) {
            if (p.getKey().equals(oldKey)) {
                p.setKey(newKey);
                return;
            }
        }
        throw new IllegalArgumentException("No such key " + oldKey + " exists within storage");
    }
    
    /**
     * Remove element from `storage`
     * @param pair The pair you would like to remove
     */
    public void remove(Pair pair) {
        Iterator<Pair> iterator = storage.iterator();
        while (iterator.hasNext()) {
            Pair p = iterator.next();
            if (p.getKey().equals(pair.getKey())) {
                iterator.remove();
                return;
            }
        }
        throw new NoSuchElementException("No such element with key '" + pair.getKey() + "' exists within storage");
    }
    
    /**
     * Clears the content within `storage`
     */
    public void clear() {
        storage.clear();
    }
    
    /**
     * Check if a pair exists within `storage`
     * @param pair The pair you want to check for
     * @return True/False depending on if the pair exists within `storage`
     */
    public boolean exists(Pair pair) {
        for (Pair p : storage) {
            if (p.equals(pair)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if a pair exists within a List
     * @param pair The pair you would like to check for
     * @param list The list you would like to check within
     * @return True/False depending on if the pair exists within the list
     */
    public boolean existsInList(Pair pair, List<Pair> list) {
        for (Pair p : list) {
            if (p.equals(pair)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This function returns value based on the given key
     * @param key The key of the value you want to get
     * @return The value based on key else null
     */
    public String get(String key) throws IOException {
        read();
        for (Pair pair : storage) {
            if (pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }
        return null;
    }
    
    /**
     * This function allows you to update `storage`
     * @throws IOException Thrown because of file handling
     */
    public void update() throws IOException {
        read();
    }
    
    /**
     * Function to write data to file
     * @throws IOException Thrown when handling files
     */
    public void write() throws IOException {
        FileWriter writer = new FileWriter(getFile());
        for (Pair pair : storage) {
            writer.write(pair.getKey() + getDelimiter() + pair.getValue() + "\n");
        }
        writer.close();
    }
    
    /**
     * Function to update `storage` by reading keys and values from `file`
     * @throws IOException Thrown when handling files
     */
    private void read() throws IOException {
        removeDuplicates();
        FileReader reader = new FileReader(getFile());
        BufferedReader br = new BufferedReader(reader);
        
        String line;
        String[] split;
        while ((line = br.readLine()) != null) {
            split = line.split(String.valueOf(getDelimiter()));
            try {
                Pair pair = new Pair(split[0], split[1]);
                if (!exists(pair)) {
                    storage.add(pair);
                }
            } catch (DuplicateRequestException ignore) {
            }
        }
        
        br.close();
        reader.close();
    }
    
    /**
     * Removes duplicates from `storage`
     */
    private void removeDuplicates() {
        List<Pair> uniques = new ArrayList<>();
        
        for (Pair pair : storage) {
            if (!existsInList(pair, uniques)) {
                uniques.add(pair);
            }
        }
        
        storage.clear();
        storage.addAll(uniques);
    }
    
    /**
     * Set method for `file`
     * @param file The new `file` you want
     */
    public void setFile(File file) {
        if (file == null) {
            throw new NullPointerException("File cannot be null");
        }
        this.file = file;
    }
    
    /**
     * Get method for `file`
     * @return `file`
     */
    public File getFile() {
        if (file == null) {
            throw new NullPointerException("File cannot be null");
        }
        return file;
    }
    
    /**
     * Set method for `delimiter`
     * @param delimiter The new `delimiter` you want
     */
    public void setDelimiter(char delimiter) throws Exception {
        for (char option : new char[]{'=', ':'}) {
            if (delimiter == option) {
                this.delimiter = delimiter;
                return;
            }
        }
        throw new Exception("Invalid delimiter");
    }
    
    /**
     * Get method for `delimiter`
     * @return `delimiter`
     */
    public char getDelimiter() {
        return this.delimiter;
    }
}
