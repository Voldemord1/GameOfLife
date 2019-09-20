import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameOfLife {

    private static int x;
    private static int y;
    private static int iterations;
    private static String[][] initialCells;
    private static String[][] resultCells;

    void start() {
        readFromFile();
        for (int i = 0; i < iterations; i++) {
            if (i == 0) {
                doIteration(initialCells);
            } else {
                initialCells = resultCells;
                resultCells = null;
                doIteration(initialCells);
            }
        }
        writeToFile(resultCells);
    }

    private static void readFromFile() {
        String line;
        List<String> lines = new ArrayList<>();
        try (final BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] dimension = lines.get(0).split(" ");
        x = Integer.parseInt(dimension[0]);
        y = Integer.parseInt(dimension[1]);
        iterations = Integer.parseInt(lines.get(1));
        lines.remove(0);
        lines.remove(0);
        initialCells = new String[lines.get(0).length()][lines.size()];
        for (int i = 0; i < initialCells.length; i++) {
            String[] s = lines.get(i).split("");
            System.arraycopy(s, 0, initialCells[i], 0, initialCells[i].length);
        }
    }

    //is the cell inside the field
    private static boolean isInsideBorder(int i, int j) {
        return i >= 0 && j >= 0 && i < initialCells.length && j < initialCells.length;
    }

    private static boolean isCellAlive(int i, int j) {
        return initialCells[i][j].equals("O");
    }

    private static int countOfNeighbours(int x, int y) {
        int count = 0;
        for (int stepX = -1; stepX < 2; stepX++) {
            for (int stepY = -1; stepY < 2; stepY++) {
                int newX = x + stepX;
                int newY = y + stepY;
                if (isInsideBorder(newX, newY) && (newX != x || newY != y)) {
                    count += (isCellAlive(newX, newY)) ? 1 : 0;
                }
            }
        }
        return count;
    }

    private static void doIteration(String[][] initCells) {
        // if field > two-dimensional array of characters "X" and "O"
        if (checkFieldSize(x, y)) {
            resultCells = new String[x][y];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (i < initCells.length && j < initCells[0].length) {
                        resultCells[i][j] = initCells[i][j];
                    } else {
                        resultCells[i][j] = "X";
                    }
                }
            }
        } else {
            // field < two-dimensional array of characters "X" and "O"
            resultCells = new String[initCells.length][initCells[0].length];
            for (int i = 0; i < initCells.length; i++) {
                System.arraycopy(initCells[i], 0, resultCells[i], 0, initCells[0].length);
            }
        }

        //main process of the "Game of Life"
        for (int i = 0; i < initCells.length; i++) {
            for (int j = 0; j < initCells[i].length; j++) {
                if (isInsideBorder(i, j)) {
                    if (initCells[i][j].equals("X")) {
                        int countOfNeighbors = countOfNeighbours(i, j);
                        if (countOfNeighbors == 3) {
                            resultCells[i][j] = "O";
                        }
                    } else if (initCells[i][j].equals("O")) {
                        int countOfNeighbors = countOfNeighbours(i, j);
                        if (countOfNeighbors < 2 || countOfNeighbors > 3) {
                            resultCells[i][j] = "X";
                        }
                    }
                }
            }
        }
    }

    private static boolean checkFieldSize(int x, int y) {
        return x > initialCells.length && y > initialCells[0].length;
    }

    private static void writeToFile(String[][] result) {
        try (final FileWriter writer = new FileWriter("output.txt", false)) {
            for (String[] array : result) {
                for (String s : array) {
                    writer.write(s);
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
