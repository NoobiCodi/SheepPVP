package fr.mrartichaud.sheeppvp.config;

import fr.mrartichaud.sheeppvp.SheepPvp;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class JsonFile {
    private File file;
    private JSONObject jsonObject;

    public JsonFile(File file, SheepPvp sheepPvp) {
        this.file = file;

        if (!this.file.exists()) sheepPvp.saveResource(this.file.getName(), false);

        try {
            JSONParser jsonParser = new JSONParser();
            Object parsed = jsonParser.parse(new FileReader(this.file));

            this.jsonObject = (JSONObject) parsed;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    private JSONObject getPath(String path, JSONObject obj) {
        String[] paths = path.split("\\.");

        int objs = paths.length - 1;

        if (objs < 1) {
            return obj;
        } else {
            obj = (JSONObject) obj.get(paths[0]);
            paths = Arrays.copyOfRange(paths, 1, paths.length);
            return getPath(String.join(".", paths), obj);
        }
    }

    private Object getFinalPath(String path) {
        String[] paths = path.split("\\.");
        JSONObject jsonObject = this.getPath(path, this.jsonObject);
        return jsonObject.get(paths[paths.length - 1]);
    }

//    public void set(String path, Object value) {
//        String[] paths = path.split("\\.");
//        JSONObject jsonObject = this.getPath(path, this.jsonObject);
//
//        jsonObject.
//    }

    public void save() {
        try (FileWriter file = new FileWriter(this.file)) {

            file.write(this.jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        return (String) getFinalPath(path);
    }

    public int getInt(String path) {
        return Math.toIntExact((long) getFinalPath(path));
    }

    public JSONObject get(String path) {
        return (JSONObject) getFinalPath(path);
    }
}
