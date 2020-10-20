public class Test {

    // Stress Tester: pipe this output into PlayGin to simulate user input over a game
    public static void main(String[] args) {
        StringBuilder str = new StringBuilder();
        // random answers for settings questions
        str.append("\n");
        double r = Math.random();
        if (r >= 0.5) str.append("y\n");
        else str.append("n\n");
        r = Math.random();
        if (r >= 0.5) str.append("y\n");
        else str.append("n\n");
        r = Math.random();
        if (r >= 0.5) str.append("y\n");
        else str.append("n\n");

        // add user input
        str.append("\n");
        Deck d = new Deck();
        for (int i = 0; i < 100; i++) {
            str.append(d.testString());
        }
        System.out.println(str);
    }

    // stress test by running following command in terminal many many times
    // java Test > test.txt ; java PlayGin < test.txt ;
}


