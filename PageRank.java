import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class PageRank {

    // constant field
    final double BETA = 0.8;
    final double maxDelta = 0.0001;
    final int maxRepeatLimit = 100;

    // instance field
    int PAGES;
    int [][] relationshiop;
    double [][] M;
    double [] r;
    double [] rOld;
    ArrayList<String> names;
    GUI gui;

    PageRank() {
        gui = new GUI();
    }

    void readNewFile() {
        // reading in the field
        String urlFile = gui.showFileChooser("Select URL File");
        String linkFile = gui.showFileChooser("Select Link File");

        try {
            names = new ArrayList<>();
            FileInputStream fIS = new FileInputStream(urlFile);
            Scanner scanner = new Scanner(fIS);

            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }

            scanner.close();
            fIS.close();

        } catch (IOException e) {
            System.out.println("URL FILE: FAILED TO READ");
        }

        // allocting space in the memory for the array
        PAGES = names.size();
        relationshiop = new int[PAGES][PAGES];
        M = new double[PAGES][PAGES];
        r = new double[PAGES];
        rOld = new double[PAGES];

        // Initialize r and rOld
        for (int i = 0; i < PAGES; i++) {
            r[i] = (1.0 / PAGES);
            rOld[i] = (1.0 / PAGES);
        }

        // Initialize M to 0.0 to the teleport value
            for (int i = 0; i < PAGES; i++) {
                for (int j = 0; j < PAGES; j++) {
                    M[i][j] = ((1.0-BETA)/(double)PAGES);
                    relationshiop[i][j] = 0;
                }
        }

        // Read in the links from the links file.
        try {
            FileInputStream fIS = new FileInputStream(linkFile);
            Scanner scanner = new Scanner(fIS);

            // Mark our the relationship between each webpage
            while (scanner.hasNext()) {
                int row = scanner.nextInt();
                if(scanner.hasNext()){
                    int col =scanner.nextInt();

                    // Mark the intersection.update the relationship
                    relationshiop[row][col] += 1;
                }
                else{
                    scanner.next(); // do this to skip the empty new line
                }

            }


            scanner.close();
            fIS.close();

            // Generate Array M
            for (int col = 0; col < PAGES; col++) {
                int degree = 0;

                // Calculate the degree for the node.
                for (int row = 0; row < PAGES; row++) {
                    degree += relationshiop[col][row];
                }

                // read through M.
                if (degree != 0.0) {
                    for (int row = 0; row < PAGES; row++) {
                        if (relationshiop[col][row] != 0) {
                            M[col][row] =(BETA/(double)degree)+M[col][row];
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("out of bound");
        }
    }



    void calculatePageRank() {
        int iteration = 0;
        // r is the importance of the node

        while (iteration < maxRepeatLimit) {
            iteration += 1;

            // Copy r to rOld
            System.arraycopy(r, 0, rOld, 0, PAGES);

            for (int i = 0; i < PAGES; i++) {
                double rowSum = 0;

                for (int j = 0; j < PAGES; j++) {
                    rowSum += (M[j][i] * rOld[j]);
                }

                // Set r to the value.
                r[i] = rowSum;
            }

            // Normalize r.
            // Compute the sum of R.
            double rSum = 0;
            for (int i = 0; i < PAGES; i++) {
                rSum += r[i];
            }

            // Perform the normalization of R.
            for (int i = 0; i < PAGES; i++) {
                r[i] = (r[i] / rSum);
            }

            // Compute the distance between r and rOld.
            // This will determine if we can stop.
            double squaredSum = 0;
            for (int i = 0; i < PAGES; i++) {
                squaredSum = Math.pow((r[i] - rOld[i]), 2);
            }
            double delta = Math.sqrt(squaredSum);

            // Determine if the delta falls under our threshold.
            if (delta < maxDelta) {
                return;
            }
        }
    }

    void printOut() {

        // Migrate the array over to a new array that holds the web address in addition to the page rank.
        TreeMap<Double, String> pageRankMap = new TreeMap<>();
        for( int i = 0; i < PAGES; i++) {
            pageRankMap.put(r[i], names.get(i));
        }

        // Convert to navigable map and sort in descendingMap
        NavigableMap<Double, String> sortedPageRankMap = pageRankMap.descendingMap();

        // Print the map.
        int iteration = 0;
        for(Map.Entry<Double, String> entry : sortedPageRankMap.entrySet()) {
            Double key = entry.getKey();
            String value = entry.getValue();

            // Print out the values
            System.out.println(iteration + ": " + value + " " + key);
            iteration++;
            if(iteration>9){
                break;
            }
        }
    }
}
