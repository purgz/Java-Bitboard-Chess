public class Engine {

    // CONSTANTS
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static final long NOT_A_FILE = -72340172838076674l;
    public static final long NOT_H_FILE = 9187201950435737471l;
    public static final long NOT_HG_FILE = 4557430888798830399l;
    public static final long NOT_AB_FILE = -217020518514230020l;

    public Engine(){
        System.out.println("Initialising engine...");
        initialiseLeapAttacks();
        System.out.println("Leap attack masks generated");

        //one day will have options for tuning engine
        //e.g. custom piece values for evaluation function
    }


    // Piece attacks masks
    public static final long pawnAttacks[][] = new long[2][64];

    public static final long knightAttacks[] = new long[64];

    public static final long kingAttacks[] = new long[64];


    public static void initialiseLeapAttacks(){

        //e.g pawn, knight, king

        for (int square = 0; square < 64; square++){
            pawnAttacks[WHITE][square] = pawnAttackMask(square, WHITE);
            pawnAttacks[BLACK][square] = pawnAttackMask(square, BLACK);
            knightAttacks[square] = knightAttackMask(square);
            kingAttacks[square] = kingAttackMask(square);
        }
    }

    public static long kingAttackMask(int square){

        long bitboard = 0l;
        long attackBitboard = 0l;

        //1
        //789

        bitboard = BitBoard.setBit(bitboard, square);

        //downwards
        attackBitboard |= (NOT_A_FILE & (bitboard << 1));
        attackBitboard |= (NOT_H_FILE & (bitboard << 7));
        attackBitboard |= (NOT_A_FILE & (bitboard << 9));
        attackBitboard |= ((bitboard << 8));

        //upwards
        attackBitboard |= (NOT_H_FILE & (bitboard >>> 1));
        attackBitboard |= (NOT_A_FILE & (bitboard >>> 7));
        attackBitboard |= (NOT_H_FILE & (bitboard >>> 9));
        attackBitboard |= ((bitboard >>> 8));

        return attackBitboard;
    }

    //much the same as pawn moves but don't need to specify colour
    //movement are 17, 15, 10, 6
    public static long knightAttackMask(int square){

        long bitboard = 0l;

        long attackBitboard = 0l;

        bitboard = BitBoard.setBit(bitboard,square);

        //moves going downwards
        attackBitboard |= (NOT_A_FILE & (bitboard << 17));
        attackBitboard |= (NOT_H_FILE & (bitboard << 15));
        attackBitboard |= (NOT_AB_FILE & (bitboard << 10));
        attackBitboard |= (NOT_HG_FILE & (bitboard << 6));

        attackBitboard |= (NOT_H_FILE & (bitboard >>> 17));
        attackBitboard |= (NOT_A_FILE & (bitboard >>> 15));
        attackBitboard |= (NOT_HG_FILE & (bitboard >>> 10));
        attackBitboard |= (NOT_AB_FILE & (bitboard >>> 6));

        return attackBitboard;
    }

    public static long pawnAttackMask(int square, int side){

        //piece bitboard
        long bitboard = 0l;


        //attack bitboard
        long attackBitboard = 0l;

        //set pawn position on the bitboard
        bitboard = BitBoard.setBit(bitboard,square);


        if (side == WHITE){
            attackBitboard |= (((bitboard >>> 7) & NOT_A_FILE)
                    | ((bitboard >>> 9) & NOT_H_FILE));
        } else {
            attackBitboard |= (((bitboard << 7) & NOT_H_FILE)
                    | ((bitboard << 9) & NOT_A_FILE));
        }

        return attackBitboard;
    }
}
