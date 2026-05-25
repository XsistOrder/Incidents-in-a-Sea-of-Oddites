package main;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 SaveManager - handles all saving and loading for the game

 Save files are stored in:
 C:\Users\<username>\AppData\Local\Incidents_In_A_Sea_Of_Oddities\

 The save file is a .json file
 Each saved variable has a name (key) and a value

 examples:

 save.set("day", 5);
 save.set("score", 1200);
 save.set("playerName", "Alex");
 save.set("musicVol", 0.8f);
 save.set("hardMode", true);

 writing to disk
 save.write();

 loading from disk
 save.load();

 reading variables back

 int day = save.getInt("day", 1); //1 is default if not found
 float vol = save.getFloat("musicVol", 1.0f);
 String name = save.getString("playerName", "");
 boolean hard = save.getBoolean("hardMode", false);

 check if a key exists

 save.has("day");       returns either true or false

 remove a key from the save

 save.remove("score");

 delete the save file entirely

 save.deleteSave();

 wipe all in-memory values AND delete the file (fresh start)

 save.resetSave();

 print all saved values to console (for debugging)
 save.printAll();

 Check when the file was last saved

 save.getLastSaveTime();

 supported save types:

 String, int, long, float, double, boolean, char, byte, short
 */

public class SaveManager {

    private static final String APP_FOLDER = "Incidents_In_A_Sea_Of_Oddities";
    private static final String SAVE_FILE = "save.json";

    //in-memory storage

    private final Map<String, String> data = new LinkedHashMap<>();

    private final Path saveDir;
    private final Path saveFile;

    private String lastSaveTime = "Never";

    public SaveManager() {
        //resolve path

        String appData = System.getenv("LOCALAPPDATA");
        //failsafe
        if (appData == null || appData.isEmpty()) {
            appData = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Local";
        }
        saveDir = Paths.get(appData, APP_FOLDER);
        saveFile = saveDir.resolve(SAVE_FILE);

        //create the folder if it doesn't exist yet

        try {
            Files.createDirectories(saveDir);
        } catch (IOException e) {
            System.err.println("[SaveManager] Could not create save directory: " + saveDir);
        }

        //if a save file already exists, load it automatically

        if (Files.exists(saveFile)) {
            load();
        }
    }

    public void set(String key, String value) {data.put(key, value);}
    public void set(String key, int value) {data.put(key, Integer.toString(value));}
    public void set(String key, long value) {data.put(key, Long.toString(value));}
    public void set(String key, float value) {data.put(key, Float.toString(value));}
    public void set(String key, double value) {data.put(key, Double.toString(value));}
    public void set(String key, boolean value) {data.put(key, Boolean.toString(value));}
    public void set(String key, char value) {data.put(key, Character.toString(value));}
    public void set(String key, byte value) {data.put(key, Byte.toString(value));}
    public void set(String key, short value) {data.put(key, Short.toString(value));}

    //returns the raw string value, or default value if not found

