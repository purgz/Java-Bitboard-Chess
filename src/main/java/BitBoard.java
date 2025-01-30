public class BitBoard {

    public long bitboard;

    public BitBoard(long bitboard){

        this.bitboard = bitboard;
    }

    public long getBit(int square){
        return bitboard & (1L << square);
    }

    public void setBit(int square){
        this.bitboard |= (1L << square);
    }

    public void removeBit(int square){
        if (getBit(square) != 0)
            this.bitboard ^= (1L << square);
    }


    public void printBitboard(){

        for (int rank = 0; rank < 8; rank++){

            for (int file = 0; file < 8; file++){

                int square = rank * 8 + file;

                if (file == 0){
                    System.out.print(8 - rank + "  ");
                }

                //shift by square to get current bit
                //self bitwise and to get 0 or 1
                long bit = getBit(square);

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
