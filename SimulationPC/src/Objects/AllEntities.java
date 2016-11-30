/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Objects.Entity;
import Objects.EntityAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author kenca
 */
public class AllEntities {

    public ArrayList<Entity> allEntities = new ArrayList<>(25);

    public void addCell(Entity pCell) {
        allEntities.add(pCell);
    }

    public void remove(int i) {
        allEntities.remove(i);
    }

    public String saveToJSONFile(boolean pLocation, File pChoosenFile) throws IOException {
        // FALSE = Save to local path
        // TRUE = Save to remote
        if (pLocation == false) {
            // Save locally
            File file = pChoosenFile;
            if (file.exists()) {
                file.delete();
            }
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file, true));
            buffWriter.write("[");

            Gson gsonExt = null;
            {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Entity.class, new EntityAdapter());
                gsonExt = builder.create();
            }
            int i = 0;
            for (Entity animal : allEntities) {
                i++;
                String animalJson = gsonExt.toJson(animal, Entity.class);
                System.out.println(i + " " + allEntities.size());

                if (i != allEntities.size()) {
                    buffWriter.write(animalJson + ",");
                    buffWriter.newLine();
                } else {
                    buffWriter.write(animalJson);
                }

            }
            buffWriter.write("]");
            buffWriter.close();
            return "";
        } else {
            // Save to server
            String JSONString = "";

            JSONString += "[";

            Gson gsonExt = null;
            {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Entity.class, new EntityAdapter());
                gsonExt = builder.create();
            }
            int i = 0;
            for (Entity animal : allEntities) {
                i++;
                String animalJson = gsonExt.toJson(animal, Entity.class);
                if (i != allEntities.size()) {
                    JSONString += animalJson + ",";

                } else {
                    JSONString += animalJson;
                }

            }
            JSONString += "]";
            System.err.println(JSONString);
            return JSONString;
        }
        /*String filename = "sim.json";
        String JSONString = "";
        File file = new File(/*getFilesDir(),filename);
        if (file.exists()) {
            file.delete();
        }
        BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file, true));
        buffWriter.write("[");
        JSONString += "[";

        Gson gsonExt = null;
        {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Entity.class, new EntityAdapter());
            gsonExt = builder.create();
        }
        int i = 0;
        for (Entity animal : allEntities) {
            i++;
            String animalJson = gsonExt.toJson(animal, Entity.class);
            JSONString += animalJson;
            System.out.println(i + " " + allEntities.size());

            if (i != allEntities.size()) {
                buffWriter.write(animalJson + ",");
                JSONString += ",";
                buffWriter.newLine();
            } else {
                buffWriter.write(animalJson);
            }

        }
        buffWriter.write("]");
        JSONString += "]";
        buffWriter.close();

        return JSONString;
         */
    }

    public void loadFromJSONFile(boolean pLocation, String pJSONData, File pChoosenFile) {
        // FALSE = Save to local path
        // TRUE = Save to remote
        if (!pLocation) {
            File file = pChoosenFile;
            allEntities.clear();
            try {
                BufferedReader buffReader = new BufferedReader(new FileReader(file));
                String line;
                Gson gsonExt = null;
                {
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Entity.class, new EntityAdapter());
                    gsonExt = builder.create();
                }

                while ((line = buffReader.readLine()) != null) { // loop till you have no more lines
                    if (line.startsWith("[")) {
                        line = line.substring(1);
                    }
                    if (line.endsWith(",") || line.endsWith("]")) {
                        line = line.substring(0, line.length() - 1);
                    }
                    Entity myLoadedEntity = gsonExt.fromJson(line, Entity.class);
                    if (myLoadedEntity != null) {
                        allEntities.add(myLoadedEntity);
                    }

                }

            } catch (IOException e) {
                System.err.println("Error!");
            }
        } else {
            String line;
            Gson gsonExt = null;
            allEntities.clear();

            {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Entity.class, new EntityAdapter());
                gsonExt = builder.create();
            }

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Entity.class, new EntityAdapter());
            Gson gson = gsonBuilder.create();
            allEntities = gson.fromJson(pJSONData, new TypeToken<ArrayList<Entity>>() {
            }.getType());
            System.out.println(allEntities);
        }
    }
}