    public String getString(String key, String defaultValue) {
        return data.getOrDefault(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        String v = data.get(key);
        if (v == null) return defaultValue;
        try {return Integer.parseInt(v);}
        catch (NumberFormatException e) {
            System.err.println("[SaveManager] getInt: \"" + key + "\" is not an int (value: " + v + ")");
            return defaultValue;
        }
    }

    public long getLong(String key, long defaultValue) {
        String v = data.get(key);
        if (v == null) return defaultValue;
        try {return Long.parseLong(v);}
        catch (NumberFormatException e) {
            System.err.println("[SaveManager] getLong: \"" + key + "\" is not a long (value: " + v + ")");
            return defaultValue;
        }
    }

    public float getFloat(String key, float defaultValue) {
        String v = data.get(key);
        if (v == null) return defaultValue;
        try {return Float.parseFloat(v);}
        catch (NumberFormatException e) {
            System.err.println("[SaveManager] getFloat: \"" + key + "\" is not a float (value: " + v + ")");
            return defaultValue;
        }
    }

    public double getDouble(String key, double defaultValue) {
        String v = data.get(key);
        if (v == null) return defaultValue;
        try {return Double.parseDouble(v);}
        catch (NumberFormatException e) {
            System.err.println("[SaveManager] getDouble: \"" + key + "\" is not a double (value: " + v + ")");
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String v = data.get(key);
        if (v == null) return defaultValue;
        if (v.equalsIgnoreCase("true")) return true;
        if (v.equalsIgnoreCase("false")) return false;
        System.err.println("[SaveManager] getBoolean: \"" + key + "\" is not a boolean (value : " + v + ")");
        return defaultValue;
    }

    public char getChar(String key, char defaultValue) {
        String v = data.get(key);
        if (v == null || v.isEmpty()) return defaultValue;
        return v.charAt(0);
    }

    public byte getByte(String key, byte defaultValue) {
        String v = data.get(key);
        if (v == null) return defaultValue;
        try {return Byte.parseByte(v);}
        catch (NumberFormatException e) {
            System.err.println("[SaveManager] getByte: \"" + key + "\" is not a byte (value: " + v + ")");
            return defaultValue;
        }
    }

    public short getShort(String key, short defaultValue) {
        String v = data.get(key);
        if (v == null) return defaultValue;
        try {return Short.parseShort(v);}
        catch (NumberFormatException e) {
            System.err.println("[SaveManager] getShort: \"" + key + "\" is not a short (value: " + v + ")");
            return defaultValue;
        }
    }

    //returns true if the key exists in the save data

    public boolean has(String key) {
        return data.containsKey(key);
    }

    public boolean remove(String key) {
        if (!data.containsKey(key)) {
            System.err.println("[SaveManager] remove: key \"" + key + "\" not found");
            return false;
        }
        data.remove(key);
        return true;
    }

    //returns all currently stored keys

    public Set<String> getAllKeys() {
        return Collections.unmodifiableSet(data.keySet());
    }

    //returns how many keys are currently stored

    public int getKeyCount() {
        return data.size();
    }

    //Disk operations

    //writes all in-memory data to the save file
    //creates the file if it doesn't exist, overwrites it if it does

    public boolean write() {
        try {
            Files.createDirectories(saveDir);
            lastSaveTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("  \"_lastSaved\": \"").append(lastSaveTime).append("\"");

            for (Map.Entry<String, String> entry : data.entrySet()) {

                sb.append("{\n");
                sb.append("  \"").append(escapeJson(entry.getKey())).append("\": ");
                sb.append("\"").append(escapeJson(entry.getValue())).append("\"");
            }

            sb.append("\n}");
            Files.writeString(saveFile, sb.toString());
            System.out.println("[SaveManager] Saved to: " + saveFile);
            return true;
        } catch (IOException e) {
            System.err.println("[SaveManager] Failed to write save: " + e.getMessage());
            return false;
        }
    }

    //loads data from the save file into memory
    //existing in-memory keys are replaced by what is on disk
    //returns false if the file doesn't exist or can't be parsed

    public boolean load() {
        if (!Files.exists(saveFile)) {
            System.out.println("[SaveManager] No save file found at: " + saveFile);
            return false;
        }
        try {
            String content = Files.readString(saveFile);
            data.clear();
            parseJson(content);
            lastSaveTime = data.getOrDefault("_lastSaved", "Unknown");
            data.remove("_lastSaved"); //don't expose the internal timestamp as a user key
            System.out.println("[SaveManager] Loaded from: " + saveFile);
            return true;
        } catch (IOException e) {
            System.err.println("[SaveManager] Failed to read save: " + e.getMessage());
            return false;
        }
    }

    //deletes the save file from disk, In-memory data is kept
    //returns true if the file was deleted, false if it didn't exist or failed

    public boolean deleteSave() {
        try {
            boolean deleted = Files.deleteIfExists(saveFile);
            if (deleted) {
                System.out.println("[SaveManager] Save file deleted.");
            } else {
                System.out.println("[SaveManager] No save file to delete.");
            }
            return deleted;
        } catch (IOException e) {
            System.err.println("[SaveManager] Failed to delete save: " + e.getMessage());
            return false;
        }
    }

    //Clears all in-memory data AND deletes the save file.
    //Use for a full "new game" reset

    public void resetSave() {
        data.clear();
        deleteSave();
        lastSaveTime = "Never";
        System.out.println("[SaveManager] Save reset. All data cleared.");
    }

    //Checks whether a save files currently exists on disk

    public boolean saveExists() {
        return Files.exists(saveFile);
    }

    //Returns the fill path to the save file as a String

    public String getSavePath() {
        return saveFile.toString();
    }

    //Returns the timestamp of the last write() call, or "Never"

    public String getLastSaveTime() {
        return lastSaveTime;
    }

    //debug

    //Prints all in-memory key-value pairs to the console

    public void printAll() {
        System.out.println("SaveManager] --- Save Data (" + data.size() + " keys) ---");
        if (data.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
            }
        }
        System.out.println("[SaveManager] Last saved: " + lastSaveTime);
        System.out.println("[SaveManager] File: " + saveFile);
    }

    //Minimal JSON parser - handles flat key/value format writes

    private void parseJson(String json) {
        //Strip outer braces
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        //split on commas that are followed (eventually) by a quoted key
        String[] lines = json.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.equals("{") || line.equals("}")) continue;

            //each line looks like: "key": "value",
            int colonIdx = line.indexOf("\": \"");
            if (colonIdx == -1) continue;

            String key = line.substring(1, colonIdx); //strip leading "
            String rest = line.substring(colonIdx + 4); //skip ": "
            //Strip trailing ", or "
            if (rest.endsWith("\",")) rest = rest.substring(0, rest.length() - 2);
            else if (rest.endsWith("\"")) rest = rest.substring(0, rest.length() - 1);

            data.put(unescapeJson(key), unescapeJson(rest));
        }
    }

    //Escapes special characters for JSON values

    private String escapeJson(String s) {
        return s.replace("\\", "\\\"")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    //reverses JSON escape sequences

    private String unescapeJson(String s) {
        return s.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
    }
}