package controller;

import model.CheckersBoard;

import java.io.*;

/**
 * Created by Martin on 2016-10-15.
 */
public class SaveManager {


        public void serializeToFile(String filename, CheckersBoard board) throws IOException {
            ObjectOutputStream out = null;

            try {
                out = new ObjectOutputStream(new FileOutputStream(filename));
                out.writeObject(board);
            } finally {
                try {
                    if (out != null)
                        out.close();
                } catch (Exception e) {
                }
            }
        }


        /**
         * Fills library with books from already existing file
         */
        @SuppressWarnings("unchecked")
        public CheckersBoard deSerializeFromFile(String filename) throws IOException, ClassNotFoundException {

            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(filename));
                return ((CheckersBoard) in.readObject());
            } finally {
                try {
                    if (in != null) in.close();
                } catch (Exception e) {
                }
            }
        }
    }
