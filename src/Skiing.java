import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Skiing {
            private static int[][] map;
            private static int row;
            private static int column;
            private static int[][] path;
            private static int[][] drop;

            public static void main(String[] args) throws Exception {
                String fileName = "src/data.txt";
                loadData(fileName);

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        // Only calculate the non-visited nodes
                        if (path[i][j] == 0) {
                            calculatePathAndDrop(i, j);
                        }
                    }
                }

                int maxPath = Integer.MIN_VALUE;
                int maxDrop = Integer.MIN_VALUE;

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        if (path[i][j] > maxPath) {
                            maxPath = path[i][j];
                            maxDrop = drop[i][j];
                        }
                        if (path[i][j] == maxPath) {
                            if (drop[i][j] > maxDrop) {
                                maxDrop = drop[i][j];
                            }
                        }
                    }
                }

                System.out.println("Max Path: " + maxPath + "\nMaxDrop: " + maxDrop);
            }

            public static void loadData(String fileName) throws Exception {
                File file = new File(fileName);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                if ((line = bufferedReader.readLine()) != null) {
                    String[] str = line.split(" ");
                    row = Integer.parseInt(str[0]);
                    column = Integer.parseInt(str[1]);
                    map = new int[row][column];
                    path = new int[row][column];
                    drop = new int[row][column];
                }
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    int j = 0;
                    String[] str = line.split(" ");
                    for (String s : str) {
                        map[i][j++] = Integer.parseInt(s);
                    }
                    i++;
                }
                bufferedReader.close();
            }

            public static void calculateWithNeighborNode(int i, int j, int k, int l) {
                if (k < 0 || k >= row || l < 0 || l >= column) {
                    return;
                }

                if (map[i][j] > map[k][l]) {
                    if (path[k][l] == 0) {
                        calculatePathAndDrop(k, l);
                    }

                    int newDrop = map[i][j] - map[k][l] + drop[k][l];

                    // Compare path of current node with longest path of neighbor node
                    if (path[i][j] < path[k][l] + 1) {
                        path[i][j] = path[k][l] + 1;
                        drop[i][j] = newDrop;
                    } else if (path[i][j] == path[k][l] + 1 && drop[i][j] < newDrop) {
                        // Compare drop if paths is same
                        drop[i][j] = newDrop;
                    }

                }
            }

            public static void calculatePathAndDrop(int i, int j) {
                // Go up
                calculateWithNeighborNode(i, j, i  - 1, j);
                // Go down
                calculateWithNeighborNode(i, j, i  + 1, j);
                // Go left
                calculateWithNeighborNode(i, j, i, j - 1);
                // Go right
                calculateWithNeighborNode(i, j, i, j + 1);

                // Check the isolated nodes
                if (path[i][j] == 0) {
                    path[i][j] = 1;
                }
            }
    }
