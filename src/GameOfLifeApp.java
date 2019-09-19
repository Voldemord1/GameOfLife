import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeApp {

    private static int x;
    private static int y;
    private static int iterations;
    private static String[][] initialCells;

    private static void readFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
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

    public static void main(String[] args) throws IOException {
        readFromFile();

        for (String[] array : initialCells) {
            for (String s : array) {
                System.out.print(s);
            }
            System.out.println();
        }

        System.out.println("=========");

        for (int q = 0; q < iterations; q++) {
            doIteration(initialCells);
        }

        for (String[] array : initialCells) {
            for (String s : array) {
                System.out.print(s);
            }
            System.out.println();
        }
    }

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
        String[][] tempCells = new String[x][y];
        for (int i = 0; i < initCells.length; i++) {
            for (int j = 0; j < initCells[i].length; j++) {
                if (isInsideBorder(i, j)) {
                    if (initCells[i][j].equals("X")) {
                        int countOfNeighbors = countOfNeighbours(i, j);
                        if (countOfNeighbors == 3) {
                            tempCells[i][j] = "O";
                        }
                    } else if (initCells[i][j].equals("O")) {
                        int countOfNeighbors = countOfNeighbours(i, j);
                        if (countOfNeighbors < 2 || countOfNeighbors > 3) {
                            tempCells[i][j] = "X";
                        }
                    }
                }
            }
        }

        for (int i = 0; i < tempCells.length; i++) {
            for (int j = 0; j < tempCells[i].length; j++) {
                if (tempCells[i][j] != null) {
                    initCells[i][j] = tempCells[i][j];
                }
            }
        }
    }
}
