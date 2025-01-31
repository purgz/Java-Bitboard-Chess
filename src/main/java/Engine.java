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

    //MAGIC
    public static final long bishopOccupancyMask[] = new long[64];

    public static final long rookOccupancyMask[] = new long[64];

    public static void initialiseLeapAttacks(){

        //e.g pawn, knight, king

        for (int square = 0; square < 64; square++){
            pawnAttacks[WHITE][square] = pawnAttackMask(square, WHITE);
            pawnAttacks[BLACK][square] = pawnAttackMask(square, BLACK);
            knightAttacks[square] = knightAttackMask(square);
            kingAttacks[square] = kingAttackMask(square);
        }
    }


    public static long bishopAttacks(int square, long blockers){


        long attacks = 0l;


        int row; int file;

        int nextRow = square / 8;
        int nextFile = square % 8;

        for (row = nextRow + 1, file = nextFile + 1; row < 7 && file < 7; row++, file++){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (row * 8 + file));
            if (((1l << (row * 8 + file)) & blockers) > 0){
                break;
            }
        }
        for (row = nextRow - 1, file = nextFile + 1; row > 0 && file < 7; row--, file++){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (row * 8 + file));
            if (((1l << (row * 8 + file)) & blockers) > 0){
                break;
            }
        }
        for (row = nextRow + 1, file = nextFile - 1; row < 7 && file > 0; row++, file--){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (row * 8 + file));
            if (((1l << (row * 8 + file)) & blockers) > 0){
                break;
            }
        }
        for (row = nextRow - 1, file = nextFile - 1; row > 0 && file > 0; row--, file--){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (row * 8 + file));
            if (((1l << (row * 8 + file)) & blockers) > 0){
                break;
            }
        }


        return attacks;
    }

    public static long rookAttacks(int square, long blockers){

        long attacks = 0l;

        int row; int file;

        int nextRow = square / 8;
        int nextFile = square % 8;

        for (row = nextRow + 1; row <= 7; row++){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (row * 8 + nextFile));
            if (((1l << (row * 8 + nextFile)) & blockers) > 0){
                break;
            }
        }
        for (row = nextRow - 1; row >= 0; row--){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (row * 8 + nextFile));
            if (((1l << (row * 8 + nextFile)) & blockers) > 0){
                break;
            }
        }
        for (file = nextFile + 1;file <= 7; file++){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (nextRow * 8 + file));
            if (((1l << (nextRow * 8 + file)) & blockers) > 0){
                break;
            }
        }
        for ( file = nextFile - 1; file >= 0;  file--){
            //1l << square - shifts a 1 bit into the given square
            attacks |= (1l << (nextRow * 8 + file));
            if (((1l << (nextRow * 8 + file)) & blockers) > 0){
                break;
            }
        }
        return attacks;
    }

    public static long rookOccupancy(int square){
        long occupancy = 0l;

        int row; int file;

        int nextRow = square / 8;
        int nextFile = square % 8;

        for (row = nextRow + 1; row < 7; row++){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (row * 8 + nextFile));
        }
        for (row = nextRow - 1; row > 0; row--){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (row * 8 + nextFile));
        }
        for (file = nextFile + 1;file < 7; file++){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (nextRow * 8 + file));
        }
        for ( file = nextFile - 1; file > 0;  file--){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (nextRow * 8 + file));
        }
        return occupancy;
    }

    public static long bishopOccupancy(int square){

        long occupancy = 0l;


        int row; int file;

        int nextRow = square / 8;
        int nextFile = square % 8;

        for (row = nextRow + 1, file = nextFile + 1; row < 7 && file < 7; row++, file++){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (row * 8 + file));
        }
        for (row = nextRow - 1, file = nextFile + 1; row > 0 && file < 7; row--, file++){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (row * 8 + file));
        }
        for (row = nextRow + 1, file = nextFile - 1; row < 7 && file > 0; row++, file--){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (row * 8 + file));
        }
        for (row = nextRow - 1, file = nextFile - 1; row > 0 && file > 0; row--, file--){
            //1l << square - shifts a 1 bit into the given square
            occupancy |= (1l << (row * 8 + file));
        }


        return occupancy;
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
