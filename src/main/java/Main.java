public class Main {

    public static final int WHITE = 0;
    public static final int BLACK = 1;



    /*
        8  0 1 1 1 1 1 1 1
        7  0 1 1 1 1 1 1 1
        6  0 1 1 1 1 1 1 1
        5  0 1 1 1 1 1 1 1
        4  0 1 1 1 1 1 1 1
        3  0 1 1 1 1 1 1 1
        2  0 1 1 1 1 1 1 1
        1  0 1 1 1 1 1 1 1
     */

    //constants precomputed, e.g. above for the not A file
    //can use these masks to remove edge cases where pieces would wrap attacks across the edges of the board
    public static final long NOT_A_FILE = -72340172838076674l;
    public static final long NOT_H_FILE = 9187201950435737471l;
    public static final long NOT_HG_FILE = 4557430888798830399l;
    public static final long NOT_AB_FILE = -217020518514230020l;


    //pawn attacks table [side][square]
    static final long pawnAttacks[][] = new long[2][64];

    static final long knightAttacks[] = new long[64];

    static final long kingAttacks[] = new long[64];

    public static void main(String[] args) {

        Engine myChessEngine = new Engine();

        for (int i = 0; i < 64; i ++){

            // BitBoard.printBitboard(myChessEngine.kingAttacks[i]);
        }

    }




    enum Squares {
        a8, b8, c8, d8, e8, f8, g8, h8,
        a7, b7, c7, d7, e7, f7, g7, h7,
        a6, b6, c6, d6, e6, f6, g6, h6,
        a5, b5, c5, d5, e5, f5, g5, h5,
        a4, b4, c4, d4, e4, f4, g4, h4,
        a3, b3, c3, d3, e3, f3, g3, h3,
        a2, b2, c2, d2, e2, f2, g2, h2,
        a1, b1, c1, d1, e1, f1, g1, h1,
    }
}
