package dev.woz07.liteconfig.customs;

public class Pair {
    private String key, value;
    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     * Set method for key
     * @param key The new key you want
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Get method for key
     * @return `key`
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Set method for value
     * @param value The new value you want
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * Get method for value
     * @return `value`
     */
    public String getValue() {
        return value;
    }
}
