public class Main {

    public static void main(String[] args) {
        PageRank pageRank = new PageRank();
        pageRank.readNewFile();
        pageRank.calculatePageRank();
        pageRank.printOut();

        System.exit(0);
    }
}
