
public class GameOfLifeApp {

    private static int x = 10;
    private static int y = 20;
    private static int countOfIterations = 2;
    private static String[][] startCells = {
            {"X", "X", "X", "X", "X", "X"},
            {"X", "O", "O", "X", "X", "X"},
            {"X", "O", "O", "X", "X", "X"},
            {"X", "X", "X", "O", "O", "X"},
            {"X", "X", "X", "O", "O", "X"},
            {"X", "X", "X", "X", "X", "X"}};

    private static String[][] resultCells = {
            {"X", "X", "X", "X", "X", "X"},
            {"X", "O", "O", "X", "X", "X"},
            {"X", "O", "O", "X", "X", "X"},
            {"X", "X", "X", "O", "O", "X"},
            {"X", "X", "X", "O", "O", "X"},
            {"X", "X", "X", "X", "X", "X"}};

    public static void main(String[] args) {
        for (String[] array : startCells) {
            for (String s : array) {
                System.out.print(s);
            }
            System.out.println();
        }
        System.out.println("=========");
//        for (int q = 0; q < countOfIterations; q++) {
        for (int i = 0; i < startCells.length; i++) {
            for (int j = 0; j < startCells[i].length; j++) {
                if (checkBorder(i, j)) {
                    if (startCells[i][j].equals("X")) {
                        int countOfNeighbors = countOfNeighbours(i, j);
                        if (countOfNeighbors == 3) {
                            resultCells[i][j] = "O";
                        }
                    } else if (startCells[i][j].equals("O")) {
                        int countOfNeighbors = countOfNeighbours(i, j);
                        if (countOfNeighbors < 2 || countOfNeighbors > 3) {
                            resultCells[i][j] = "X";
                        }
                    }
                }
            }
        }
//        }
        for (String[] array : resultCells) {
            for (String s : array) {
                System.out.print(s);
            }
            System.out.println();
        }
    }

    private static boolean checkBorder(int i, int j) {
        return i >= 0 && j >= 0 && i < startCells.length && j < startCells.length;
    }

    private static boolean checkLifeCell(int i, int j) {
        return startCells[i][j].equals("O");
    }

    private static int countOfNeighbours(int x, int y) {
        int count = 0;
        for (int stepX = -1; stepX < 2; stepX++) {
            for (int stepY = -1; stepY < 2; stepY++) {
                int newX = x + stepX;
                int newY = y + stepY;
                if (checkBorder(newX, newY) && (newX != x || newY != y)) {
                    count += (startCells[newX][newY].equals("O")) ? 1 : 0;
                }
            }
        }
        return count;
    }
}
