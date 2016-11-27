/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author kenca
 */
public class AllCells {

    public ArrayList<Cell> allCells = new ArrayList<>(25);

    public void addCell(Cell pCell) {
        allCells.add(pCell);
    }

    public void remove(int i) {
        allCells.remove(i);
    }

    public int saveToJSONFile() {
        String filename = "sim.json";
        File file = new File(/*getFilesDir(),*/filename);
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file, true));
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Cell>>() {}.getType();
            String json = gson.toJson(allCells, type.getClass());
            buffWriter.append(json);
            buffWriter.newLine();
            buffWriter.close();
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }

    public int loadFromJSONFile() {
        String filename = "sim.json";
        File file = new File(/*getFilesDir(),*/filename);
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(file));
            String line;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Cell>>() {}.getType();
            allCells.clear();
            while ((line = buffReader.readLine()) != null) {
                allCells.addAll(gson.fromJson(line, type));
            }
            buffReader.close();
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }
}
