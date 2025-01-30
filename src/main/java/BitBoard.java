public class BitBoard {


    public static long getBit(long bitboard, int square){
        return bitboard & (1L << square);
    }

    public static long setBit(long bitboard, int square){
        bitboard |= (1L << square); return bitboard;
    }

    public static long removeBit(long bitboard,int square){
        if (getBit(bitboard, square) != 0)
            bitboard ^= (1L << square);
        return bitboard;
    }


    public static void printBitboard(long bitboard){

        for (int rank = 0; rank < 8; rank++){

            for (int file = 0; file < 8; file++){

                int square = rank * 8 + file;

                if (file == 0){
                    System.out.print(8 - rank + "  ");
                }

                //shift by square to get current bit
                //self bitwise and to get 0 or 1
                long bit = getBit(bitboard, square);

                System.out.print(bit != 0 ? 1 + " " : 0 + " ");
            }

            System.out.println("");
        }

        //print each rank
        System.out.print("   a b c d e f g h\n");

        //print bitboard decimal number
        System.out.println("Board " + bitboard);
        System.out.println("***********");
    }
}
